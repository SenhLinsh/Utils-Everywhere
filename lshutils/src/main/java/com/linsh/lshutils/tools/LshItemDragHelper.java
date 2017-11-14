package com.linsh.lshutils.tools;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : RecyclerView Item 拖动排序 / 滑动删除动画的帮助类
 * </pre>
 */
public class LshItemDragHelper extends ItemTouchHelper {

    /**
     * 使用接口模式, 可以通过 Adapter 实现 IItemDragCallback 接口来让其在Adapter内部进行处理
     */
    public LshItemDragHelper(IItemDragCallback callback) {
        super(new CallbackImpl(callback));
    }

    /**
     * 使用继承模式, 可以在Adapter里面创建内部类来实现, 方便获取更多的方法来实现更多的功能
     */
    public LshItemDragHelper(ItemDragCallback callback) {
        super(callback);
    }

    public abstract static class ItemDragCallback extends Callback {

        @Override
        public abstract boolean isItemViewSwipeEnabled();

        @Override
        public abstract boolean isLongPressDragEnabled();

        /**
         * 当用户拖拽或者滑动Item时, 告诉系统ItemTouchHelper滑动或者拖拽的方向
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag;
            int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                // 网格布局只有拖拽没有滑动删除
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = 0;
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int orientation = linearLayoutManager.getOrientation();
                // 根据RecyclerView方向判断拖拽方向
                if (orientation == LinearLayoutManager.VERTICAL) {
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                } else {
                    dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
            } else {
                // 不知道情况的, 上下左右都能拖动吧
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }

        /**
         * 每次进行拖拽排序时, ItemTouchHelper 都会一直在进行相邻两个item的替换操作, 所以方法里面不宜进行耗时操作
         */
        @Override
        public abstract boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);

        @Override
        public abstract void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }

    public interface IItemDragCallback {

        boolean isItemViewSwipeEnabled();

        boolean isLongPressDragEnabled();

        /**
         * 拖拽 Item 时, 进行两个相邻 Item 的互换操作
         *
         * @param recyclerView recyclerView
         * @param fromPosition 被用户拖动 Item 的开始位置
         * @param toPosition   被用户拖动 Item 的结束位置
         * @return
         */
        boolean onMove(RecyclerView recyclerView, int fromPosition, int toPosition);

        /**
         * 拖拽 Item 后
         *
         * @param recyclerView recyclerView
         * @param fromPosition 被用户拖动 Item 的开始位置
         * @param toPosition   被用户拖动 Item 的结束位置
         */
        void onMoved(RecyclerView recyclerView, int fromPosition, int toPosition);

        /**
         * 用户执行滑动删除后
         *
         * @param position  滑动删除 Item 的位置
         * @param direction 滑动方向; 值为以下其中一个:
         *                  <br>{@link #UP}, {@link #DOWN}, {@link #LEFT}, {@link #RIGHT}
         */
        void onSwiped(int position, int direction);
    }

    private static class CallbackImpl extends ItemDragCallback {

        private IItemDragCallback mICallback;

        public CallbackImpl(IItemDragCallback callback) {
            if (callback == null) {
                throw new RuntimeException("IItemDragCallback must not be null");
            }
            mICallback = callback;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return mICallback.isItemViewSwipeEnabled();
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return mICallback.isLongPressDragEnabled();
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return super.getMovementFlags(recyclerView, viewHolder);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return mICallback.onMove(recyclerView, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            mICallback.onMoved(recyclerView, fromPos, toPos);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mICallback.onSwiped(viewHolder.getAdapterPosition(), direction);
        }
    }
}
