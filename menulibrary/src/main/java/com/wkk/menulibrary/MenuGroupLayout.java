package com.wkk.menulibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by yueban on 2018/7/27.
 * 组合控件  将背景控件和内容空间组合在一起作为新的内容控件替换
 */

public class MenuGroupLayout extends RelativeLayout {

    private MenuBgView menuBgView;
    private MenuContentLayout menuContentLayout;

    public MenuGroupLayout(MenuContentLayout menuContentLayout) {
        this(menuContentLayout.getContext());
        init(menuContentLayout);
    }

    public MenuGroupLayout(Context context) {
        super(context);
    }

    public MenuGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(MenuContentLayout menuContentLayout) {
        this.menuContentLayout = menuContentLayout;
        menuBgView = new MenuBgView(getContext());
        //复制内容控件的数据参数作为组合控件的参数
        ViewGroup.LayoutParams layoutParams = menuContentLayout.getLayoutParams();
        setLayoutParams(layoutParams);
        //偷取内容背景作为菜单view的背景
        menuBgView.setColor(menuContentLayout.getBackground());
        //把原内容控件的背景透明
        menuContentLayout.setBackgroundColor(Color.TRANSPARENT);
        addView(menuBgView, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(menuContentLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 根据手势传入Y坐标以及便宜百分比
     *
     * @param y
     * @param slideOffset
     */
    public void setTouchParam(float y, float slideOffset) {
        menuBgView.setTouchParam(y, slideOffset);
        menuContentLayout.setTouchParam(y, slideOffset);
    }
}
