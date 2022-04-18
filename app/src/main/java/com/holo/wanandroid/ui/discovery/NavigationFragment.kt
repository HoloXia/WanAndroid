package com.holo.wanandroid.ui.discovery

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.startActivity
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentNavigationBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ui.WebActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
@AndroidEntryPoint
class NavigationFragment : BaseFragment<FragmentNavigationBinding>() {

    private val viewModel: DiscoveryViewModel by viewModels()

    private lateinit var loads: LoadService

    private lateinit var adapter: NavigationAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = NavigationAdapter { childItem ->
            startActivity<WebActivity>(
                Constant.KEY_WEB_TITLE to childItem.title,
                Constant.KEY_WEB_URL to childItem.link
            )
        }
        binding.container.recyclerView.adapter = adapter

        binding.container.refreshLayout.setOnRefreshListener { viewModel.getNavigationList() }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_tree_shimmer) {
            viewModel.getNavigationList()
        }
    }

    override fun lazyLoadData() {
        viewModel.getNavigationList()
    }

    override fun observeViewModel() {
        observerObj(viewModel.navigationListResp) { list ->
            binding.container.refreshLayout.finishRefresh()
            if (list.isNullOrEmpty()) {
                loads.showEmpty()
            } else {
                loads.hide()
                adapter.setList(list)
            }
        }
    }
}