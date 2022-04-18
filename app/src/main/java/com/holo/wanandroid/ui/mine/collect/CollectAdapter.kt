package com.holo.wanandroid.ui.mine.collect

import coil.load
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.architecture.ext.toHtml
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.CollectBean
import com.holo.wanandroid.data.dto.CollectType
import com.holo.wanandroid.databinding.ItemArticleBinding
import com.holo.wanandroid.databinding.ItemProjectsBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/18
 */
class CollectAdapter : BaseMultiItemQuickAdapter<CollectBean, BaseViewHolder>(mutableListOf()), LoadMoreModule {

    init {
        addItemType(CollectType.COLLECT_ARTICLE, R.layout.item_article)
        addItemType(CollectType.COLLECT_PROJECTS, R.layout.item_projects)
        addChildClickViewIds(R.id.like_view)
    }

    override fun convert(holder: BaseViewHolder, item: CollectBean) {
        when (holder.itemViewType) {
            CollectType.COLLECT_ARTICLE -> convertArticle(holder, item)
            CollectType.COLLECT_PROJECTS -> convertProjects(holder, item)
        }
    }

    private fun convertArticle(holder: BaseViewHolder, item: CollectBean) {
        val binding = holder.getBinding(ItemArticleBinding::bind)

        binding.tvAuthor.text = item.author

        binding.tvCreateTime.text = item.niceDate
        binding.tvTitle.text = item.title.toHtml()

        binding.tvCategory.text = "${item.chapterName}".toHtml()

        binding.likeView.isChecked = true
    }

    private fun convertProjects(holder: BaseViewHolder, item: CollectBean) {
        val binding = holder.getBinding(ItemProjectsBinding::bind)

        binding.ivCover.load(item.envelopePic) {
            error(R.mipmap.ic_img_default)
        }
        binding.tvTitle.text = item.title.toHtml()
        binding.tvContent.text = item.desc.toHtml()
        binding.tvAuthor.text = item.author
        binding.tvCreateTime.text = item.niceDate

        binding.likeView.isChecked = true
    }
}