plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = ModuleConfig.compileSdkVersion

    defaultConfig {
        minSdk = ModuleConfig.minSdkVersion
        targetSdk = ModuleConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    // Some libs (such as androidx.core:core-ktx 1.2.0 and newer) require Java 8
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // To avoid the compile error: "Cannot inline bytecode built with JVM target 1.8
    // into bytecode that is being built with JVM target 1.6"
    kotlinOptions {
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}

dependencies {
    // Android
    implementation(Android.appcompat)
    implementation(Android.startup)
    implementation(Android.coreKtx)
    implementation(Android.constraintLayout)
    implementation(Android.material)

    // Navigation
    implementation(Navigation.uiKtx)
    implementation(Navigation.fragmentKtx)

    // Architecture Components
    implementation(Lifecycle.runtimeKtx)

    // ImmersionBar
    api(Depends.immersionBar)
    api(Depends.immersionBarKtx)

    // Retrofit
    implementation(Retrofit.retrofit)

    // Gson
    implementation(Depends.gson)

    implementation(Depends.unPeekLiveData)

    implementation(MaterialDialogs.core)
    implementation(Depends.lottie)
    api(Depends.shimmer)
}