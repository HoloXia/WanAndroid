package com.holo.wanandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.gyf.immersionbar.ktx.statusBarHeight
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toHtml
import com.holo.architecture.ext.toVisible
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentNavHomeBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ext.parseListState
import com.holo.wanandroid.ext.showLoginDialog
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.mine.CollectViewModel
import com.holo.wanandroid.util.CacheUtil
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class NavHomeFragment : BaseFragment<FragmentNavHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val collectViewModel: CollectViewModel by viewModels()

    private lateinit var loads: LoadService

    private lateinit var bannerAdapter: BannerImgAdapter
    private lateinit var adapter: ArticleAdapter

    override fun initView(savedInstanceState: Bundle?) {
        val params = binding.titleBar.layoutParams as CollapsingToolbarLayout.LayoutParams
        val height = statusBarHeight
        params.height = height
        binding.titleBar.layoutParams = params
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollHeight = binding.bannerHomeTop.height - height
            val fraction: Float = abs(verticalOffset * 1.0f) / scrollHeight
            binding.titleBar.alpha = if (fraction >= 1) fraction else 0f
            binding.titleBar.toVisible(fraction > 0.8)
        })

        bannerAdapter = BannerImgAdapter()
        bannerAdapter.setOnBannerListener { data, position ->
            val item = adapter.getItem(position)
            startActivity<WebActivity>(
                Constant.KEY_WEB_TITLE to item.title.toHtml(),
                Constant.KEY_WEB_URL to item.link
            )
        }
        binding.bannerHomeTop
            .addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(activity))
            .setAdapter(bannerAdapter)

        adapter = ArticleAdapter(true)
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

        adapter.loadMoreModule.setOnLoadMoreListener { viewModel.getArticleList(false) }
        binding.container.refreshLayout.setOnRefreshListener { viewModel.getArticleList() }
        loads = loadServiceInit(binding.container.recyclerView, adapter, R.layout.item_article_shimmer) {
            viewModel.getArticleList()
        }
    }

    override fun initData() {
        viewModel.getBannerList()
        viewModel.getArticleList()
    }

    override fun observeViewModel() {
        observer(viewModel.bannerListResp) {
            bannerAdapter.setDatas(it)
        }
        observerList(viewModel.articleListResp) { state ->
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