plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
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

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      consumerProguardFiles("datasource-proguard-rules.pro")
      buildConfigField(
        BuildConfigType.string,
        BuildConfigFields.nseBaseUrl,
        BuildConfigValues.nseBaseUrl
      )
      buildConfigField(
        BuildConfigType.string,
        BuildConfigFields.kotakResearchUrl,
        BuildConfigValues.kotakResearchUrl
      )
    }
    getByName("release") {
      isMinifyEnabled = true
      consumerProguardFiles("datasource-proguard-rules.pro")
      buildConfigField(
        BuildConfigType.string,
        BuildConfigFields.nseBaseUrl,
        BuildConfigValues.nseBaseUrl
      )
      buildConfigField(
        BuildConfigType.string,
        BuildConfigFields.kotakResearchUrl,
        BuildConfigValues.kotakResearchUrl
      )
    }
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
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

  // Hilt
  implementation(Libs.hiltCore)
  implementation(Libs.hiltCommon)
  implementation(Libs.hiltViewModelLifecycle)
  kapt(Libs.hiltDaggerAndroidCompiler)
  kapt(Libs.hiltCompiler)

  // Okhttp
  api(Libs.okHttp)
  api(Libs.okHttpLogging)

  // Retrofit and Moshi
  api(Libs.retrofit)
  api(Libs.moshiConverter)
  api(Libs.moshi)
  kapt(Libs.moshiCodeGen)

  // Room
  api(Libs.roomRuntime)
  api(Libs.roomKts)
  kapt(Libs.roomCompiler)

  // Kotlin preferences
  api(Libs.kotPref)
  api(Libs.kotPrefEnumSupport)

  //Gson
  api(Libs.gson)
  api(Libs.gsonConverter)

  implementation(Libs.jsoup)

  // Lifecycle
  implementation(Libs.lifecycleViewModel)
  implementation(Libs.lifecycleLiveDataKtx)
  implementation(Libs.lifecycleCommon)
  implementation(Libs.lifecycleExtension)
}