package com.wkk.menulibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yueban on 2018/7/26.
 */

public class MenuBgView extends View {

    private float OFFSET_RATIO = 1 / 8f;//偏移量
    private float WIDTH_RATIO = 6/5f;//弧度最高点与滑动宽度的宽度比例
    private Paint mPaint;
    private Path mPath;

    public MenuBgView(Context context) {
        this(context, null);
    }

    public MenuBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("widthMeasureSpec", "widthMeasureSpec===" + widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //初始化一个画笔
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
    }

    /**
     * @param y           手指位置Y坐标
     * @param slideOffset 菜单滑动百分比
     */
    public void setTouchParam(float y, float slideOffset) {
        //重置path
        mPath.reset();
        //背景露出部分不包括圆弧的矩形宽高
        float width = getWidth() * slideOffset;
        float height = getHeight();
        //为保证菜单划出宽度  最好从屏幕外一定偏移量开始(0,0)开始的话不美观
        float offsetY = height * OFFSET_RATIO;
        //开始点
        float startX = 0;
        float startY = -offsetY;
        //结束点
        float endX = 0;
        float endY = getHeight();
        //控制点
        float contralX = width * WIDTH_RATIO;
        float contralY = y;
        //划线
        mPath.lineTo(startX, startY);
        mPath.quadTo(contralX, contralY, endX, endY);

        mPath.close();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setColor(Drawable color) {
        if (color instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) color;
            mPaint.setColor(colorDrawable.getColor());
        } else {

        }
    }
}
