package com.holo.wanandroid.ui.mine.integral

import android.os.Bundle
import androidx.activity.viewModels
import com.holo.architecture.base.activity.BaseActivity
import com.holo.loadstate.LoadService
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.ActivityIntegralRankListBinding
import com.holo.wanandroid.ext.initClose
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ui.mine.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntegralRankListActivity : BaseActivity<ActivityIntegralRankListBinding>() {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var loads: LoadService
    private lateinit var adapter: IntegralAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.titleBar.toolbar.initClose("积分排行") { finish() }

        adapter = IntegralAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getIntegralRankList(false) }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_integral_shimmer) {
            viewModel.getIntegralRankList()
        }
        loads.config { setItemCount(15) }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getIntegralRankList() }
    }

    override fun initData() {
        viewModel.getIntegralRankList()
    }

    override fun observeViewModel() {
        observerList(viewModel.integralRankListResp) { state ->
            parseListState(state, loads, binding.container.refreshLayout, adapter)
        }
    }
}