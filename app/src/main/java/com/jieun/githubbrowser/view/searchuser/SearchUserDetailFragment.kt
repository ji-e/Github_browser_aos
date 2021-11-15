package com.jieun.githubbrowser.view.searchuser

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jieun.githubbrowser.BaseFragment
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.FragmentSearchUserDetailBinding
import com.jieun.githubbrowser.view.MainActivity
import com.jieun.githubbrowser.view.custom.adapter.SearchUserDetailListAdapter
import com.jieun.githubbrowser.view.custom.dialog.LoadingDialog
import com.jieun.githubbrowser.view.custom.dialog.SingleDialog
import com.jieun.githubbrowser.viewmodel.SearchUserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 사용자 정보 상세
 */
class SearchUserDetailFragment : BaseFragment<FragmentSearchUserDetailBinding>() {
    override fun getLayoutId() = R.layout.fragment_search_user_detail

    private val viewModel: SearchUserViewModel by sharedViewModel()

    private val mainActivity: MainActivity? by lazy { activity as? MainActivity }
    private val mContext: Context by lazy { this.requireContext() }

    private val username: String? by lazy { arguments?.getString("username") }

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(mContext) }

    override fun initStartView() {
        binding.run {
            searchUserVM = viewModel
            rootView = this@SearchUserDetailFragment
        }

        binding.searchUserDetailRecyclerView.apply {
            val searchUserDetailListAdapter = SearchUserDetailListAdapter.Adapter()
            adapter = searchUserDetailListAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                val lastVisibleItemPosition = (this.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = searchUserDetailListAdapter.itemCount - 1

                // 최하단 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    viewModel.getPagingUserEvents(username.toString())
                }
            }
        }

        binding.searchUserDetailSwipeRefresh.setOnRefreshListener {
            binding.searchUserDetailRecyclerView.visibility = View.GONE
            viewModel.getUserDetail(username.toString())
        }
    }

    override fun initDataBinding() {
        viewModel.getUserDetail(username.toString())
    }

    override fun initAfterBinding() {
        with(viewModel) {
            liveErrorDialog.observe(viewLifecycleOwner, {
                it?.let { errorMsg ->
                    SingleDialog(mContext).setDialog(
                        mContext.getString(R.string.notice),
                        errorMsg
                    )
                    setErrorDialog(null)
                }
            })

            liveLoading.observe(viewLifecycleOwner, {
                if (it && !binding.searchUserDetailSwipeRefresh.isRefreshing) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                    binding.searchUserDetailSwipeRefresh.isRefreshing = false
                }
            })

            liveUserDetail.observe(viewLifecycleOwner, {
                binding.searchUserDetailRecyclerView.visibility = View.VISIBLE
                binding.searchUserDetailSwipeRefresh.isRefreshing = false
            })
        }
    }

    fun onClickSearchUserDetailFragment(view: View?) {
        when (view) {
            binding.searchUserDetailImgBack -> mainActivity?.onBackPressed()
        }
    }
}