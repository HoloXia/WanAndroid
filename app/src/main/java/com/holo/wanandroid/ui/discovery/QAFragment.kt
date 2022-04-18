package com.holo.wanandroid.ui.discovery

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.showToast
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toHtml
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentQaBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ext.showLoginDialog
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.home.ArticleAdapter
import com.holo.wanandroid.ui.mine.CollectViewModel
import com.holo.wanandroid.util.CacheUtil
import com.jaren.lib.view.LikeView
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
@AndroidEntryPoint
class QAFragment : BaseFragment<FragmentQaBinding>() {

    private val viewModel: DiscoveryViewModel by viewModels()
    private val collectViewModel: CollectViewModel by viewModels()

    private lateinit var loads: LoadService

    private lateinit var adapter: ArticleAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = ArticleAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.setOnItemChildClickListener { _, view, position ->
            val item = adapter.getItem(position)
            when (view.id) {
                R.id.tv_author -> {
                }
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
        adapter.setOnItemClickListener { _, view, position ->
            val item = adapter.getItem(position)
            startActivity<WebActivity>(
                Constant.KEY_WEB_TITLE to item.title.toHtml(),
                Constant.KEY_WEB_URL to item.link
            )
        }

        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getQAList(false) }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getQAList() }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_article_shimmer) {
            viewModel.getQAList()
        }
    }

    override fun lazyLoadData() {
        viewModel.getQAList()
    }

    override fun observeViewModel() {
        observerList(viewModel.qaListResp) { state ->
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