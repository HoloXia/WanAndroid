package com.holo.wanandroid.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.architecture.ext.toGone
import com.holo.architecture.ext.toHtml
import com.holo.architecture.ext.toVisible
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.ArticleBean
import com.holo.wanandroid.databinding.ItemArticleBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class ArticleAdapter(private val inHome: Boolean = false) : BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.item_article, mutableListOf()),
    LoadMoreModule {

    init {
        addChildClickViewIds(R.id.tv_author, R.id.like_view)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        val binding = holder.getBinding(ItemArticleBinding::bind)
        binding.tvAuthor.text = item.authorShow()
        binding.slNewTag.toVisible(item.fresh)
        if (inHome) {
            binding.slTypeTag.toVisible(!item.tags.isNullOrEmpty())
            if (item.tags.isNotEmpty()) {
                binding.tvTypeTag.text = item.tags[0].name
            }
        } else {
            binding.slTypeTag.toGone()
        }
        binding.slTopTag.toVisible(item.type == 1)

        binding.tvCreateTime.text = item.niceDate
        binding.tvTitle.text = item.title.toHtml()

        binding.tvCategory.text = "${item.superChapterName}・${item.chapterName}".toHtml()

        binding.likeView.isChecked = item.collect
    }

}