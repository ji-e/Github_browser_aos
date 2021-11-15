package com.jieun.githubbrowser.view.custom.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.ItemSearchRecentBinding
import com.jieun.githubbrowser.model.data.SearchRecentData


/**
 * date 2021-11-14
 * create by jieun
 *
 * 최근 검색어 ListAdapter
 */
class SearchRecentListAdapter :
    BaseListAdapter.Adapter<SearchRecentData?, ItemSearchRecentBinding>(
        layoutResId = R.layout.item_search_recent,
        bindingVariableId = BR.searchRecentItem,
        diff = object : DiffUtil.ItemCallback<SearchRecentData?>() {
            override fun areItemsTheSame(
                oldItem: SearchRecentData,
                newItem: SearchRecentData
            ): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(
                oldItem: SearchRecentData,
                newItem: SearchRecentData
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {


    /** 아이템 클릭 리스너 -------------------*/
    interface OnItemClickListener {
        fun onItemClick(isDeleteBtn:Boolean, searchRecentData: SearchRecentData?)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    /** -------------------아이템 클릭 리스너 */


    override fun onBindViewHolder(
        holder: BaseListAdapter.ViewHolder<*>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(false, getItem(holder.bindingAdapterPosition))
        }
        (holder.itemView.findViewById<ImageView>(R.id.item_search_recent_img_delete))?.setOnClickListener {
            onItemClickListener?.onItemClick(true, getItem(holder.bindingAdapterPosition))
        }
    }
}