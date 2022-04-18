package com.holo.wanandroid.ui.discovery.tree

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toHtml
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentTreeInfoBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.discovery.DiscoveryViewModel
import com.holo.wanandroid.ui.home.ArticleAdapter
import com.jaren.lib.view.LikeView
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/17
 */
@AndroidEntryPoint
class TreeInfoFragment : BaseFragment<FragmentTreeInfoBinding>() {

    companion object {
        fun newInstance(treeId: Int): TreeInfoFragment {
            val fragment = TreeInfoFragment()
            fragment.arguments = bundleOf(Constant.KEY_ID to treeId)
            return fragment
        }
    }

    private val viewModel: DiscoveryViewModel by viewModels()

    private var treeId: Int = 0
    private lateinit var loads: LoadService
    private lateinit var adapter: ArticleAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = ArticleAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tv_author -> {
                }
                R.id.like_view -> {
                    if (view is LikeView) {
                        view.toggle()
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

        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getTreeChildList(treeId, false) }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getTreeChildList(treeId) }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_article_shimmer) {
            viewModel.getTreeChildList(treeId)
        }
    }

    override fun initData() {
        treeId = arguments?.getInt(Constant.KEY_ID, 0) ?: 0
    }

    override fun lazyLoadData() {
        viewModel.getTreeChildList(treeId)
    }

    override fun observeViewModel() {
        observerList(viewModel.treeChildListResp) { state ->
            parseListState(state, loads, binding.container.refreshLayout, adapter)
        }
    }
}