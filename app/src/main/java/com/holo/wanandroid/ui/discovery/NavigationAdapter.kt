package com.holo.wanandroid.ui.discovery

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.architecture.ext.toHtml
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.ArticleBean
import com.holo.wanandroid.data.dto.NavigationBean
import com.holo.wanandroid.databinding.ItemTreeBinding
import com.holo.wanandroid.databinding.ItemTreeInBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
class NavigationAdapter(private val childClick: (childItem: ArticleBean) -> Unit) :
    BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_tree, mutableListOf()) {

    private lateinit var adapter: NavigationInAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun convert(holder: BaseViewHolder, item: NavigationBean) {
        val binding = holder.getBinding(ItemTreeBinding::bind)
        binding.tvName.text = item.name

        adapter = NavigationInAdapter(item.articles.toMutableList())
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, view, position ->
            childClick.invoke(item.articles[position])
        }
        binding.recyclerView.setOnTouchListener { v, event ->
            binding.itemRoot.onTouchEvent(event)
            false
        }
    }
}

class NavigationInAdapter(list: MutableList<ArticleBean>) : BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.item_tree_in, list) {
    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        val binding = holder.getBinding(ItemTreeInBinding::bind)

        binding.tvName.text = item.title.toHtml()
    }
}