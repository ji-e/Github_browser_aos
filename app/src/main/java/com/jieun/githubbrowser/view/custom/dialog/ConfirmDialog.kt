package com.jieun.githubbrowser.view.custom.dialog

import android.content.Context

/**
 * date 2021-11-13
 * create by jieun
 *
 * 버튼 두개 다이얼로그
 */
class ConfirmDialog(mContext: Context) : DefaultDialog(mContext) {

    fun setDialog(
        title: String?,
        content: String?,
        cancelTxt: String? = null,
        cancelCallback: (() -> Unit)? = null,
        confirmTxt: String? = null,
        confirmCallback: (() -> Unit)? = null
    ) {
        setTitleContent(title, content)
        setCancel(cancelTxt, cancelCallback)
        setConfirm(confirmTxt, confirmCallback)
        show()
    }

}