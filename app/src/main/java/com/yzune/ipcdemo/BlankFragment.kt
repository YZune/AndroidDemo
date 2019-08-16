package com.yzune.ipcdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import kotlinx.android.synthetic.main.fragment_blank.*

class BlankFragment : Fragment() {

    private var param = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getInt("param")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_text.text = "$param"
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putInt("param", param1)
                }
            }
    }
}
