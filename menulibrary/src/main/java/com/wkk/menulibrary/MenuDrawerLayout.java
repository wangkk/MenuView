package com.wkk.menulibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wkk on 2018/7/31.
 * 处理菜单滑出等事件分发功能
 */

public class MenuDrawerLayout extends DrawerLayout {

    private MenuContentLayout menuContentLayout;
    private MenuGroupLayout menuGroupView;
    private float curSlideOffset;
    private float y;
    private View contentView;
    private float OFFSET = 0.5f;

    public MenuDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public MenuDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 该类中需要进行一些偷梁换柱的工作，所以要保证activity已经调用了setContentView（）方法，故初始化工作在onFinishInflate方法中进行
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        //在drawerlayout的子View中筛选到菜单
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof MenuContentLayout) {
                menuContentLayout = (MenuContentLayout) child;
            } else {//否则是内容区域
                contentView = child;
            }
        }
        //移除菜单内容控件
        if (menuContentLayout != null) {
            removeView(menuContentLayout);
        }
        //换上新组合的控件
        menuGroupView = new MenuGroupLayout(menuContentLayout);
        addView(menuGroupView);
        addDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                curSlideOffset = slideOffset;//滑出的百分比
                menuGroupView.setTouchParam(y, curSlideOffset);//如果没有完全打开该代码无效
                //drawer内容区域滑动偏移  如果需要内容区域随着滑动可放开该行代码
//                float contentViewOffset = drawerView.getWidth() * slideOffset / 2;
//                contentView.setTranslationX(contentViewOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.END);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 根据是否完全滑出来分发事件 当完全滑出  事件传递给菜单内容组合新控件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        y = ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP) {//抬起手的时候要相应对应菜单的点击事件
            closeDrawers();
            menuContentLayout.downClick();
            return super.dispatchTouchEvent(ev);
        }
        if (curSlideOffset < OFFSET) {//到0.8就截取其滑动事件
            return super.dispatchTouchEvent(ev);
        } else {
            menuGroupView.setTouchParam(y, curSlideOffset);
        }
        return super.dispatchTouchEvent(ev);
    }
}
