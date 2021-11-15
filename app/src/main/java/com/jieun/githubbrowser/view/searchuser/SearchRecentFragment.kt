package com.jieun.githubbrowser.view.searchuser

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import com.jieun.githubbrowser.BaseFragment
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.FragmentSearchRecentBinding
import com.jieun.githubbrowser.model.data.SearchRecentData
import com.jieun.githubbrowser.view.MainActivity
import com.jieun.githubbrowser.view.custom.adapter.SearchRecentListAdapter
import com.jieun.githubbrowser.view.custom.adapter.SearchUserResultListAdapter
import com.jieun.githubbrowser.view.custom.dialog.ConfirmDialog
import com.jieun.githubbrowser.view.custom.dialog.SingleDialog
import com.jieun.githubbrowser.viewmodel.SearchUserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * date 2021-11-14
 * create by jieun
 *
 * 최근 검색어 목록
 */
class SearchRecentFragment : BaseFragment<FragmentSearchRecentBinding>() {
    override fun getLayoutId() = R.layout.fragment_search_recent

    private val viewModel: SearchUserViewModel by sharedViewModel()

    private val mainActivity: MainActivity? by lazy { activity as? MainActivity }
    private val mContext: Context by lazy { this.requireContext() }

    override fun initStartView() {
        binding.run {
            searchUserVM = viewModel
            rootView = this@SearchRecentFragment
        }

        binding.searchRecentRecyclerView.apply {
            val searchRecentListAdapter = SearchRecentListAdapter().apply {
                setOnItemClickListener(object : SearchRecentListAdapter.OnItemClickListener {
                    override fun onItemClick(
                        isDelete: Boolean,
                        searchRecentData: SearchRecentData?
                    ) {
                        if (isDelete) {
                            searchRecentData?.let { viewModel.deleteSearchRecent(it) }
                        } else {
                            searchRecentData?.let { viewModel.setKeyword(it) }
                        }
                    }
                })
            }
            adapter = searchRecentListAdapter
        }
    }

    override fun initDataBinding() {
        viewModel.getSearchRecent()
    }

    override fun initAfterBinding() {}

    fun onClickSearchRecentFragment(view: View?) {
        when (view) {
            binding.searchUserRecentTvDeleteAll -> showSearchRecentDeleteDialog()
        }
    }

    /** 전체 삭제 안내 팝업*/
    private fun showSearchRecentDeleteDialog() {
        ConfirmDialog(mContext).setDialog(
            mContext.getString(R.string.notice),
            mContext.getString(R.string.search_user_recent_delete_msg),
            cancelCallback = {},
            confirmCallback = {
                viewModel.deleteAllSearchRecent()
            }
        )
    }
}