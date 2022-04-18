package com.holo.wanandroid.ui.mine.collect

import android.os.Bundle
import androidx.activity.viewModels
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toHtml
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.ActivityMyCollectBinding
import com.holo.wanandroid.ext.initClose
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.mine.CollectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCollectActivity : BaseActivity<ActivityMyCollectBinding>() {

    private val viewModel: CollectViewModel by viewModels()

    private lateinit var loads: LoadService
    private lateinit var adapter: CollectAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.titleBar.toolbar.initClose("我的收藏") { finish() }

        adapter = CollectAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getCollectList(false) }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_projects_shimmer) {
            viewModel.getCollectList()
        }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getCollectList() }

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
                    viewModel.infoUnCollect(item.id, item.originId, position)
                }
            }
        }
    }

    override fun initData() {
        viewModel.getCollectList()
    }

    override fun observeViewModel() {
        observerList(viewModel.collectListResp) { state ->
            parseListState(state, loads, binding.container.refreshLayout, adapter)
        }
        observer(viewModel.unCollectResp, success = { position ->
            adapter.data.removeAt(position)
            adapter.notifyItemRemoved(position)
        })
    }
}