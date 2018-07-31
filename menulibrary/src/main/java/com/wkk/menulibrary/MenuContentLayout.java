package com.wkk.menulibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yueban on 2018/7/27.
 */

public class MenuContentLayout extends LinearLayout {

    private boolean isOpen;

    public MenuContentLayout(Context context) {
        this(context, null);
    }

    public MenuContentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuContentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //初始化一些属性值 该布局要是纵向的
        setOrientation(VERTICAL);

    }

    /**
     * 随手势的偏移以及当前手势的Y坐标
     *
     * @param y
     * @param slideOffset 滑行偏移
     */
    public void setTouchParam(float y, float slideOffset) {
        //是否完全滑出
        isOpen = slideOffset > 0.8;
        //获取所有的子控件进行偏移
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                childAt.setPressed(false);//默认是没有按压的
                boolean isHover = isOpen && y > childAt.getTop() && y < childAt.getBottom();//完全滑出并且手指在该子View区域
                childAt.setPressed(isHover);
                offsetView(childAt, y, slideOffset);
            }
        }
    }

    /**
     * 设置每一个子View的偏移 制定一个最大的偏移量  然后减去手指Y坐标与子View的中心Y坐标的距离 形成一个阶梯型的偏移
     *
     * @param childAt
     * @param y
     * @param slideOffset
     */
    private void offsetView(View childAt, float y, float slideOffset) {
        //获取view的中心Y坐标
        float centerY = childAt.getTop() + childAt.getHeight() / 2;
        float disrance = Math.abs(y - centerY);
        float scale = disrance / getHeight() * 3;//纵轴距离在整体高度的比值会很小很小，进行适当的扩大以便每个子VIEW横向偏移差明显，3这个倍数可以自己调节
        childAt.setTranslationX(66 - 66 * scale);
    }

    public void downClick() {
        //获取当前press为true的子控件相应点击
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isPressed()) {
                view.performClick();
            }
        }
    }
}
