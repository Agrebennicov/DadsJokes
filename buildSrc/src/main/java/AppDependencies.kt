import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //Core
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

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
    private const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompiler}"

    // DI
    private const val hilt = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"
    private const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"

    //test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

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
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
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