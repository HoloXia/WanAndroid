package com.holo.wanandroid.ui.projects

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.statusBarHeight
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.wanandroid.databinding.FragmentNavProjectsBinding
import com.holo.wanandroid.ext.init
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class NavProjectsFragment : BaseFragment<FragmentNavProjectsBinding>() {

    private val viewModel: ProjectsViewModel by viewModels()

    private val fragmentList: MutableList<BaseFragment<*>> = mutableListOf()
    private val titleList: MutableList<String> = mutableListOf()

    override fun initView(savedInstanceState: Bundle?) {
        val params = binding.titleBar.layoutParams as LinearLayout.LayoutParams
        params.height = statusBarHeight
        binding.titleBar.layoutParams = params
    }

    override fun initData() {
        viewModel.getProjectCategories()
    }

    override fun observeViewModel() {
        observerObj(viewModel.categoryListResp) { list ->
            activity?.let { act ->
                fragmentList.add(ProjectsFragment.newInstance(-1))
                titleList.add("最新项目")
                list?.forEach { bean ->
                    fragmentList.add(ProjectsFragment.newInstance(bean.id))
                    titleList.add(bean.name)
                }
                binding.viewPager.init(act, fragmentList)
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = titleList[position]
                }.attach()
            }
        }
    }
}