package com.yzune.ipcdemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var textView = view.find<TextView>(R.id.tv_item)

}