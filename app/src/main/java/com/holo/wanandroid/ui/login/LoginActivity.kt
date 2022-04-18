package com.holo.wanandroid.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.ext.showErrToast
import com.holo.architecture.network.State
import com.holo.architecture.utils.flowbus.observeEvent
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.UserInfoBean
import com.holo.wanandroid.databinding.ActivityLoginBinding
import com.holo.wanandroid.event.LoginEvent
import com.holo.wanandroid.ext.initClose
import com.holo.wanandroid.ui.mine.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var controller: NavController

    override fun initView(savedInstanceState: Bundle?) {
        binding.titleBar.toolbar.initClose { finish() }
    }

    override fun onStart() {
        super.onStart()
        controller = Navigation.findNavController(binding.navHostLogin)
        controller.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment -> binding.titleBar.tvToolbarTitle.text = "登录"
                else -> binding.titleBar.tvToolbarTitle.text = "注册"
            }
        }
    }

    override fun observeViewModel() {
        observeEvent<LoginEvent> { event ->
            when (event.state) {
                is State.Loading -> showLoading()
                is State.Success -> {
                    hideLoading()
                    finish()
                }
                is State.Error -> {
                    hideLoading()
                    showErrToast(event.state.err)
                }
            }
        }
    }
}