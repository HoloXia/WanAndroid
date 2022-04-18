package com.holo.wanandroid.ui.login

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.*
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.FragmentLoginBinding
import com.holo.wanandroid.ui.mine.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录
 *
 * @Author holo
 * @Date 2022/4/18
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: UserViewModel by activityViewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.etAccount.afterTextChanged { switchBtnEnable() }
        binding.etPsw.afterTextChanged { switchBtnEnable() }

        binding.tvRegisterNow.clickNoRepeat { nav().navigateAction(R.id.action_login_to_register) }
        binding.btnConfirm.clickNoRepeat {
            viewModel.doLogin(binding.etAccount.text.toString(), binding.etPsw.text.toString())
        }
    }

    private fun switchBtnEnable() {
        binding.btnConfirm.isEnabled = binding.etAccount.isNotBlank() && binding.etPsw.isNotBlank()
    }

    override fun observeViewModel() {

    }
}