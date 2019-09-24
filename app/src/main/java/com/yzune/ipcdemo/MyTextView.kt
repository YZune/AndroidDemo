package com.yzune.ipcdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.text.StaticLayout
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.text.Layout
import android.text.TextPaint
import org.jetbrains.anko.dip
import kotlin.math.min


class MyTextView(
    context: Context, attributeSet: AttributeSet?, defStyleAttr: Int
) : View(context, attributeSet, defStyleAttr) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private var mText =
        "在啦啦啦开发中处理了文字换行的问题"

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12 * resources.displayMetrics.density
        color = Color.WHITE
    }
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
    }
    private val path = Path()
    private var mStaticLayout: StaticLayout? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Tell the parent layout how big this view would like to be
        // but still respect any requirements (measure specs) that are passed down.

        // determine the width
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (mStaticLayout == null) {
            mStaticLayout = StaticLayout(
                mText,
                mTextPaint,
                width - paddingRight,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0f,
                false
            )
        }

        // determine the height
        var height: Int
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightRequirement = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement
        } else {
            height = mStaticLayout!!.height + paddingTop + paddingBottom
            if (heightMode == MeasureSpec.AT_MOST) {
                height = min(height, heightRequirement)
            }
        }

        // Required call: set width and height
        setMeasuredDimension(width, height)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // do as little as possible inside onDraw to improve performance
        path.moveTo(width - dip(12).toFloat(), height - dip(6).toFloat()) // 此点为多边形的起点
        path.lineTo(width - dip(6).toFloat(), height - dip(6).toFloat())
        path.lineTo(width - dip(6).toFloat(), height - dip(12).toFloat())
        path.close() // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint)
        // draw the text on the canvas after adjusting for padding
        canvas.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        mStaticLayout!!.draw(canvas)
        canvas.restore()
    }
}