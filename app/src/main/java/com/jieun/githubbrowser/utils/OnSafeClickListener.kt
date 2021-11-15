package com.jieun.githubbrowser.utils

import android.view.View

/**
 * date 2021-11-13
 * create by jieun
 *
 * 중복 클릭 방지
 */
class OnSafeClickListener(
    private val clickListener: View.OnClickListener,
) : View.OnClickListener {
    private var canClick = true

    override fun onClick(v: View?) {
        if (canClick) {
            v?.run {
                canClick = false
                postDelayed({ canClick = true }, MIN_CLICK_INTERVAL)
                clickListener.onClick(v)
            }
        }
    }

    companion object {
        // 중복 클릭 방지 시간 설정
        private val MIN_CLICK_INTERVAL: Long = 600
    }
}