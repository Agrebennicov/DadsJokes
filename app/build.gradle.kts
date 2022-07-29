plugins {
    id("com.android.application")
    id("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    kotlin("android")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.agrebennicov.jetpackdemo"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName(BuildTypes.debug) {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }

        getByName(BuildTypes.release) {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions(AppConfig.dimension)
    productFlavors {
        create(BuildFlavor.production) {
            dimension = AppConfig.dimension
        }

        create(BuildFlavor.sandbox) {
            applicationIdSuffix = ".${BuildFlavor.sandbox}"
            dimension = AppConfig.dimension
        }
    }

    packagingOptions {
        excludes.plusAssign("META-INF/notice.txt")
        excludes.plusAssign("/META-INF/{AL2.0,LGPL2.1}")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=compatibility"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerVersion = Versions.jetpackComposeCompiler
        kotlinCompilerExtensionVersion = Versions.jetpackComposeCompilerExtension
    }
}

dependencies {
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(AppDependencies.appLibraries)
    kapt(AppDependencies.kaptLibraries)

    //test libs
    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}