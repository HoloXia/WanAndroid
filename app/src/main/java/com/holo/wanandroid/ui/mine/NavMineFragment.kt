package com.holo.wanandroid.ui.mine

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.clickNoRepeat
import com.holo.architecture.ext.startActivity
import com.holo.architecture.ext.toVisible
import com.holo.architecture.network.State
import com.holo.architecture.utils.flowbus.observeEvent
import com.holo.wanandroid.Constant
import com.holo.wanandroid.databinding.FragmentNavMineBinding
import com.holo.wanandroid.event.LoginEvent
import com.holo.wanandroid.event.LogoutEvent
import com.holo.wanandroid.ext.showLoginDialog
import com.holo.wanandroid.ui.WebActivity
import com.holo.wanandroid.ui.login.LoginActivity
import com.holo.wanandroid.ui.mine.collect.MyCollectActivity
import com.holo.wanandroid.ui.mine.integral.IntegralRankListActivity
import com.holo.wanandroid.ui.mine.integral.MyIntegralDetailsActivity
import com.holo.wanandroid.util.CacheUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@AndroidEntryPoint
class NavMineFragment : BaseFragment<FragmentNavMineBinding>() {

    private val viewModel: UserViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivAvatar.clickNoRepeat { startActivity<LoginActivity>() }
        binding.tvUserName.clickNoRepeat { startActivity<LoginActivity>() }

        binding.vgMyIntegral.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                startActivity<MyIntegralDetailsActivity>()
            } else {
                activity?.showLoginDialog()
            }
        }
        binding.vgIntegralRankList.clickNoRepeat {
            startActivity<IntegralRankListActivity>()
        }
        binding.vgMyCollect.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                startActivity<MyCollectActivity>()
            } else {
                activity?.showLoginDialog()
            }
        }
        binding.vgGithub.clickNoRepeat {
            startActivity<WebActivity>(
                Constant.KEY_WEB_TITLE to "Github",
                Constant.KEY_WEB_URL to "https://github.com/HoloXia/WanAndroid"
            )
        }
        binding.tvLogout.clickNoRepeat { viewModel.doLogout() }
        refreshUI()
    }

    override fun initData() {
        viewModel.getMyIntegral()
    }

    private fun refreshUI() {
        val userInfo = CacheUtil.getUserInfo()
        binding.tvUserName.text = userInfo?.getShowName() ?: "请登录"
        binding.tvLogout.toVisible(userInfo != null)
        if (userInfo == null) {
            binding.tvLevel.text = "等级：-"
            binding.tvRank.text = "排名：-"
            binding.tvIntegral.text = ""
        }
    }

    override fun observeViewModel() {
        observerObj(viewModel.myIntegralResp) { bean ->
            bean?.let {
                binding.tvLevel.text = "等级：${bean.level}"
                binding.tvRank.text = "排名：${bean.rank}"
                binding.tvIntegral.text = bean.coinCount.toString()
            }
        }
        observeEvent<LoginEvent> { event ->
            if (event.state is State.Success) {
                CacheUtil.saveUserInfo(event.state.data)
                refreshUI()
                viewModel.getMyIntegral()
            }
        }
        observeEvent<LogoutEvent> { event ->
            if (event.state is State.Success) {
                CacheUtil.saveUserInfo(null)
                refreshUI()
            }
        }
    }
}