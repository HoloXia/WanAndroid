/**
 *
 *
 * @Author holo
 * @Date 2022/2/14
 */

object Moshi {
    const val moshi = "com.squareup.moshi:moshi-kotlin:1.13.0"
    const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:1.13.0"
}

object Retrofit {
    //网路请求库retrofit
    private const val version = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:${version}"

    //gson序列化转换
    const val convertGson = "com.squareup.retrofit2:converter-gson:${version}"

    //moshi序列化转换
    const val convertMoshi = "com.squareup.retrofit2:converter-moshi:${version}"

    //日志拦截打印
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
}

object Depends {
    //gson解析
    const val gson = "com.google.code.gson:gson:2.8.7"

    // Kotlin 协程方式的加载图片库 https://github.com/coil-kt/coil/
    const val coil = "io.coil-kt:coil:2.0.0-rc01"

    // 腾讯内存映射组件，替代SP https://github.com/Tencent/MMKV/blob/master/README_CN.md
    const val mmkv = "com.tencent:mmkv:1.2.11"

    // android 4.4以上沉浸式实现 https://github.com/gyf-dev/ImmersionBar
    const val immersionBar = "com.geyifeng.immersionbar:immersionbar:3.2.0"
    const val immersionBarKtx = "com.geyifeng.immersionbar:immersionbar-ktx:3.2.0"

    // UnPeekLiveData-解决数据倒灌困扰 https://github.com/KunMinX/UnPeek-LiveData
    const val unPeekLiveData = "com.kunminx.arch:unpeek-livedata:7.2.0-beta1"

    //lottie用来加载json动画
    // 你可以使用转换工具将mp4转换成json【https://isotropic.co/video-to-lottie/】
    // 去这里下载素材【https://lottiefiles.com/】
    const val lottie = "com.airbnb.android:lottie:4.2.2"

    const val shadowLayout = "com.github.lihangleo2:ShadowLayout:3.2.4"

    // Android WebView ，极度容易使用以及功能强大的库 https://github.com/Justson/AgentWeb
    const val agentWeb = "com.github.Justson.AgentWeb:agentweb-core:v5.0.0-alpha.1-androidx"

    // BRVAH 强大而灵活的RecyclerView Adapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    const val BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"

    // 加载反馈页管理框架 https://github.com/KingJA/LoadSir
    const val loadSir = "com.kingja.loadsir:loadsir:1.3.8"

    // FaceBook shimmer闪烁动画库 https://github.com/facebook/shimmer-android
    const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

    // 指示器框架 https://github.com/hackware1993/MagicIndicator
    const val magicIndicator = "com.github.hackware1993:MagicIndicator:1.7.0"

    // 基于ViewPager2的Banner控件 https://github.com/youth5201314/banner
    const val banner = "io.github.youth5201314:banner:2.2.2"

    // 权限申请库 https://github.com/guolindev/PermissionX
    const val permissionX = "com.guolindev.permissionx:permissionx:1.6.1"

    // 点赞View https://github.com/qkxyjren/LikeView
    const val likeView = "com.jaren:likeview:1.2.2"

    // 缺省页管理 https://github.com/HoloXia/LoadState
    const val loadState = "com.github.HoloXia:LoadState:1.0"

    // Cookie持久化 https://github.com/franmontiel/PersistentCookieJar
    const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"
}

object SmartRefreshLayout {
    // 智能下拉刷新框架 https://github.com/scwang90/SmartRefreshLayout
    const val core = "io.github.scwang90:refresh-layout-kernel:2.0.5"
    const val headerMaterial = "io.github.scwang90:refresh-header-material:2.0.5"
    const val footerClassics = "io.github.scwang90:refresh-footer-classics:2.0.5"
}

object DKVideoPlayer {
    // 视频播放器 https://github.com/Doikki/DKVideoPlayer
    private const val version = "3.3.5"
    const val core = "xyz.doikki.android.dkplayer:dkplayer-java:$version"
    const val ui = "xyz.doikki.android.dkplayer:dkplayer-ui:$version"
    const val exo = "xyz.doikki.android.dkplayer:player-exo:$version"
    const val ijk = "xyz.doikki.android.dkplayer:player-ijk:$version"
    const val cache = "xyz.doikki.android.dkplayer:videocache:$version"
}

/**
 * 插入即用的dialog
 */
object MaterialDialogs {
    // 项目地址 https://github.com/afollestad/material-dialogs

    private const val version = "3.3.0"
    const val core = "com.afollestad.material-dialogs:core:$version"
    const val input = "com.afollestad.material-dialogs:input:$version"
    const val color = "com.afollestad.material-dialogs:color:$version"
    const val files = "com.afollestad.material-dialogs:files:$version"
    const val datetime = "com.afollestad.material-dialogs:datetime:$version"
    const val bottomSheets = "com.afollestad.material-dialogs:bottomsheets:$version"
    const val lifecycle = "com.afollestad.material-dialogs:lifecycle:$version"
}
