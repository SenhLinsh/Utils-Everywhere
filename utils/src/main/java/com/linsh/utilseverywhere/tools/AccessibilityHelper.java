package com.linsh.utilseverywhere.tools;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 用于 AccessibilityService 的帮助类
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AccessibilityHelper {

    private AccessibilityService mService;

    public AccessibilityHelper(AccessibilityService service) {
        mService = service;
    }

    /**
     * 通过 View Id 查找节点信息
     *
     * @param viewId View Id, 可通过 HierarchyViewer 等查看
     * @return 包含该 View Id 的节点信息集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<AccessibilityNodeInfo> findNodeInfosByViewId(String viewId) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
        }
        return null;
    }

    /**
     * 通过 View Id 查找首个匹配的节点信息
     *
     * @param viewId View Id, 可通过 HierarchyViewer 等查看
     * @return 首个匹配的节点信息
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public AccessibilityNodeInfo findFirstNodeInfoByViewId(String viewId) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
            if (infos != null && infos.size() > 0) {
                return infos.get(0);
            }
        }
        return null;
    }

    /**
     * 通过文字内容查找节点信息
     *
     * @param text 文字内容
     * @return 包含该文字内容的节点信息集合
     */
    public List<AccessibilityNodeInfo> findNodeInfosByText(String text) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

    /**
     * 通过文字内容查找首个匹配的节点信息
     *
     * @param text 文字内容
     * @return 首个匹配的节点信息
     */
    public AccessibilityNodeInfo findFirstNodeInfoByText(String text) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByText(text);
            if (infos != null && infos.size() > 0) {
                return infos.get(0);
            }
        }
        return null;
    }

    /**
     * 查找当前窗口所有节点中所包含的文本内容
     *
     * @return 所有节点中所包含的文本内容
     */
    public List<String> findAllTexts() {
        return findAllTexts(mService.getRootInActiveWindow());
    }

    /**
     * 查找当前节点下所有节点中所包含的文本内容
     *
     * @return 所有节点中所包含的文本内容
     */
    public List<String> findAllTexts(AccessibilityNodeInfo nodeInfo) {
        ArrayList<String> result = new ArrayList<>();
        findAllTexts(nodeInfo, result);
        return result;
    }

    /**
     * 查找指定的节点下所有节点中所包含的文本内容
     *
     * @param viewId 指定节点的 View Id
     * @return 指定的节点下所有节点所包含的文本内容
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<String> findAllTexts(String viewId) {
        ArrayList<String> result = new ArrayList<>();
        List<AccessibilityNodeInfo> infos = findNodeInfosByViewId(viewId);
        if (infos != null && infos.size() > 0) {
            for (AccessibilityNodeInfo info : infos) {
                findAllTexts(info, result);
            }
        }
        return result;
    }

    private void findAllTexts(AccessibilityNodeInfo nodeInfo, ArrayList<String> result) {
        if (nodeInfo == null) return;
        CharSequence text = nodeInfo.getText();
        CharSequence className = nodeInfo.getClassName();
        if (text != null && text.length() > 0) {
            result.add(text.toString());
        }
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            findAllTexts(nodeInfo.getChild(i), result);
        }
    }

    /**
     * 查找当前窗口所有节点中所包含的文本描述
     *
     * @return 所有节点中所包含的文本描述
     */
    public List<String> findAllContentDescriptions() {
        return findAllContentDescriptions(mService.getRootInActiveWindow());
    }

    /**
     * 查找指定节点下所有节点中所包含的文本描述
     *
     * @return 所有节点中所包含的文本描述
     */
    public List<String> findAllContentDescriptions(AccessibilityNodeInfo nodeInfo) {
        ArrayList<String> result = new ArrayList<>();
        findAllContentDescriptions(nodeInfo, result);
        return result;
    }

    private void findAllContentDescriptions(AccessibilityNodeInfo nodeInfo, ArrayList<String> result) {
        if (nodeInfo == null) return;
        CharSequence text = nodeInfo.getContentDescription();
        if (text != null && text.length() > 0) {
            result.add(text.toString());
        }
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            findAllContentDescriptions(nodeInfo.getChild(i), result);
        }
    }

    /**
     * 模拟点击指定的节点
     * <p>即使该节点是按钮控件, 使用该方法也不一定能模拟成功, 需要判断该节点是否可以点击
     *
     * @param nodeInfo 指定的节点
     */
    public void performClick(AccessibilityNodeInfo nodeInfo) {
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    /**
     * 模拟点击指定的节点
     * <p>即使该节点是按钮控件, 使用该方法也不一定能模拟成功, 需要判断该节点是否可以点击
     * <br>如果当前节点不可点击, 可以尝试往上追溯, 点击父节点, 直到该节点可以点击为止
     *
     * @param nodeInfo                  指定的节点
     * @param clickParentIfNotClickable 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     */
    public void performClick(AccessibilityNodeInfo nodeInfo, boolean clickParentIfNotClickable) {
        if (clickParentIfNotClickable) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                while (parent != null) {
                    if (parent.isClickable()) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        break;
                    }
                    parent = parent.getParent();
                }
            }
        } else {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }
}
