package com.holo.wanandroid.ui.projects

import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.architecture.ext.toHtml
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.ProjectBean
import com.holo.wanandroid.databinding.ItemProjectsBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class ProjectsAdapter : BaseQuickAdapter<ProjectBean, BaseViewHolder>(R.layout.item_projects, mutableListOf()), LoadMoreModule {

    init {
        addChildClickViewIds(R.id.like_view)
    }

    override fun convert(holder: BaseViewHolder, item: ProjectBean) {
        val binding = holder.getBinding(ItemProjectsBinding::bind)

        binding.ivCover.load(item.envelopePic) {
            error(R.mipmap.ic_img_default)
        }
        binding.tvTitle.text = item.title.toHtml()
        binding.tvContent.text = item.desc.toHtml()
        binding.tvAuthor.text = item.authorShow()
        binding.tvCreateTime.text = item.niceDate

        binding.likeView.isChecked = item.collect
    }

}