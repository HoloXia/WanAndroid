/**
 * androidx 相关依赖库
 *
 * @Author holo
 * @Date 2022/2/14
 */
object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
}

object Android {
    /**
     * appcompat中默认引入了很多库(比如activity库、fragment库、core库、annotation库、drawerlayout库、appcompat-resources)
     * 如果想使用其中某个库的更新版本，可以单独引用，比如下面的vectordrawable
     * 提示：对于声明式依赖，同一个库的不同版本，gradle会自动使用最新版本来进行依赖替换、编译
     */
    const val appcompat = "androidx.appcompat:appcompat:1.4.1"

    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

    // startup
    const val startup = "androidx.startup:startup-runtime:1.1.0"

    // activity+ktx扩展函数
    const val activityKtx = "androidx.activity:activity-ktx:1.4.0"

    // fragment+ktx扩展函数
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.4.0"

    // core包+ktx扩展函数
    const val coreKtx = "androidx.core:core-ktx:1.7.0"

    // 约束布局
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"

    // swipeRefreshLayout
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01"

    //material包
    const val material = "com.google.android.material:material:1.5.0-alpha01"

    const val webkit = "androidx.webkit:webkit:1.4.0"

    const val flexBox = "com.google.android.flexbox:flexbox:3.0.0"
}

object ViewPager {
    //viewpager
    const val viewpager = "androidx.viewpager:viewpager:1.0.0"

    //viewpager2
    const val viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-beta01"
}

object Room {
    private const val version = "2.4.0"
    const val compiler = "androidx.room:room-compiler:${version}"
    const val ktx = "androidx.room:room-ktx:${version}"
    const val runtime = "androidx.room:room-runtime:${version}"
}

object Hilt {
    private const val version = "2.40.5"
    const val hiltAndroid = "com.google.dagger:hilt-android:${version}"
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${version}"

    private const val xversion = "1.0.0-alpha03"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${xversion}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${xversion}"
}

object Lifecycle {
    private const val version = "2.4.0"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${version}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${version}"
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${version}"
}

object Navigation {
    //这个版本支持多返回栈了
    private const val version = "2.4.1"

    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
    const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
}

object Testing {
    const val jUnit = "junit:junit:4.13.2"
    const val extJUnit = "androidx.test.ext:junit:1.1.3"
    const val testRunner = "androidx.test:runner:1.4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1"
    const val room = "androidx.room:room-testing:2.2.6"
    const val okHttp = "com.squareup.okhttp3:mockwebserver:4.9.0"
    const val core = "androidx.arch.core:core-testing:2.1.0"
    const val truth = "com.google.truth:truth:1.1.3"
}

