package com.holo.wanandroid.ui.discovery

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.CategoryBean
import com.holo.wanandroid.data.dto.TreeBean
import com.holo.wanandroid.databinding.ItemTreeBinding
import com.holo.wanandroid.databinding.ItemTreeInBinding
import com.holo.wanandroid.ext.getBinding

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
class TreeAdapter(private val childClick: (treeBean: TreeBean, category: CategoryBean) -> Unit) :
    BaseQuickAdapter<TreeBean, BaseViewHolder>(R.layout.item_tree, mutableListOf()) {

    private lateinit var adapter: TreeInAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun convert(holder: BaseViewHolder, item: TreeBean) {
        val binding = holder.getBinding(ItemTreeBinding::bind)
        binding.tvName.text = item.name

        adapter = TreeInAdapter(item.children.toMutableList())
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, view, position ->
            childClick.invoke(item, item.children[position])
        }
        binding.recyclerView.setOnTouchListener { v, event ->
            binding.itemRoot.onTouchEvent(event)
            false
        }
    }
}

class TreeInAdapter(list: MutableList<CategoryBean>) : BaseQuickAdapter<CategoryBean, BaseViewHolder>(R.layout.item_tree_in, list) {
    override fun convert(holder: BaseViewHolder, item: CategoryBean) {
        val binding = holder.getBinding(ItemTreeInBinding::bind)

        binding.tvName.text = item.name
    }
}