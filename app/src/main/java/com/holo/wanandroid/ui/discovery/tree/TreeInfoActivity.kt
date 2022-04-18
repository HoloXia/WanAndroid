package com.holo.wanandroid.ui.discovery.tree

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.clickNoRepeat
import com.holo.wanandroid.Constant
import com.holo.wanandroid.data.dto.CategoryBean
import com.holo.wanandroid.data.dto.TreeBean
import com.holo.wanandroid.databinding.ActivityTreeInfoBinding
import com.holo.wanandroid.ext.init
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreeInfoActivity : BaseActivity<ActivityTreeInfoBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val bean = intent.getParcelableExtra<TreeBean>(Constant.KEY_TREE_BEAN)
        val selectedId = intent.getIntExtra(Constant.KEY_ID, 0)
        if (bean == null) {
            finish()
            return
        }
        binding.toolbar.tvToolbarTitle.text = bean.name
        binding.toolbar.ivBack.clickNoRepeat { finish() }
        initTab(bean.children, selectedId)
    }

    private fun initTab(categoryList: List<CategoryBean>, selectedId: Int) {
        val titleList = mutableListOf<String>()
        val fragments = mutableListOf<BaseFragment<*>>()
        var selectedPosition = 0
        categoryList.forEachIndexed { index, bean ->
            if (bean.id == selectedId) {
                selectedPosition = index
            }
            titleList.add(bean.name)
            fragments.add(TreeInfoFragment.newInstance(bean.id))
        }

        binding.viewPager.init(this, fragments)
        binding.viewPager.setCurrentItem(selectedPosition, false)
        TabLayoutMediator(binding.tabNews, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun observeViewModel() {

    }
}