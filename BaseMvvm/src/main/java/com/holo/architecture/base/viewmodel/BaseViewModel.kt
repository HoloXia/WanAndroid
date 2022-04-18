package com.holo.architecture.base.viewmodel

import androidx.lifecycle.ViewModel

/**
 * @Desc
 * @Author holo
 * @Date 2022/1/6
 */
open class BaseViewModel : ViewModel() {

    /**
     * 分页请求，页码
     */
    protected var pageNo: Int = 1
}