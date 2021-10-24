plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("kotlin-android")
  id("dagger.hilt.android.plugin")
  id("androidx.navigation.safeargs")
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
        "research-proguard-rules.pro"
      )
    }
    getByName("release") {
      isMinifyEnabled = true
      consumerProguardFiles(
        "research-proguard-rules.pro"
      )
    }
  }
  buildFeatures {
    dataBinding = true
    compose = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
  implementation(Libs.kotlin)
  implementation(Libs.appcompat)
  implementation(Libs.coreKtx)
  implementation(Libs.constraintLayout)
  implementation(Libs.swipeRefresh)

  // Hilt
  implementation(Libs.hiltCore)
  implementation(Libs.hiltCommon)
  implementation(Libs.hiltViewModelLifecycle)
  implementation(Libs.hiltNavigationCompose)
  kapt(Libs.hiltDaggerAndroidCompiler)
  kapt(Libs.hiltCompiler)

  // Navigation
  implementation(Libs.navComponentFragment)
  implementation(Libs.navComponentUi)
  implementation(Libs.navigationCompose)

  // Lifecycle
  implementation(Libs.lifecycleViewModel)
  implementation(Libs.lifecycleLiveDataKtx)
  implementation(Libs.lifecycleCommon)
  implementation(Libs.lifecycleExtension)

  implementation(Libs.mPAndroidChart)

  // Compose
  implementation(Libs.composeActivityExtension)
  implementation(Libs.composeRuntime)
  implementation(Libs.composeUi)
  implementation(Libs.composeFoundationLayout)
  implementation(Libs.composeMaterial)
  implementation(Libs.composeIcons)
  implementation(Libs.composeFoundation)
  implementation(Libs.composeAnimation)
  implementation(Libs.composeUiTooling)
  implementation(Libs.composeRuntimeLiveData)
  implementation(Libs.composeViewModel)

  testImplementation(TestLibs.junit)
  testImplementation(TestLibs.junitExtension)
  testImplementation(TestLibs.espressoCore)
  testImplementation(TestLibs.mockitoKotlin)
  testImplementation(TestLibs.mockitoInline)

  implementation(project(Modules.core))
}