package com.holo.wanandroid.ui

import android.os.Bundle
import android.webkit.ConsoleMessage
import android.webkit.WebView
import android.widget.LinearLayout
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.ext.toGone
import com.holo.architecture.ext.toVisible
import com.holo.architecture.logger.KLog
import com.holo.loadstate.LoadService
import com.holo.wanandroid.Constant
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.ActivityWebBinding
import com.holo.wanandroid.ext.loadServiceInit
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient

class WebActivity : BaseActivity<ActivityWebBinding>() {

    private lateinit var agentWeb: AgentWeb
    private lateinit var preAgentWeb: AgentWeb.PreAgentWeb

    private var webTitle: String? = null

    private lateinit var loads: LoadService

    override fun initView(savedInstanceState: Bundle?) {
        val webUrl = intent.getStringExtra(Constant.KEY_WEB_URL)
        webTitle = intent.getStringExtra(Constant.KEY_WEB_TITLE)
        initWeb(webUrl ?: "")
        loads = loadServiceInit(binding.loadView, R.layout.layout_loadsir_loading) {
            agentWeb = preAgentWeb.go(webUrl)
        }
    }

    private fun initWeb(url: String) {
        KLog.d("Holo", "WebActivity go url:${url}")
        preAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.container, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
            .useDefaultIndicator(resources.getColor(R.color.colorPrimary))
            .setWebChromeClient(webChromeClient)
            .setWebViewClient(webClient)
            .setMainFrameErrorView(com.just.agentweb.R.layout.agentweb_error_page, -1)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .createAgentWeb()
            .ready()
        agentWeb = preAgentWeb.go(url)
    }

    private val webClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            loads.hide()
            binding.loadView.toGone()
            binding.container.toVisible()
            super.onPageFinished(view, url)
        }

    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return super.onConsoleMessage(consoleMessage)
        }
    }

    override fun observeViewModel() {

    }

    override fun onPause() {
        agentWeb.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}