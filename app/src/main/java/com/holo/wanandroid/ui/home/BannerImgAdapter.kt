package com.holo.wanandroid.ui.home

import coil.load
import com.holo.wanandroid.data.dto.BannerBean
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class BannerImgAdapter : BannerImageAdapter<BannerBean>(mutableListOf()) {

    override fun onBindView(holder: BannerImageHolder, data: BannerBean, position: Int, size: Int) {
        holder.imageView.load(data.imagePath)
    }

}