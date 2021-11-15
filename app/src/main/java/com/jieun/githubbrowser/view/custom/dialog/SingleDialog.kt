package com.jieun.githubbrowser.view.custom.dialog

import android.content.Context

/**
 * date 2021-11-13
 * create by jieun
 *
 * 버튼 한개 다이얼로그
 */
class SingleDialog(mContext: Context) : DefaultDialog(mContext) {

    fun setDialog(
        title: String?,
        content: String?,
        confirmTxt: String? = null,
        confirmCallback: (() -> Unit)? = null
    ) {
        setTitleContent(title, content)
        setConfirm(confirmTxt, confirmCallback)
        setSingleDialog()
        show()
    }

}