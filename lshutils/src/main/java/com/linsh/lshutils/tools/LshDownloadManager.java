package com.linsh.lshutils.tools;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;
import com.linsh.lshutils.utils.Basic.LshSharedPreferenceUtils;
import com.linsh.lshutils.utils.Basic.LshStringUtils;

import java.io.File;


/**
 * Created by Senh Linsh on 17/5/23.
 */

public class LshDownloadManager {

    private RequestBuilder mRequestBuilder;
    private String mDownloadKey;
    private String mFileName;
    private long mRequestId;
    private boolean mIsQuerying;

    public LshDownloadManager() {
    }

    /**
     * @param downloadKey 本次下载链接的 KEY, 用于存储 requestId; 建议使用, 可以避免已经下载好的文件被重复下载
     */
    public LshDownloadManager(String downloadKey) {
        mDownloadKey = downloadKey;
    }

    public RequestBuilder buildRequest(String downloadUrl, String fileName) {
        mRequestBuilder = new RequestBuilder(downloadUrl, fileName);
        return mRequestBuilder;
    }

    public void download() {
        if (mRequestBuilder == null) {
            throw new RuntimeException("请先调用 buildRequest() 方法以构建请求参数");
        }
        mRequestBuilder.download();
    }

    public void cancel() {
        if (mRequestBuilder != null && mRequestId != 0) {
            DownloadManager manager = (DownloadManager) LshApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.remove(mRequestId);
            mRequestId = 0;
        }
    }

    /**
     * @return 返回值为 -1 时, 表示查询失败
     */
    public float getProgress() {
        return getProgress(mRequestId);
    }

    private static float getProgress(long requestId) {
        if (requestId != 0) {
            DownloadManager manager = (DownloadManager) LshApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(requestId);
            return getProgress(manager, query);
        }
        return -1;
    }

    private static float getProgress(DownloadManager manager, DownloadManager.Query query) {
        float progress = -1;
        Cursor cursor = manager.query(query);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int downloadedSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                progress = downloadedSize * 1f / total;
            }
            cursor.close();
        }
        return progress;
    }

    public void queryProgress(final QueryCallback callback) {
        if (mRequestId == 0) {
            callback.onFailed("没有当前任务");
            return;
        }
        final DownloadManager manager = (DownloadManager) LshApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        final DownloadManager.Query query = new DownloadManager.Query().setFilterById(mRequestId);
        if (query == null) {
            callback.onFailed("没有当前任务");
            return;
        }
        if (mIsQuerying) {
            callback.onFailed("正在查询中");
            return;
        }
        mIsQuerying = true;
        queryProgress(manager, query, callback);
    }

    private void queryProgress(final DownloadManager manager, final DownloadManager.Query query, final QueryCallback callback) {
        if (!mIsQuerying) {
            callback.onFailed("查询被中止");
            return;
        }
        float progress = getProgress(manager, query);
        if (progress < 0) {
            callback.onFailed("查询失败");
            mIsQuerying = false;
            return;
        }
        callback.inProgress(progress);
        if (progress < 1) {
            LshApplicationUtils.getMainHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    queryProgress(manager, query, callback);
                }
            }, 1000);
            return;
        }
        callback.onCompleted();
        mIsQuerying = false;
    }

    public void stopQuery() {
        mIsQuerying = false;
    }

    public File getDownloadedFile() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);
    }

    //================================================ 构建 Request ================================================//
    public class RequestBuilder {

        private DownloadManager.Request mRequest;

        public RequestBuilder(String downloadUrl, String fileName) {
            mFileName = fileName;
            mRequest = new DownloadManager.Request(Uri.parse(downloadUrl));
            mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            mRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        }

        /**
         * DownloadManager.Request.NETWORK_WIFI
         * DownloadManager.Request.NETWORK_MOBILE
         */
        public RequestBuilder setNetworkTypes(int flags) {
            mRequest.setAllowedNetworkTypes(flags);
            return this;
        }

        /**
         * DownloadManager.Request.VISIBILTY_HIDDEN: Notification:将不会显示，如果设置该属性的话，必须要添加权限 。 Android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
         * DownloadManager.Request.VISIBILITY_VISIBLE： Notification显示，但是只是在下载任务执行的过程中显示，下载完成自动消失。（默认值）
         * DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED : Notification显示，下载进行时，和完成之后都会显示。
         * DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION ：只有当任务完成时，Notification才会显示。
         */
        public RequestBuilder setNotification(String title, String desc, int visibility) {
            mRequest.setTitle(title);
            mRequest.setDescription(desc);
            mRequest.setNotificationVisibility(visibility);
            return this;
        }

        public RequestBuilder setNotification(String title, String desc) {
            return setNotification(title, desc, DownloadManager.Request.VISIBILITY_VISIBLE);
        }

        public RequestBuilder setMimeType(String extension) {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            if (mimeTypeMap.hasExtension(extension)) {
                String mimeType = mimeTypeMap.getMimeTypeFromExtension(extension);
                mRequest.setMimeType(mimeType);
            }
            return this;
        }

        public RequestBuilder addRequestHeader(String name, String value) {
            mRequest.addRequestHeader(name, value);
            return this;
        }

        /**
         * @return 本次下载请求的 Id
         */
        public long download() {
            // 判断是否已经下载过该文件
            if (mRequestId == 0 && !LshStringUtils.isEmpty(mDownloadKey)) {
                long requestId = LshSharedPreferenceUtils.getLong(mDownloadKey);
                if (requestId > 0) {
                    float progress = getProgress(requestId);
                    if (progress == 1) {
                        // 进度为 1 , 表示系统已经下载过该文件了
                        File file = getDownloadedFile();
                        if (file.isFile() && file.exists()) {
                            // 文件存在, 则不需要重新下载
                            mRequestId = requestId;
                            return mRequestId;
                        }
                    }
                    // 大于 0 小于 1 时, 执行下载, 会继续本次下载. 取消或删除任务之后, 查询为 0
                } else {
                    // 没有保存过 RequestId, 说明因为没有执行过该下载, 可先将可能存在的同名文件删除后再下载
                    File file = getDownloadedFile();
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
            // 判断是否已经执行完该下载请求
            if (mRequestId != 0 && getProgress(mRequestId) == 1) {
                return mRequestId;
            }
            DownloadManager manager = (DownloadManager) LshApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            long id = manager.enqueue(mRequest);
            if (mRequestId != id) {
                mRequestId = id;
                LshSharedPreferenceUtils.putLong(mDownloadKey, mRequestId);
            }
            return id;
        }
    }

    public interface QueryCallback {

        void onCompleted();

        void onFailed(String msg);

        void inProgress(float progress);
    }

}
