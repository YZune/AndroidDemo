package com.yzune.ipcdemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintProperties.PARENT_ID
import androidx.constraintlayout.widget.ConstraintProperties.WRAP_CONTENT
import kotlinx.android.synthetic.main.fragment_third_page.*
import org.jetbrains.anko.support.v4.dip
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.MotionEvent
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.dragselectrecyclerview.DragSelectReceiver
import com.afollestad.dragselectrecyclerview.DragSelectTouchListener
import com.afollestad.dragselectrecyclerview.Mode
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onTouch
import java.util.*
import kotlin.collections.HashSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdPageFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val data = (1..90).toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = resources.displayMetrics.widthPixels
        Log.d("宽度", "$width")
//        val textView = TextView(context).apply {
//            text = android.os.Build.BRAND
//            textColor = Color.WHITE
//            backgroundColorResource = R.color.colorAccent
//        }
//        cl_page.addView(textView, 0, ConstraintLayout.LayoutParams(width / 6, WRAP_CONTENT).apply {
//            startToStart = PARENT_ID
//            topToTop = PARENT_ID
//            marginStart = dip(16)
//            topMargin = dip(16)
//        })
//        textView.setOnClickListener {
//            SettingUtil.onViewClicked(SettingUtil.getDeviceType(), context)
//        }
        val adapter = SimpleAdapter(R.layout.item_number, 90, data)
        adapter.bindToRecyclerView(rv_select)
        rv_select.layoutManager = StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL)
        var prePos = -1
        rv_select.positionChangedListener = object : MyRecyclerView.PositionChangedListener {
            override fun changeState(pos: Int, isDown: Boolean) {
                if (prePos != pos || isDown) {
                    Log.d("位置", "$pos")
                    if (pos in 0 until 90) {
                        if (!data.contains(pos + 1)) {
                            data.add(pos + 1)
                            adapter.getViewByPosition(pos, R.id.tv_num)?.backgroundResource =
                                R.drawable.week_selected_bg
                            (adapter.getViewByPosition(pos, R.id.tv_num) as TextView).textColor =
                                Color.WHITE
                        } else {
                            data.remove(pos + 1)
                            adapter.getViewByPosition(pos, R.id.tv_num)?.background = null
                            (adapter.getViewByPosition(pos, R.id.tv_num) as TextView).textColor =
                                Color.BLACK
                        }
                    }
                    if (prePos != pos) {
                        prePos = pos
                    }
                }
            }
        }
    }

    private fun goHuaWeiManager() {
        try {
            val intent = Intent(context?.packageName)
            val comp = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
            intent.component = comp
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun enterWhiteListSetting() {
        try {
            startActivity(getSettingIntent())
        } catch (e: Exception) {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }

    }

    private fun getSettingIntent(): Intent {
        var componentName: ComponentName? = null
        val brand = android.os.Build.BRAND
        when (brand.toLowerCase()) {
            "samsung" -> componentName = ComponentName(
                "com.samsung.android.sm",
                "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity"
            )
            "huawei" -> componentName = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
            "honor" -> componentName = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
            "xiaomi" -> componentName = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            "vivo" -> componentName = ComponentName(
                "com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
            )
            "oppo" -> componentName = ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            )
            "390" -> componentName = ComponentName(
                "com.yulong.android.coolsafe",
                "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity"
            )
            "meizu" -> componentName = ComponentName(
                "com.meizu.safe",
                "com.meizu.safe.permission.SmartBGActivity"
            )
            "oneplus" -> componentName = ComponentName(
                "com.oneplus.security",
                "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
            )
            else -> {
            }
        }
        val intent = Intent()
        if (componentName != null) {
            intent.component = componentName
        } else {
            intent.action = Settings.ACTION_SETTINGS
        }
        return intent
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
