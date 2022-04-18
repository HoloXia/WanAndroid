// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
        //阿里云jcenter仓库
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://s01.oss.sonatype.org/content/groups/public")
    }
    dependencies {
        classpath(ModulePlugin.android)
        classpath(ModulePlugin.kotlin)
        classpath(ModulePlugin.hilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
        //阿里云jcenter仓库
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://s01.oss.sonatype.org/content/groups/public")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}