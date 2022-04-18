/**
 * Gradle插件库
 *
 * @Author holo
 * @Date 2022/2/14
 */
object ModulePlugin {
    private const val gradle_version = "7.0.4"
    const val android = "com.android.tools.build:gradle:$gradle_version"

    private const val kotlin_version = "1.6.10"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

    private const val hilt_version = "2.40.5"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
}