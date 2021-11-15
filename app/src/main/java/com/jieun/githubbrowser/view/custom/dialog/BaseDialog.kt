package com.jieun.githubbrowser.view.custom.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.jieun.githubbrowser.R


/**
 * date 2021-11-13
 * create by jieun
 */
abstract class BaseDialog(mContext: Context) : Dialog(mContext) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = (LinearLayout.LayoutParams.MATCH_PARENT)

        }
        val window: Window? = window
        window?.attributes = layoutParams
    }

}