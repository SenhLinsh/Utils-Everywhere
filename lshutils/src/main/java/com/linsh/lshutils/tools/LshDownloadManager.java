package com.linsh.lshutils.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.linsh.lshutils.utils.ApplicationUtils;
import com.linsh.lshutils.utils.SharedPreferenceUtils;
import com.linsh.lshutils.utils.StringUtils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 简化系统 DownloadManager 文件下载操作的帮助类
 * </pre>
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

    /**
     * 构建 RequestBuilder
     *
     * @param downloadUrl 下载地址
     * @param fileName    文件名
     */
    public RequestBuilder buildRequest(String downloadUrl, String fileName) {
        mRequestBuilder = new RequestBuilder(downloadUrl, fileName);
        return mRequestBuilder;
    }

    /**
     * 开始下载
     */
    public void download() {
        if (mRequestBuilder == null) {
            throw new RuntimeException("请先调用 buildRequest() 方法以构建请求参数");
        }
        mRequestBuilder.download();
    }

    /**
     * 取消下载
     */
    public void cancel() {
        if (mRequestBuilder != null && mRequestId != 0) {
            DownloadManager manager = (DownloadManager) ApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.remove(mRequestId);
            mRequestId = 0;
        }
    }

    /**
     * 获取当前进度
     *
     * @return 返回值为 -1 时, 表示查询失败
     */
    public float getProgress() {
        return getProgress(mRequestId);
    }

    /**
     * 获取指定 requestId 的下载进度
     *
     * @param requestId 指定下载任务的 requestId
     */
    private static float getProgress(long requestId) {
        if (requestId != 0) {
            DownloadManager manager = (DownloadManager) ApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
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

    /**
     * 查询进度
     *
     * @param callback 进度回调
     */
    public void queryProgress(final QueryCallback callback) {
        if (mRequestId == 0) {
            callback.onFailed("没有当前任务");
            return;
        }
        final DownloadManager manager = (DownloadManager) ApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
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
            ApplicationUtils.getMainHandler().postDelayed(new Runnable() {
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

    /**
     * 停止查询
     */
    public void stopQuery() {
        mIsQuerying = false;
    }

    /**
     * 获取当前下载好的文件
     *
     * @return 文件
     */
    public File getDownloadedFile() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);
    }

    /**
     * 指定下载任务, 如果已经下载好了则返回该文件, 没有下载好返回空
     *
     * @param requestId 指定下载任务的 requestId
     * @return 如果已经下载好了则返回该文件, 没有下载好返回 null
     */
    public static File getFileIfDownloaded(long requestId) {
        File file = null;
        DownloadManager manager = (DownloadManager) ApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(requestId);
        Cursor cursor = manager.query(query);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int downloadedSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                if (downloadedSize == total) {
                    String filePath;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        String fileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        filePath = Uri.parse(fileUri).getPath();
                    } else {
                        filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    }
                    file = new File(filePath);
                }
            }
            cursor.close();
        }
        return file;
    }

    /**
     * 注册下载完成时接收广播的接收者
     *
     * @param receiver 广播接收者
     */
    public void registerCompleteReceiver(BroadcastReceiver receiver) {
        ApplicationUtils.getContext().registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 解除注册广播接收者
     *
     * @param receiver 广播接收者
     */
    public void unregisterReceiver(BroadcastReceiver receiver) {
        ApplicationUtils.getContext().unregisterReceiver(receiver);
    }

    //================================================ 构建 Request ================================================//
    public class RequestBuilder {

        private DownloadManager.Request mRequest;

        /**
         * @param downloadUrl 下载地址
         * @param fileName    文件名
         */
        public RequestBuilder(String downloadUrl, String fileName) {
            mFileName = fileName;
            mRequest = new DownloadManager.Request(Uri.parse(downloadUrl));
            mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            mRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        }

        /**
         * 设置网络下载类型
         * <p> 好像无法设置可以使用流量下载? --> // Todo
         *
         * @param flags {@link DownloadManager.Request#NETWORK_WIFI} 或 {@link DownloadManager.Request#NETWORK_MOBILE}
         */
        public RequestBuilder setNetworkTypes(int flags) {
            mRequest.setAllowedNetworkTypes(flags);
            return this;
        }

        /**
         * 设置通知栏进度显示
         *
         * @param title      标题
         * @param desc       描述
         * @param visibility 可见性, 使用以下值:
         *                   <br>{@link DownloadManager.Request#VISIBILITY_HIDDEN}: Notification 将不会显示，如果设置该属性的话，必须要添加权限 。 Android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
         *                   <br>{@link DownloadManager.Request#VISIBILITY_VISIBLE}: Notification 显示，但是只是在下载任务执行的过程中显示，下载完成自动消失。（默认值）
         *                   <br>{@link DownloadManager.Request#VISIBILITY_VISIBLE_NOTIFY_COMPLETED}: Notification 显示，下载进行时，和完成之后都会显示。
         *                   <br>{@link DownloadManager.Request#VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION}: 只有当任务完成时，Notification 才会显示。
         */
        public RequestBuilder setNotification(String title, String desc, int visibility) {
            mRequest.setTitle(title);
            mRequest.setDescription(desc);
            mRequest.setNotificationVisibility(visibility);
            return this;
        }

        /**
         * 设置通知栏进度显示
         *
         * @param title 标题
         * @param desc  描述
         */
        public RequestBuilder setNotification(String title, String desc) {
            return setNotification(title, desc, DownloadManager.Request.VISIBILITY_VISIBLE);
        }

        /**
         * 设置下载文件的 Mime 类型
         *
         * @param extension 扩展名
         */
        public RequestBuilder setMimeType(String extension) {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            if (mimeTypeMap.hasExtension(extension)) {
                String mimeType = mimeTypeMap.getMimeTypeFromExtension(extension);
                mRequest.setMimeType(mimeType);
            }
            return this;
        }

        /**
         * 添加 RequestHeader
         *
         * @param name  名称
         * @param value 值
         */
        public RequestBuilder addRequestHeader(String name, String value) {
            mRequest.addRequestHeader(name, value);
            return this;
        }

        /**
         * 开始下载
         *
         * @return 本次下载请求的 Id
         */
        public long download() {
            // 判断是否已经下载过该文件
            if (mRequestId == 0 && !StringUtils.isEmpty(mDownloadKey)) {
                long requestId = SharedPreferenceUtils.getLong(mDownloadKey);
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
            DownloadManager manager = (DownloadManager) ApplicationUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            long id = manager.enqueue(mRequest);
            if (mRequestId != id) {
                mRequestId = id;
                SharedPreferenceUtils.putLong(mDownloadKey, mRequestId);
            }
            return id;
        }
    }

    public interface QueryCallback {

        /**
         * 下载完成
         */
        void onCompleted();

        /**
         * 下载失败
         *
         * @param msg 失败信息
         */
        void onFailed(String msg);

        /**
         * 下载中
         *
         * @param progress 进度
         */
        void inProgress(float progress);
    }
}
