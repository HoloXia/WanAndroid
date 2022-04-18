package com.holo.wanandroid.ui.login

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.*
import com.holo.wanandroid.databinding.FragmentRegisterBinding
import com.holo.wanandroid.ui.mine.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 注册
 *
 * @Author holo
 * @Date 2022/4/18
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: UserViewModel by activityViewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.etAccount.afterTextChanged { switchBtnEnable() }
        binding.etPsw.afterTextChanged { switchBtnEnable() }
        binding.etRePsw.afterTextChanged { switchBtnEnable() }

        binding.btnConfirm.clickNoRepeat {
            val psw = binding.etPsw.text.toString()
            val rePsw = binding.etRePsw.text.toString()
            if (psw != rePsw) {
                showToast("两次密码输入不一致")
                return@clickNoRepeat
            }
            viewModel.doRegister(binding.etAccount.text.toString(), psw, rePsw)
        }
    }

    private fun switchBtnEnable() {
        binding.btnConfirm.isEnabled = binding.etAccount.isNotBlank() && binding.etPsw.isNotBlank() && binding.etRePsw.isNotBlank()
    }

    override fun observeViewModel() {
        observer(viewModel.registerResp, success = {
            showToast("注册成功，请登录")
            nav().navigateUp()
        })
    }
}