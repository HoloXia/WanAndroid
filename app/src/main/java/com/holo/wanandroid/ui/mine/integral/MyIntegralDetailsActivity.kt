package com.holo.wanandroid.ui.mine.integral

import android.os.Bundle
import androidx.activity.viewModels
import com.holo.architecture.base.activity.BaseActivity
import com.holo.loadstate.LoadService
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.ActivityMyIntegralDetailsBinding
import com.holo.wanandroid.ext.initClose
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ui.mine.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyIntegralDetailsActivity : BaseActivity<ActivityMyIntegralDetailsBinding>() {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var loads: LoadService
    private lateinit var adapter: IntegralDetailsAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.titleBar.toolbar.initClose("我的积分") { finish() }

        adapter = IntegralDetailsAdapter()
        binding.container.recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getMyIntegralList(false) }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_integral_details_shimmer) {
            viewModel.getMyIntegralList()
        }
        loads.config { setItemCount(15) }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getMyIntegralList() }
    }

    override fun initData() {
        viewModel.getMyIntegralList()
    }

    override fun observeViewModel() {
        observerList(viewModel.myIntegralListResp) { state ->
            parseListState(state, loads, binding.container.refreshLayout, adapter)
        }
    }
}