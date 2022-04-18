package com.holo.wanandroid.ui.mine.integral

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.architecture.ext.toInvisible
import com.holo.architecture.ext.toVisible
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.IntegralBean
import com.holo.wanandroid.databinding.ItemIntegralBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/18
 */
class IntegralAdapter : BaseQuickAdapter<IntegralBean, BaseViewHolder>(R.layout.item_integral, mutableListOf()), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: IntegralBean) {
        val binding = holder.getBinding(ItemIntegralBinding::bind)

        binding.vDivider.toVisible(holder.bindingAdapterPosition < (data.size - 1))

        binding.ivRankTop.toInvisible(holder.bindingAdapterPosition < 3)
        binding.tvRank.toVisible(holder.bindingAdapterPosition >= 3)
        when (holder.bindingAdapterPosition) {
            0 -> binding.ivRankTop.setImageResource(R.mipmap.ic_rank_no1)
            1 -> binding.ivRankTop.setImageResource(R.mipmap.ic_rank_no2)
            2 -> binding.ivRankTop.setImageResource(R.mipmap.ic_rank_no3)
            else -> binding.tvRank.text = "${holder.bindingAdapterPosition + 1}"
        }

        binding.tvUserName.text = item.getShowName()
        binding.tvIntegral.text = item.coinCount.toString()
    }

}