package com.yzune.ipcdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    String mText = "在Android开发中，\nCanvas.drawText不会换行，即使一个很长的字符串也只会显示一行，超出部分会隐藏在屏幕之外.StaticLayout是android中处理文字的一个工具类，StaticLayout 处理了文字换行的问题";
    TextPaint mTextPaint;
    StaticLayout mStaticLayout;

    // use this constructor if creating MyView programmatically
    public MyView(Context context) {
        super(context);
        initLabelView();
    }

    // this constructor is used when created from xml
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLabelView();
    }

    private void initLabelView() {
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mTextPaint.setColor(0xFF000000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Tell the parent layout how big this view would like to be
        // but still respect any requirements (measure specs) that are passed down.

        // determine the width
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (mStaticLayout == null) {
            mStaticLayout = new StaticLayout(mText, mTextPaint, width - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
        }

        // determine the height
        int height;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement;
        } else {
            height = mStaticLayout.getHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightRequirement);
            }
        }

        // Required call: set width and height
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // do as little as possible inside onDraw to improve performance

        // draw the text on the canvas after adjusting for padding
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mStaticLayout.draw(canvas);
        canvas.restore();
    }
}