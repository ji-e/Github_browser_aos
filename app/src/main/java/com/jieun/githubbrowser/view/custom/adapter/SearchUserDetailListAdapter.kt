package com.jieun.githubbrowser.view.custom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.*
import com.jieun.githubbrowser.model.data.*
import com.jieun.githubbrowser.utils.LogUtil
import com.jieun.githubbrowser.utils.SearchUserDetailViewType
import java.net.URL
import kotlin.coroutines.coroutineContext


/**
 * date 2021-11-14
 * create by jieun
 *
 * Github 사용자 상세 정보 RecyclerViewAdapter
 */
class SearchUserDetailListAdapter {
    open class Adapter : ListAdapter<UserDetail?, ViewHolder<*>>(
        object : DiffUtil.ItemCallback<UserDetail?>() {
            override fun areItemsTheSame(
                oldItem: UserDetail,
                newItem: UserDetail
            ): Boolean {
                return when {
                    oldItem is UserDetailTitleData && newItem is UserDetailTitleData -> {
                        oldItem.title == newItem.title
                    }
                    oldItem is UserDetailEmptyData && newItem is UserDetailEmptyData -> {
                        oldItem.content == newItem.content
                    }
                    oldItem is UserInfoData && newItem is UserInfoData -> {
                        oldItem.id == newItem.id
                    }
                    oldItem is UserReposData && newItem is UserReposData -> {
                        oldItem.id == newItem.id
                    }
                    oldItem is UserEventsData && newItem is UserEventsData -> {
                        oldItem.id == newItem.id
                    }
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: UserDetail,
                newItem: UserDetail
            ): Boolean {
                return when {
                    oldItem is UserDetailTitleData && newItem is UserDetailTitleData -> {
                        oldItem == newItem
                    }
                    oldItem is UserDetailEmptyData && newItem is UserDetailEmptyData -> {
                        oldItem == newItem
                    }
                    oldItem is UserInfoData && newItem is UserInfoData -> {
                        oldItem == newItem
                    }
                    oldItem is UserReposData && newItem is UserReposData -> {
                        oldItem == newItem
                    }
                    oldItem is UserEventsData && newItem is UserEventsData -> {
                        oldItem == newItem
                    }
                    else -> false
                }
            }
        }
    ) {
        private val list = mutableListOf<UserDetail?>()
        fun replaceAll(list: List<UserDetail?>?) {
            list?.let {
                this.list.run {
                    clear()
                    addAll(it)
                }
            }
            submitList(list?.toMutableList())
        }

        override fun getItemViewType(position: Int) = when (list[position]) {
            is UserDetailTitleData -> SearchUserDetailViewType.USER_TITLE
            is UserDetailEmptyData -> SearchUserDetailViewType.USER_EMPTY
            is UserInfoData -> SearchUserDetailViewType.USER_INFO
            is UserReposData -> SearchUserDetailViewType.USER_REPO
            is UserEventsData -> SearchUserDetailViewType.USER_EVENT
            null -> SearchUserDetailViewType.LOADING
            else -> throw IllegalArgumentException("Invalid view type")
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> {

            return when (viewType) {
                SearchUserDetailViewType.USER_TITLE -> {
                    ViewHolder<ItemSearchUserInfoBinding>(
                        R.layout.item_search_user_title,
                        parent,
                        BR.searchUserTitleItem
                    )
                }
                SearchUserDetailViewType.USER_EMPTY -> {
                    ViewHolder<ItemSearchUserInfoBinding>(
                        R.layout.item_search_user_empty,
                        parent,
                        BR.searchUserEmptyItem
                    )
                }
                SearchUserDetailViewType.USER_INFO -> {
                    ViewHolder<ItemSearchUserInfoBinding>(
                        R.layout.item_search_user_info,
                        parent,
                        BR.searchUserInfoItem
                    )
                }
                SearchUserDetailViewType.USER_REPO -> {
                    ViewHolder<ItemSearchUserReposBinding>(
                        R.layout.item_search_user_repos,
                        parent,
                        BR.searchUserReposItem
                    )
                }
                SearchUserDetailViewType.USER_EVENT -> {
                    ViewHolder<ItemSearchUserEventBinding>(
                        R.layout.item_search_user_event,
                        parent,
                        BR.searchUserEventsItem
                    )
                }
                SearchUserDetailViewType.LOADING -> {
                    ViewHolder<ItemSearchUserEventBinding>(
                        R.layout.item_search_user_loading,
                        parent,
                        BR.searchUserLoadingItem
                    )
                }
                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
            val item = list[position]
            when (holder.binding) {
                is ItemSearchUserTitleBinding -> holder.onBindViewHolder(item as UserDetailTitleData)
                is ItemSearchUserEmptyBinding -> holder.onBindViewHolder(item as UserDetailEmptyData)
                is ItemSearchUserInfoBinding -> holder.onBindViewHolder(item as UserInfoData)
                is ItemSearchUserReposBinding -> holder.onBindViewHolder(item as UserReposData)
                is ItemSearchUserEventBinding -> holder.onBindViewHolder(item as UserEventsData)
                is ItemSearchUserLoadingBinding -> holder.onBindViewHolder(item)
                else -> throw IllegalArgumentException()
            }
        }

        override fun getItemCount(): Int = list.size

    }

    open class ViewHolder<B : ViewDataBinding>(
        @LayoutRes layoutResId: Int,
        parent: ViewGroup,
        private val bindingVariableId: Int?
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    ) {

        val binding: B = DataBindingUtil.bind(itemView)!!

        fun onBindViewHolder(item: Any?) {
            item ?: return
            try {
                bindingVariableId?.let {
                    binding.setVariable(it, item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}