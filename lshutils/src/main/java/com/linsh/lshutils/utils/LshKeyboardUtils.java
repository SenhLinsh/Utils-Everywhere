package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.linsh.lshutils.common.FileProperties;
import com.linsh.lshutils.utils.Basic.LshSharedPreferenceUtils;


/**
 * Created by Senh Linsh on 16/8/12.
 */
public class LshKeyboardUtils {
    public static final String KEY_KEYBOARD_HEIGHT_LANDSCAPE = "key_keyboard_height_landscape";
    public static final String KEY_KEYBOARD_HEIGHT_PORTRAIT = "key_keyboard_height_portrait";
    private static final int PANEL_HEIGHT_WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    private static final int PANEL_HEIGHT_EQUAL_KEYBOARD = ViewGroup.LayoutParams.MATCH_PARENT;
    private static int mPanelHeight = PANEL_HEIGHT_WRAP_CONTENT;


    public static void attach(Activity activity, View panelRoot, @Nullable View btn, View etInput,
                              @Nullable OnKeyboardShowingListener onKeyboardShowingListener, @Nullable OnPanelShowingListener onPanelShowingListener) {
        if (activity == null) {
            throw new RuntimeException("need to bind an Activity.");
        }
        if (panelRoot == null) {
            throw new RuntimeException("need a panel to handler the keyboard conflict.");
        }

        final ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        KeyboardStatusListener keyboardStatusListener = new KeyboardStatusListener(
                contentView, panelRoot, btn, etInput, onKeyboardShowingListener, onPanelShowingListener);

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardStatusListener);
        if (btn != null) {
            btn.setOnClickListener(keyboardStatusListener);
        }
        if (etInput != null) {
            etInput.setOnClickListener(keyboardStatusListener);
        }
    }

    private static class KeyboardStatusListener implements ViewTreeObserver.OnGlobalLayoutListener,
            View.OnClickListener {
        private final ViewGroup mContentView;
        private final View mPanel;
        private final View mBtn;
        private final View mEtInput;
        private final OnKeyboardShowingListener mOnKeyboardShowingListener;
        private OnPanelShowingListener mOnPanelShowingListener;
        private int maxBottom;
        private int lastKeyboardHeight;
        private boolean keyBoard2Panel;
        private boolean isKeyboardShowed;

        public KeyboardStatusListener(ViewGroup contentView, View panel, @Nullable View btn, View etInput,
                                      @Nullable OnKeyboardShowingListener onKeyboardShowingListener, @Nullable OnPanelShowingListener onPanelShowingListener) {
            mContentView = contentView;
            mPanel = panel;
            mBtn = btn;
            mEtInput = etInput;
            mOnKeyboardShowingListener = onKeyboardShowingListener;
            mOnPanelShowingListener = onPanelShowingListener;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            mContentView.getWindowVisibleDisplayFrame(rect);

            // 获取最大底部坐标
            if (maxBottom < rect.bottom) {
                maxBottom = rect.bottom;
            }
            // 获取键盘高度
            int keyboardHeight = 0;
            if (maxBottom != 0 && maxBottom - rect.bottom > 100) {
                keyboardHeight = maxBottom - rect.bottom;
            }
            // 每次初始化界面时都不能直接获取键盘高度,先从本地保存的数据中获取
            // ps.第一次启动应用时,这时本地也没有数据,这是打开软件盘时无法自动匹配键盘高度,导致此时无法解决遮挡问题,等第二次开始之后就好了 (算是一个还没有解决的bug吧)
            if (keyboardHeight == 0) {
                if (isOrientationPortrait()) {
                    keyboardHeight = LshSharedPreferenceUtils.getInt(KEY_KEYBOARD_HEIGHT_PORTRAIT);
                } else {
                    keyboardHeight = LshSharedPreferenceUtils.getInt(KEY_KEYBOARD_HEIGHT_LANDSCAPE);
                }
            }
            if (keyboardHeight == 0) {
                FileProperties fileProperties = LshPropertiesFileUtils.getObject(FileProperties.class);
                if (fileProperties != null) {
                    keyboardHeight = isOrientationPortrait() ? fileProperties.KeyboardHeightPortrait : fileProperties.KeyboardHeightLandscape;
                }
            }
            // 键盘高度发生变化, 保存数据到本地
            if (lastKeyboardHeight != keyboardHeight && keyboardHeight != 0) {
                if (isOrientationPortrait()) {
                    if (LshSharedPreferenceUtils.getInt(KEY_KEYBOARD_HEIGHT_PORTRAIT) != keyboardHeight) {
                        LshSharedPreferenceUtils.putInt(KEY_KEYBOARD_HEIGHT_PORTRAIT, keyboardHeight);
                        FileProperties fileProperties = LshPropertiesFileUtils.getObject(FileProperties.class);
                        if (fileProperties == null) {
                            fileProperties = new FileProperties();
                        }
                        fileProperties.KeyboardHeightPortrait = keyboardHeight;
                        LshPropertiesFileUtils.putObject(fileProperties);
                    }
                } else {
                    if (LshSharedPreferenceUtils.getInt(KEY_KEYBOARD_HEIGHT_LANDSCAPE) != keyboardHeight) {
                        LshSharedPreferenceUtils.putInt(KEY_KEYBOARD_HEIGHT_LANDSCAPE, keyboardHeight);
                        FileProperties fileProperties = LshPropertiesFileUtils.getObject(FileProperties.class);
                        if (fileProperties == null) {
                            fileProperties = new FileProperties();
                        }
                        fileProperties.KeyboardHeightLandscape = keyboardHeight;
                        LshPropertiesFileUtils.putObject(fileProperties);
                    }
                }

            }
            // 判断键盘的显示和隐藏
            if (keyboardHeight != 0 && maxBottom - rect.bottom > 100 && !isKeyboardShowed) {
                Log.w("LshKeyboardUtils---", "show keyboard  " + "keyboardHeight=" + keyboardHeight);
                isKeyboardShowed = true;
                if (mOnKeyboardShowingListener != null) {
                    mOnKeyboardShowingListener.onKeyboardShowing(true);
                }
            }
            if (maxBottom != 0 && maxBottom == rect.bottom && isKeyboardShowed) {
                Log.w("LshKeyboardUtils---", "hide keyboard");
                isKeyboardShowed = false;
                if (mOnKeyboardShowingListener != null) {
                    mOnKeyboardShowingListener.onKeyboardShowing(false);
                }

                if (keyBoard2Panel) { // 隐藏键盘, 显示面板
                    (mPanel).setVisibility(View.VISIBLE);
                    refreshPanelHeight();
                } else { // 隐藏键盘
                    (mPanel).setVisibility(View.GONE);
                }
                keyBoard2Panel = false;
            }
            lastKeyboardHeight = keyboardHeight;
        }

        /**
         * 判断横竖屏, 横屏和竖屏的键盘高度是不一样的, 使用过程中可能会出现横竖屏转换
         */
        public boolean isOrientationPortrait() {
            Activity activity = (Activity) (mPanel).getContext();
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            return rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180;
        }

        @Override
        public void onClick(View v) {
            if (mBtn != null && v.getId() == mBtn.getId()) { /// 键盘面板切换按钮的点击事件
                boolean isKeyBoardShowing = isKeyBoardShowing();
                if (isKeyBoardShowing) { // 键盘正在显示, 切换至面板
                    keyBoard2Panel = true;
                    showPanel(mPanel);
                    if (mOnPanelShowingListener != null) {
                        mOnPanelShowingListener.onPanelShowing(true);
                    }
                } else if ((mPanel).getVisibility() == View.VISIBLE) { // 面板在显示, 切换至键盘
                    (mPanel).setVisibility(View.INVISIBLE);
                    refreshPanelHeight(lastKeyboardHeight);
                    showKeyboard(mEtInput);
                    if (mOnPanelShowingListener != null) {
                        mOnPanelShowingListener.onPanelShowing(false);
                    }
                } else { // 都没有显示, 切换至面板
                    refreshPanelHeight();
                    showPanel(mPanel);
                    if (mOnPanelShowingListener != null) {
                        mOnPanelShowingListener.onPanelShowing(true);
                    }
                }
            } else if (v.getId() == mEtInput.getId()) { /// 输入框的点击事件
                if (!isKeyBoardShowing()) {
                    if (lastKeyboardHeight != 0) {
                        refreshPanelHeight(lastKeyboardHeight);
                        (mPanel).setVisibility(View.INVISIBLE);
                    }
                    if (mOnPanelShowingListener != null) {
                        mOnPanelShowingListener.onPanelShowing(false);
                    }
                }
            }
        }

        /**
         * 判断键盘是否正在显示
         */
        private boolean isKeyBoardShowing() {
            Rect rect = new Rect();
            mContentView.getWindowVisibleDisplayFrame(rect);
            return Math.abs(maxBottom - rect.bottom) > 100;
        }

        private void refreshPanelHeight(int height) {
            if (mPanel.getHeight() == height) {
                return;
            }
            Log.w("LshUtils---", "refreshPanelHeight:" + height);
            ViewGroup.LayoutParams layoutParams = mPanel.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        height);
                mPanel.setLayoutParams(layoutParams);
            } else {
                layoutParams.height = height;
                mPanel.requestLayout();
            }
        }

        private void refreshPanelHeight() {
            if (mPanelHeight == PANEL_HEIGHT_WRAP_CONTENT) {
                refreshPanelHeight(PANEL_HEIGHT_WRAP_CONTENT);
            } else if (mPanelHeight == PANEL_HEIGHT_EQUAL_KEYBOARD) {
                refreshPanelHeight(lastKeyboardHeight);
            } else if (mPanelHeight > 0) {
                refreshPanelHeight(mPanelHeight);
            } else {
                refreshPanelHeight(PANEL_HEIGHT_WRAP_CONTENT);
            }
        }
    }

    /**
     * 显示键盘
     */
    public static void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 显示键盘并隐藏面板
     */
    public static void showKeyboardAndHidePanel(View panelLayout, View focusView) {
        panelLayout.setVisibility(View.INVISIBLE);
        showKeyboard(focusView);
    }

    /**
     * 显示面板
     */
    public static void showPanel(View panelLayout) {
        final Activity activity = (Activity) panelLayout.getContext();
        panelLayout.setVisibility(View.VISIBLE);
        if (activity.getCurrentFocus() != null) {
            hideKeyboard(activity.getCurrentFocus());
        }
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hidePanelAndKeyboard(View panelLayout) {
        final Activity activity = (Activity) panelLayout.getContext();
        final View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            hideKeyboard(activity.getCurrentFocus());
            focusView.clearFocus();
        }
        panelLayout.setVisibility(View.GONE);
    }

    public static void clearFocusAndHideKeyboard(Activity activity) {
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            hideKeyboard(activity.getCurrentFocus());
            focusView.clearFocus();
        }
    }

    public static void setPanelHeight(int panelHeight) {
        mPanelHeight = panelHeight;
    }

    public interface OnKeyboardShowingListener {
        void onKeyboardShowing(boolean isShowing);
    }

    public interface OnPanelShowingListener {
        void onPanelShowing(boolean isShowing);
    }
}
