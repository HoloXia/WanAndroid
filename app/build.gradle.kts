plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ModuleConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.holo.wanandroid"
        minSdk = ModuleConfig.minSdkVersion
        targetSdk = ModuleConfig.targetSdkVersion
        versionCode = ModuleConfig.versionCode
        versionName = ModuleConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions.annotationProcessorOptions.arguments["room.schemaLocation"] = "$projectDir/schemas"
        javaCompileOptions.annotationProcessorOptions.arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
    }
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    //单元测试组件库
    testImplementation(Testing.jUnit)
    androidTestImplementation(Testing.extJUnit)
    androidTestImplementation(Testing.testRunner)
    androidTestImplementation(Testing.espresso)

    // BaseMvvm
    implementation(project(":BaseMvvm"))

    // Android
    implementation(Android.appcompat)
    implementation(Android.recyclerView)
    implementation(Android.activityKtx)
    implementation(Android.coreKtx)
    implementation(Android.constraintLayout)
    implementation(Android.swipeRefreshLayout)
    implementation(Android.material)
    implementation(Android.flexBox)
    implementation(MaterialDialogs.core)
    implementation(MaterialDialogs.bottomSheets)
    implementation(MaterialDialogs.lifecycle)

    // Navigation
    implementation(Navigation.uiKtx)
    implementation(Navigation.fragmentKtx)

    // Architecture Components
    implementation(Lifecycle.viewModel)
    implementation(Lifecycle.liveData)
    implementation(Lifecycle.runtimeKtx)

    // Hilt + Dagger
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.daggerCompiler)
    kapt(Hilt.hiltCompiler)

    // Room components
    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)

    // Coil-kt
    implementation(Depends.coil)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.convertGson)
    implementation(Retrofit.loggingInterceptor)
    implementation(Depends.persistentCookieJar)

    //腾讯内存映射组件
    implementation(Depends.mmkv)

    //webView组件
    implementation(Depends.agentWeb)

    //阴影
    implementation(Depends.shadowLayout)

    implementation(Depends.BRVAH)
    implementation(SmartRefreshLayout.core)
    implementation(SmartRefreshLayout.headerMaterial)

    implementation(Depends.lottie)
    implementation(Depends.magicIndicator)

    implementation(Depends.banner)
    implementation(Depends.likeView)
    implementation(Depends.loadState)
}