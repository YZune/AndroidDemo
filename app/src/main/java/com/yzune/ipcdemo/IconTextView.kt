package com.yzune.ipcdemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View

@SuppressLint("ViewConstructor")
class IconTextView(val icon: String, context: Context) : View(context) {

    companion object {
        const val NAV = "\uE6A7"
        const val CROP = "\uE6A8"
        const val LOCATION = "\uE6B2"
        const val BACK = "\uE6B5"
        const val SEND = "\uE6B6"
        const val SHARE = "\uE6BA"
        const val MORE = "\uE6BF"
        const val CLOSE = "\uE6C0"
        const val FAV = "\uE6C2"
        const val LIGHT = "\uE6C7"
        const val EXPLORE = "\uE6C8"
        const val SORT = "\uE6C9"
        const val DATE = "\uE6CD"
        const val DELETE = "\uE6CE"
        const val UPLOAD = "\uE6CF"
        const val CLOCK = "\uE6D2"
        const val SETTING = "\uE6D3"
        const val SEARCH = "\uE6D4"
        const val INFO = "\uE6D6"
        const val REFRESH = "\uE6D7"
        const val PIC = "\uE6DB"
        const val ADD = "\uE6DC"
        const val ALARM = "\uE6DD"
        const val DONE = "\uE6DE"
        const val DOC = "\uE6DF"
        const val MSG = "\uE6E1"
        const val DOWNLOAD = "\uE6E2"
        const val STAR = "\uE6E3"
        const val PERSON = "\uE6EB"
        const val FONT = "\uE6F0"
    }
}