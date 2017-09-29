package com.linsh.lshutils.tools;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senh Linsh on 17/9/18.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class LshAccessibilityHelper {

    private AccessibilityService mService;

    public LshAccessibilityHelper(AccessibilityService service) {
        mService = service;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<AccessibilityNodeInfo> findNodeInfosByViewId(String viewId) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
        }
        return null;
    }

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

    public List<AccessibilityNodeInfo> findNodeInfosByText(String text) {
        AccessibilityNodeInfo nodeInfo = mService.getRootInActiveWindow();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

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

    public List<String> findAllTexts() {
        return findAllTexts(mService.getRootInActiveWindow());
    }

    public List<String> findAllTexts(AccessibilityNodeInfo nodeInfo) {
        ArrayList<String> result = new ArrayList<>();
        findAllTexts(nodeInfo, result);
        return result;
    }

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

    public List<String> findAllContentDescriptions() {
        return findAllContentDescriptions(mService.getRootInActiveWindow());
    }

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
}
