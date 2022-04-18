package com.holo.wanandroid.ui.mine.integral

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.IntegralInfoBean
import com.holo.wanandroid.databinding.ItemIntegralDetailsBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/18
 */
class IntegralDetailsAdapter : BaseQuickAdapter<IntegralInfoBean, BaseViewHolder>(R.layout.item_integral_details, mutableListOf()), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: IntegralInfoBean) {
        val binding = holder.getBinding(ItemIntegralDetailsBinding::bind)

        binding.tvTitle.text = item.reason
        binding.tvCreateTime.text = item.desc
        binding.tvIntegral.text = "+${item.coinCount}"
    }
}