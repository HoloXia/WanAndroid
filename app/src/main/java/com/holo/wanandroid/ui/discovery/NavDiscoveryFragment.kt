package com.holo.wanandroid.ui.discovery

import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.statusBarHeight
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.wanandroid.databinding.FragmentNavDiscoveryBinding
import com.holo.wanandroid.ext.init
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class NavDiscoveryFragment : BaseFragment<FragmentNavDiscoveryBinding>() {

    private val fragmentList: MutableList<BaseFragment<*>> = mutableListOf()
    private val titleList: MutableList<String> = mutableListOf()

    override fun initView(savedInstanceState: Bundle?) {
        val params = binding.titleBar.layoutParams as LinearLayout.LayoutParams
        params.height = statusBarHeight
        binding.titleBar.layoutParams = params

        initTab()
    }

    private fun initTab() {
        activity?.run {
            fragmentList.add(PlazaFragment())
            titleList.add("广场")
            fragmentList.add(QAFragment())
            titleList.add("问答")
            fragmentList.add(TreeFragment())
            titleList.add("体系")
            fragmentList.add(NavigationFragment())
            titleList.add("导航")

            binding.viewPager.init(this, fragmentList)
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = titleList[position]
            }.attach()
        }
    }

    override fun observeViewModel() {

    }
}