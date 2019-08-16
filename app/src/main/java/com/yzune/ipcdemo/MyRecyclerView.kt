package com.yzune.ipcdemo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerView(context: Context, attr: AttributeSet?, int: Int) : RecyclerView(context, attr, int) {

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    var touchable = false

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (!touchable) return false
        return super.onTouchEvent(e)
    }
}