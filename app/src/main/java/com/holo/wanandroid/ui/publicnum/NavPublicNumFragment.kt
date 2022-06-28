package com.holo.wanandroid.ui.publicnum

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.statusBarHeight
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.loadstate.LoadService
import com.holo.wanandroid.databinding.FragmentNavPublicnumBinding
import com.holo.wanandroid.ext.init
import com.holo.wanandroid.ext.loadServiceInit
import com.holo.wanandroid.ui.discovery.DiscoveryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class NavPublicNumFragment : BaseFragment<FragmentNavPublicnumBinding>() {

    private val viewModel: DiscoveryViewModel by viewModels()

    private lateinit var loads: LoadService
    private val fragmentList: MutableList<BaseFragment<*>> = mutableListOf()
    private val titleList: MutableList<String> = mutableListOf()

    override fun initView(savedInstanceState: Bundle?) {
        val params = binding.titleBar.layoutParams as LinearLayout.LayoutParams
        params.height = statusBarHeight
        binding.titleBar.layoutParams = params

        loads = loadServiceInit(binding.contentView) {
            initData()
        }
    }

    override fun initData() {
        viewModel.getPublicNumList()
    }

    override fun observeViewModel() {
        observer(viewModel.publicNumListResp, success = { list ->
            loads.hide()
            activity?.let { act ->
                list.forEach { bean ->
                    fragmentList.add(PublicArticleFragment.newInstance(bean.id))
                    titleList.add(bean.name)

                    binding.viewPager.init(act, fragmentList)
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = titleList[position]
                    }.attach()
                }
            }
        }, error = {
            loads.showFailed()
        })
    }
}