package com.holo.wanandroid.ui.discovery

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.startActivity
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentTreeBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ui.discovery.tree.TreeInfoActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
@AndroidEntryPoint
class TreeFragment : BaseFragment<FragmentTreeBinding>() {

    private val viewModel: DiscoveryViewModel by viewModels()

    private lateinit var loads: LoadService

    private lateinit var adapter: TreeAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = TreeAdapter { tree, catetory ->
            startActivity<TreeInfoActivity>(
                Constant.KEY_TREE_BEAN to tree,
                Constant.KEY_ID to catetory.id
            )
        }
        binding.container.recyclerView.adapter = adapter

        adapter.setOnItemClickListener { _, view, position ->
            startActivity<TreeInfoActivity>(Constant.KEY_TREE_BEAN to adapter.getItem(position))
        }

        binding.container.refreshLayout.setOnRefreshListener { viewModel.getTreeList() }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_tree_shimmer) {
            viewModel.getTreeList()
        }
    }

    override fun lazyLoadData() {
        viewModel.getTreeList()
    }

    override fun observeViewModel() {
        observerObj(viewModel.treeListResp) { list ->
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