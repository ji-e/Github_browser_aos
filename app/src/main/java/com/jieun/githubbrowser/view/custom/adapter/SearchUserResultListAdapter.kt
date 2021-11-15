package com.jieun.githubbrowser.view.custom.adapter

import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.ItemSearchUserInfoBinding
import com.jieun.githubbrowser.databinding.ItemSearchUserLoadingBinding
import com.jieun.githubbrowser.databinding.ItemSearchUserResultBinding
import com.jieun.githubbrowser.model.data.SearchUserData
import com.jieun.githubbrowser.utils.SearchUserResultViewType

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 사용자 찾기 ListAdapter
 */
class SearchUserResultListAdapter :
    BaseListAdapter.Adapter<SearchUserData.SearchUserItemData?, ItemSearchUserResultBinding>(
        layoutResId = R.layout.item_search_user_result,
        bindingVariableId = BR.searchUserItem,
        diff = object : DiffUtil.ItemCallback<SearchUserData.SearchUserItemData?>() {
            override fun areItemsTheSame(
                oldItem: SearchUserData.SearchUserItemData,
                newItem: SearchUserData.SearchUserItemData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchUserData.SearchUserItemData,
                newItem: SearchUserData.SearchUserItemData
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {
    override fun getItemViewType(position: Int) = when (list[position]) {
        is SearchUserData.SearchUserItemData -> SearchUserResultViewType.CONTENT
        null -> SearchUserResultViewType.LOADING
        else -> throw IllegalArgumentException("Invalid view type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListAdapter.ViewHolder<*> {
        return when (viewType) {
            SearchUserResultViewType.LOADING -> {
                BaseListAdapter.ViewHolder<ItemSearchUserLoadingBinding>(
                    R.layout.item_search_user_loading,
                    parent,
                    BR.searchUserLoadingItem
                )
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }


    /** 아이템 클릭 리스너 -------------------*/
    interface OnItemClickListener {
        fun onItemClick(nickname: String)
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
            onItemClickListener?.onItemClick(getItem(holder.bindingAdapterPosition)?.login.toString())
        }
    }
}