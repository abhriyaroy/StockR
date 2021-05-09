plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("kotlin-android")
  id("androidx.navigation.safeargs")
  id("dagger.hilt.android.plugin")
  id("com.google.gms.google-services")
}
apply {
  plugin("kotlin-android")
}
android {
  compileSdkVersion(Apps.compileSdk)
  buildToolsVersion = Apps.buildToolsVersion
  defaultConfig {
    applicationId = "studio.zebro.stockr"
    minSdkVersion(Apps.minSdk)
    targetSdkVersion(Apps.targetSdk)
    versionCode = Apps.versionCode
    versionName = Apps.versionName
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
    getByName("release") {
      isMinifyEnabled = true
      isDebuggable = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
//      signingConfig = signingConfigs.getByName("release")
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
  sourceSets {
    getByName("main").java.srcDirs("build/generated/source/navigation-args")
  }
  lintOptions.isAbortOnError = false
  lintOptions.isCheckReleaseBuilds = false
}
dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(Libs.kotlin)
  implementation(Libs.appcompat)
  implementation(Libs.coreKtx)
  implementation(Libs.constraintLayout)
  implementation(Libs.recyclerView)
  implementation(Libs.designSupportLibrary)
  // Hilt
  implementation(Libs.hiltCore)
  implementation(Libs.hiltCommon)
  implementation(Libs.hiltViewModelLifecycle)
  kapt(Libs.hiltDaggerAndroidCompiler)
  kapt(Libs.hiltCompiler)
  // Navigation
  implementation(Libs.navComponentFragment)
  implementation(Libs.navComponentUi)
  // Okhttp
  implementation(Libs.okHttp)
  implementation(Libs.okHttpLogging)
  // Retrofit and Moshi
  implementation(Libs.retrofit)
  implementation(Libs.moshiConverter)
  implementation(Libs.moshi)
  kapt(Libs.moshiCodeGen)
  // Lifecycle
  implementation(Libs.lifecycleViewModel)
  implementation(Libs.lifecycleLiveDataKtx)
  implementation(Libs.lifecycleCommon)
  implementation(Libs.lifecycleExtension)
  // Firebase
  implementation(Libs.firebaseMessaging)
  implementation(Libs.firebaseAnalytics)
  implementation(Libs.MPAndroidChart)
  // Jsoup
  implementation(Libs.jsoup)

  implementation(project(Modules.recommendation))
  implementation(project(Modules.core))

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.junitExtension)
  androidTestImplementation(TestLibs.espressoCore)
}
repositories {
  maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
  mavenCentral()
}