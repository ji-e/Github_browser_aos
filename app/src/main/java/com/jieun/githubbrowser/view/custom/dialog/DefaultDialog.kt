package com.jieun.githubbrowser.view.custom.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.jieun.githubbrowser.databinding.DialogDefaultBinding


/**
 * date 2021-11-13
 * create by jieun
 */
open class DefaultDialog(private val mContext: Context) : BaseDialog(mContext) {
    var binding: DialogDefaultBinding = DialogDefaultBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding.run {
            lifecycleOwner = mContext as LifecycleOwner
            setContentView(binding.root)
        }

        super.onCreate(savedInstanceState)
    }

    /** 제목 및 내용 설정*/
    fun setTitleContent(title: String?, content: String?) {
        title?.let { binding.defaultDialogTvTitle.text = it }
        content?.let { binding.defaultDialogTvContent.text = it }
    }

    /** 취소 버튼 설정*/
    fun setCancel(txt: String?, callback: (() -> Unit)?) {
        txt?.let { binding.defaultDialogBtnCancel.text = it }
        callback?.let { cb ->
            binding.defaultDialogBtnCancel.setOnClickListener {
                cb.invoke()
                dismiss()
            }
        }
    }

    /** 확인 버튼 설정*/
    fun setConfirm(txt: String?, callback: (() -> Unit)?) {
        txt?.let { binding.defaultDialogBtnConfirm.text = it }
        binding.defaultDialogBtnConfirm.setOnClickListener {
            callback?.invoke()
            dismiss()
        }
    }

    /** 취소 버튼 숨김 처리*/
    fun setSingleDialog() {
        binding.defaultDialogBtnCancel.visibility = View.GONE
    }
}