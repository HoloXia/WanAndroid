package com.holo.wanandroid.ext

import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.holo.architecture.base.fragment.BaseFragment
import com.holo.architecture.ext.*
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.architecture.utils.flowbus.observeEvent
import com.holo.loadstate.LoadService
import com.holo.wanandroid.R
import com.holo.wanandroid.data.dto.UserInfoBean
import com.holo.wanandroid.event.LoginEvent
import com.holo.wanandroid.ui.discovery.NavDiscoveryFragment
import com.holo.wanandroid.ui.home.NavHomeFragment
import com.holo.wanandroid.ui.login.LoginActivity
import com.holo.wanandroid.ui.mine.NavMineFragment
import com.holo.wanandroid.ui.mine.UserViewModel
import com.holo.wanandroid.ui.projects.NavProjectsFragment
import com.holo.wanandroid.ui.publicnum.NavPublicNumFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * BRVAH便捷使用ViewBinding
 */
fun <VB : ViewBinding> BaseViewHolder.getBinding(bind: (View) -> VB): VB =
    itemView.getTag(Int.MIN_VALUE) as? VB ?: bind(itemView).also { itemView.setTag(Int.MIN_VALUE, it) }

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    title = titleStr
    findViewById<TextView>(R.id.tv_toolbar_title)?.let {
        title = null
        it.text = titleStr.toHtml()
    }
    return this
}

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.mipmap.icon_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    title = titleStr.toHtml()
    findViewById<TextView>(R.id.tv_toolbar_title)?.let {
        title = null
        it.text = titleStr.toHtml()
    }
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

/**
 * 初始化首页ViewPager2
 * @receiver ViewPager2
 * @param activity AppCompatActivity
 * @return ViewPager2
 */
fun ViewPager2.initMain(activity: AppCompatActivity): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 5
    //设置适配器
    adapter = object : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NavHomeFragment()
                1 -> NavProjectsFragment()
                2 -> NavDiscoveryFragment()
                3 -> NavPublicNumFragment()
                else -> NavMineFragment()
            }
        }

        override fun getItemCount() = 5
    }
    return this
}

fun ViewPager2.init(activity: FragmentActivity, fragments: List<BaseFragment<*>>): ViewPager2 {
    this.offscreenPageLimit = fragments.size
    //设置适配器
    adapter = object : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
    return this
}

fun <T> parseListState(
    st: ListState<T>,
    loadSir: LoadService?,
    refresh: SmartRefreshLayout,
    adapter: BaseQuickAdapter<T, *>,
    emptyTip: String? = null,
    emptyIcon: Int? = null
) {
    refresh.finishRefresh()
    if (st.isSuccess) {
        //成功
        when {
            //是第一页
            st.isRefresh -> {
                if (st.isEmpty) {
                    loadSir?.showEmptyCus(emptyTip, emptyIcon)
                } else {
                    adapter.setList(st.listData)
                    loadSir?.hide()
                }
                // 如果没有更多数据，直接禁用加载更多
                if (!st.hasMore) {
                    adapter.loadMoreModule.loadMoreEnd()
                }
            }
            //不是第一页
            else -> {
                adapter.addData(st.listData)
                if (st.hasMore) {
                    adapter.loadMoreModule.loadMoreComplete()
                } else {
                    adapter.loadMoreModule.loadMoreEnd()
                }
            }
        }
    } else {
        //失败
        if (st.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            loadSir?.showFailedCus(st.err.errorMsg)
        } else {
            adapter.loadMoreModule.loadMoreFail()
        }
    }
}

fun ComponentActivity.showLoginDialog() {
    showToast("请先登录")
    val dialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).lifecycleOwner(this).show {
        title(R.string.str_login_tip)
        customView(R.layout.dialog_login, scrollable = true, horizontalPadding = true)
    }.show {
        val viewModel: UserViewModel by viewModels()
        val etAccount = this.findViewById<TextInputEditText>(R.id.et_account)
        etAccount.requestFocus()
        etAccount.postDelayed({ etAccount.showKeyboard() }, 160)

        val etPsw = this.findViewById<TextInputEditText>(R.id.et_psw)
        val btnLogin = this.findViewById<MaterialButton>(R.id.btn_login)
        fun btnEnable() {
            btnLogin.isEnabled = etAccount.isNotBlank() && etPsw.isNotBlank()
        }
        etAccount.afterTextChanged { btnEnable() }
        etPsw.afterTextChanged { btnEnable() }

        this.findViewById<View>(R.id.tv_to_register).clickNoRepeat {
            dismiss()
            startActivity<LoginActivity>()
        }

        btnLogin.clickNoRepeat {
            viewModel.doLogin(etAccount.text.toString(), etPsw.text.toString())
//            doLogin.invoke(etAccount.text.toString(), etPsw.text.toString())
        }
    }
    observeEvent<LoginEvent> { event ->
        if (event.state is State.Success) {
            dialog.dismiss()
        }
    }
}