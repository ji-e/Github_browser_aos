package com.jieun.githubbrowser.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jieun.githubbrowser.view.custom.adapter.BaseListAdapter
import com.jieun.githubbrowser.view.custom.adapter.SearchUserDetailListAdapter
import java.text.SimpleDateFormat
import java.util.*


/**
 * date 2021-11-13
 * create by jieun
 */

@BindingAdapter("imgUrl", "err")
fun ImageView.loadImage(imgUrl: String?, err: Drawable) {
    Glide.with(context)
        .load(imgUrl)
        .error(err)
        .into(this)
}

@BindingAdapter("date", "pattern")
fun TextView.setDateText(date: Any?, pattern: String) {
    try {
        if (date is Date) {
            this.text = SimpleDateFormat(pattern, Locale.getDefault()).format(date)
        } else {
            this.text = ""
        }
    } catch (e: Exception) {
        this.text = ""
    }
}

@BindingAdapter("listAdapter")
fun RecyclerView.setListAdapter(list: List<Nothing>?) {
    (this.adapter as? BaseListAdapter.Adapter<*, *>)?.run {
        this.replaceAll(list)
    }
}


@BindingAdapter("searchUserDetailListAdapter")
fun RecyclerView.setSearchUserDetailListAdapter(list: List<Nothing>?) {
    (this.adapter as? SearchUserDetailListAdapter.Adapter)?.run {
        this.replaceAll(list)
    }
}

@BindingAdapter("onSafeClick")
fun View.setOnSafeClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSafeClickListener(it))
    } ?: setOnClickListener(null)
}




