val kotlin_version: String by extra
plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("kotlin-android")
  id("androidx.navigation.safeargs")
  id("dagger.hilt.android.plugin")
}
apply {
  plugin("kotlin-android")
}

android {
  compileSdkVersion(Apps.compileSdk)
  buildToolsVersion = Apps.buildToolsVersion

  defaultConfig {
    minSdkVersion(Apps.minSdk)
    targetSdkVersion(Apps.targetSdk)
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      consumerProguardFiles(
        "core-proguard-rules.pro"
      )
    }
    getByName("release") {
      isMinifyEnabled = true
      consumerProguardFiles(
        "core-proguard-rules.pro"
      )
    }
  }
  buildFeatures {
    dataBinding = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

dependencies {
  api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
  api(Libs.kotlin)
  api(Libs.appcompat)
  api(Libs.coreKtx)
  api(Libs.recyclerView)
  api(Libs.constraintLayout)
  api(Libs.designSupportLibrary)
  // Navigation
  api(Libs.navComponentFragment)
  api(Libs.navComponentUi)
  // Hilt
  api(Libs.hiltCore)
  api(Libs.hiltCommon)
  api(Libs.hiltViewModelLifecycle)
  kapt(Libs.hiltDaggerAndroidCompiler)
  kapt(Libs.hiltCompiler)
  // Lottie
  api(Libs.lottie)

  api(project(Modules.datasource))
}

repositories {
  maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
  mavenCentral()
}