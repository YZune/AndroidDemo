package com.yzune.ipcdemo


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_first_page.*
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.longToast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_first_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val start = System.currentTimeMillis()
        repeat(3) {
            val tv = MyTextView(context!!)
            tv.mText = "在啦啦啦开发中处理了文字换行的问题"
            val lp = LinearLayout.LayoutParams(MATCH_PARENT, dip(120))
            tv.layoutParams = lp
            tv.backgroundColorResource = R.color.colorAccent
            ll_test.addView(tv)
        }
        val end = System.currentTimeMillis()
        longToast("${end - start} ms")
        // my_text.mText = "在啦啦啦开发中处理了文字换行的问题"
        btn_go2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_firstPageFragment_to_secondPageFragment)
        }

        btn_go3.setOnClickListener {
            //            Navigation.findNavController(view)
//                .navigate(R.id.action_firstPageFragment_to_thirdPageFragment)
            ThirdPageFragment.newInstance("", "").show(fragmentManager!!, null)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
