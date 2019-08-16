package com.yzune.ipcdemo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.os.*
import android.util.Log
import android.view.*
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    lateinit var listener: ViewPager.OnPageChangeListener
    var index = 0
    val LAST_INDEX = 2
    val MIN_ALPHA = 0.2f
    var listOpen = false
    var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeOverlayUtils.applyThemeOverlays(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                index = position
                Log.d("滑动", "position: $position")
            }
        }

        view_pager.addOnPageChangeListener(listener)

        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        width = size.x

        view_pager.setOnTouchListener(
            object : View.OnTouchListener {
                var startX = 0f
                var endX = 0f
                override fun onTouch(view: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startX = event.x
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (index != LAST_INDEX) return false
                            if (!listOpen) {
                                if (startX - event.x >= width / 20) {
                                    fl_list.alpha = (startX - event.x - width / 20) * 1f / (width / 2)
                                }
                            }
                            if (fl_list.alpha > MIN_ALPHA) {
                                return true
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            endX = event.x
                            if (index != LAST_INDEX) return false
                            if (!listOpen && startX - endX >= width / 20 && fl_list.alpha > MIN_ALPHA) {
                                listOpen = true
                                ObjectAnimator.ofFloat(fl_list, "alpha", 1f).start()
                                recycler_view.touchable = true
                            }
                            if (fl_list.alpha <= MIN_ALPHA) {
                                dismissRecyclerView()
                            }
                        }
                    }
                    return false
                }
            }
        )

        recycler_view.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_image, parent, false)
                return MyViewHolder(view)
            }

            override fun getItemCount(): Int {
                return 20
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as MyViewHolder).textView.text = "啦啦啦$position"
                // (holder as MyViewHolder).imageView.layoutParams.height = dip(480)
            }

        }

        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun dismissRecyclerView() {
        listOpen = false
        ObjectAnimator.ofFloat(fl_list, "alpha", 0f).start()
        recycler_view.touchable = false
    }

    override fun onDestroy() {
        view_pager.removeOnPageChangeListener(listener)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (listOpen) {
            dismissRecyclerView()
        } else {
            super.onBackPressed()
        }
    }

}
