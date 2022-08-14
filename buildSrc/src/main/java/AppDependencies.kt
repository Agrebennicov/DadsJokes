import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //Core
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    // Firebase
    private const val analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.analytics}"
    private const val crashlytics =
        "com.google.firebase:firebase-crashlytics-ktx:${Versions.crashlytics}"

    // Compose
    private const val compose = "androidx.compose.ui:ui:${Versions.jetpackCompose}"
    private const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.jetpackCompose}"
    private const val composeFoundation =
        "androidx.compose.foundation:foundation:${Versions.jetpackCompose}"
    private const val composeMaterial =
        "androidx.compose.material:material:${Versions.jetpackCompose}"
    private const val composeIcons =
        "androidx.compose.material:material-icons-core:${Versions.jetpackCompose}"
    private const val composeIconsExtended =
        "androidx.compose.material:material-icons-extended:${Versions.jetpackCompose}"
    private const val composeActivity =
        "androidx.activity:activity-compose:${Versions.jetpackComposeActivity}"
    private const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.jetpackComposeViewModel}"

    // Navigation
    private const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    private const val hiltNavigation =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompiler}"

    // DI
    private const val hilt = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    private const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"
    private const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"

    // Networking
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val gsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    private const val okHttpInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpInterceptor}"

    // Database
    private const val room = "androidx.room:room-runtime:${Versions.room}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // UI
    private const val systemUiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
    private const val animationNavigation =
        "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
    private const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    private const val vectorAnimation =
        "androidx.compose.animation:animation-graphics:${Versions.vectorAnimation}"

    //test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val testCore = "androidx.test:core-ktx:${Versions.testCore}"

    val appLibraries = arrayListOf<String>().apply {
        add(coreKtx)
        add(compose)
        add(composeTooling)
        add(composeFoundation)
        add(composeMaterial)
        add(composeIcons)
        add(composeIconsExtended)
        add(composeActivity)
        add(composeViewModel)
        add(navigation)
        add(hilt)
        add(hiltNavigation)
        add(retrofit)
        add(gsonConverter)
        add(okHttpInterceptor)
        add(room)
        add(roomKtx)
        add(systemUiController)
        add(animationNavigation)
        add(constraintLayout)
        add(lottie)
        add(vectorAnimation)
        add(crashlytics)
        add(analytics)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
        add(roomCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(testCore)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}