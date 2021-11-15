package com.jieun.githubbrowser.view.custom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * date 2021-11-13
 * create by jieun
 */
class BaseListAdapter {
    open class Adapter<LIST : Any?, B:ViewDataBinding>(
        @LayoutRes private val layoutResId: Int,
        private val bindingVariableId: Int? = null,
        private val diff: DiffUtil.ItemCallback<LIST>
    ) : ListAdapter<LIST, ViewHolder<*>>(diff) {
        val list = mutableListOf<LIST>()

        fun replaceAll(list: List<LIST>?) {
            list?.let {
                this.list.run {
                    clear()
                    addAll(it)
                }
                submitList(list.toMutableList())
            }
        }

        fun getItemInfo(position: Int): Any = list[position]?:0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> {
          return ViewHolder<B>(layoutResId, parent, bindingVariableId)
        }

        override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
            holder.onBindViewHolder(getItem(position))
        }
    }

    open class ViewHolder<B : ViewDataBinding>(
        @LayoutRes layoutResId: Int,
        parent: ViewGroup,
        private val bindingVariableId: Int?
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    ) {
        protected val binding: B = DataBindingUtil.bind(itemView)!!
        fun onBindViewHolder(item: Any?) {
            try {
                bindingVariableId?.let {
                    binding.setVariable(it, item)
                }
                binding.executePendingBindings()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}