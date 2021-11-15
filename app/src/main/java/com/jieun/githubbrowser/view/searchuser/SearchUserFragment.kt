package com.jieun.githubbrowser.view.searchuser

import android.content.Context
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.jieun.githubbrowser.BaseFragment
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.FragmentSearchUserBinding
import com.jieun.githubbrowser.model.data.SearchRecentData
import com.jieun.githubbrowser.utils.SearchUserResultViewType
import com.jieun.githubbrowser.view.MainActivity
import com.jieun.githubbrowser.view.custom.adapter.SearchUserResultListAdapter
import com.jieun.githubbrowser.view.custom.dialog.LoadingDialog
import com.jieun.githubbrowser.view.custom.dialog.SingleDialog
import com.jieun.githubbrowser.viewmodel.SearchUserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 사용자 찾기 & 목록
 */
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>() {
    override fun getLayoutId() = R.layout.fragment_search_user

    private val viewModel: SearchUserViewModel by sharedViewModel()

    private val mainActivity: MainActivity? by lazy { activity as? MainActivity }
    private val mContext: Context by lazy { this.requireContext() }

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(mContext) }

    private var isSearchClick = false       // 검색 확인 플래그
    private var nickname = ""               // 검색어

    override fun initStartView() {
        binding.run {
            searchUserVM = viewModel
        }

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.search_user_frame_lt, SearchRecentFragment()).commit()

        binding.searchUserEdtUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isSearchClick = true
                nickname = query.toString()
                if (nickname.trim().isNotEmpty()) {
                    // 사용자 검색
                    viewModel.insertSearchRecent(SearchRecentData(nickname))
                    binding.searchUserFrameLt.visibility = View.GONE
                    binding.searchUserEdtUser.clearFocus()
                    mainActivity?.hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.trim()?.isEmpty() == true) {
                    binding.searchUserFrameLt.visibility = View.VISIBLE
                }
                return true
            }
        })

        binding.searchUserRecyclerView.apply {
            val searchUserResultListAdapter = SearchUserResultListAdapter().apply {
                setOnItemClickListener(object : SearchUserResultListAdapter.OnItemClickListener {
                    override fun onItemClick(nickname: String) {
                        // 사용자 상세 화면으로 이동
                        mainActivity?.replaceFragment(
                            R.id.frame_lt,
                            SearchUserDetailFragment().apply {
                                arguments = bundleOf("username" to nickname)
                            })
                    }
                })
            }
            adapter = searchUserResultListAdapter

            setOnScrollChangeListener { _, _, _, _, _ ->
                val lastVisibleItemPosition = (this.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = searchUserResultListAdapter.itemCount - 1

                // 최하단 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    viewModel.getSearchUser(nickname, false)
                }
            }

            // 하단 로딩 span
            val manager = GridLayoutManager(mContext, 2)
            manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (searchUserResultListAdapter.getItemViewType(position) == SearchUserResultViewType.LOADING) 2 else 1
                }
            }
            layoutManager = manager
        }

        binding.searchUserSwipeRefresh.setOnRefreshListener {
            viewModel.getSearchUser(nickname, true)
        }
    }

    override fun initDataBinding() {}

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
                if (it && !binding.searchUserSwipeRefresh.isRefreshing) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                    binding.searchUserSwipeRefresh.isRefreshing = false
                }
            })

            liveSearchUserResult.observe(viewLifecycleOwner, {
                if (isSearchClick) {
                    binding.searchUserRecyclerView.postDelayed({
                        binding.searchUserRecyclerView.scrollToPosition(0)
                    }, 200)
                }
                isSearchClick = false
                binding.searchUserSwipeRefresh.isRefreshing = false
                binding.searchUserFrameLt.visibility = View.GONE
            })

            liveKeyword.observe(viewLifecycleOwner,{
                it?.let {
                    binding.searchUserEdtUser.setQuery(it, true)
                    setKeyword(null)
                }
            })
        }
    }
}