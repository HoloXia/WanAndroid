package com.holo.wanandroid.ui.projects

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toHtml
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentProjectsBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ext.showLoginDialog
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.mine.CollectViewModel
import com.holo.wanandroid.util.CacheUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class ProjectsFragment : BaseFragment<FragmentProjectsBinding>() {

    companion object {
        fun newInstance(categoryId: Int = -1): ProjectsFragment {
            val fragment = ProjectsFragment()
            fragment.arguments = bundleOf(Constant.KEY_ID to categoryId)
            return fragment
        }
    }

    private val viewModel: ProjectsViewModel by viewModels()
    private val collectViewModel: CollectViewModel by viewModels()

    private var categoryId: Int = -1
    private lateinit var loads: LoadService
    private lateinit var adapter: ProjectsAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = ProjectsAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getProjectList(categoryId, false) }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_projects_shimmer) {
            viewModel.getProjectList(categoryId)
        }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getProjectList(categoryId) }

        adapter.setOnItemClickListener { _, view, position ->
            val item = adapter.getItem(position)
            startActivity<WebActivity>(
                Constant.KEY_WEB_TITLE to item.title.toHtml(),
                Constant.KEY_WEB_URL to item.link
            )
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            val item = adapter.getItem(position)
            when (view.id) {
                R.id.like_view -> {
                    if (CacheUtil.isLogin()) {
                        if (item.collect) {
                            collectViewModel.unCollect(item.id, position)
                        } else {
                            collectViewModel.collect(item.id, position)
                        }
                    } else {
                        activity?.showLoginDialog()
                    }
                }
            }
        }
    }


    override fun initData() {
        categoryId = arguments?.getInt(Constant.KEY_ID, -1) ?: -1
    }

    override fun lazyLoadData() {
        viewModel.getProjectList(categoryId)
    }

    override fun observeViewModel() {
        observerList(viewModel.projectListResp) { state ->
            parseListState(state, loads, binding.container.refreshLayout, adapter)
        }
        observer(collectViewModel.collectResp, success = {
            adapter.getItem(it).collect = true
            adapter.notifyItemChanged(it)
        })
        observer(collectViewModel.unCollectResp, success = {
            adapter.getItem(it).collect = false
            adapter.notifyItemChanged(it)
        })
    }
}