package com.yzune.ipcdemo

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.iv_bg
import kotlinx.android.synthetic.main.activity_main2.iv_blur
import kotlinx.android.synthetic.main.activity_main2.view_pager
import kotlin.math.abs

class Main2Activity : AppCompatActivity() {

    lateinit var listener: ViewPager.OnPageChangeListener

    var index = 0
    var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        Glide.with(this).load(R.drawable.pic1).into(iv_bg)
        Glide.with(this)
            .load(R.drawable.pic1)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 5)))
            .into(iv_blur)

        view_pager.adapter =
            object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getItem(position: Int): Fragment {
                    return BlankFragment.newInstance(position + 1)
                }

                override fun getCount(): Int {
                    return 3
                }

            }

        listener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.d("滑动state", "$state")
                if (state == 0) {
                    ObjectAnimator.ofFloat(iv_blur, "alpha", if (index == 1) 0f else 1f).start()
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.d("滑动", "position: $position, offset: $positionOffset, pixels: $positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
//                if (index != position) {
//                    ObjectAnimator.ofFloat(iv_blur, "alpha", if (position == 1) 0f else 1f).start()
//                }
                Log.d("滑动", "position: $position")
                ObjectAnimator.ofFloat(iv_blur, "alpha", if (position == 1) 0f else 1f).start()
                index = position
            }
        }

        view_pager.addOnPageChangeListener(listener)

        view_pager.currentItem = 1

        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        width = size.x

        view_pager.setOnTouchListener(
            object : View.OnTouchListener {
                var startX = 0f
                override fun onTouch(view: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startX = event.x
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (index == 1) {
                                if (abs(startX - event.x) >= width / 20) {
                                    iv_blur.alpha = (abs(startX - event.x) - width / 20) * 1f / (width / 2)
                                }
                            } else if (index == 2) {
                                if (event.x - startX >= width / 20) {
                                    iv_blur.alpha = 1 - (event.x - startX - width / 20) * 1f / (width / 2)
                                }
                            } else if (index == 0) {
                                if (startX - event.x >= width / 20) {
                                    iv_blur.alpha = 1 - (startX - event.x - width / 20) * 1f / (width / 2)
                                }
                            }
                        }
                    }
                    return false
                }
            }
        )
    }

    override fun onDestroy() {
        view_pager.removeOnPageChangeListener(listener)
        super.onDestroy()
    }
}
