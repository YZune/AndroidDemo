package com.yzune.ipcdemo

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs
import kotlin.math.max

class FadeCrossPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 1 -> {
                    alpha = max(0f, 1 - abs(position))
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }

}