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
  signingConfigs {
    create("release") {
      val properties = org.jetbrains.kotlin.konan.properties.Properties().apply {
        load(File("signing.properties").reader())
      }
      storeFile = File(properties.getProperty("storeFilePath"))
      storePassword = properties.getProperty("storePassword")
      keyPassword = properties.getProperty("keyPassword")
      keyAlias = properties.getProperty("keyAlias")
    }
  }
  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "research-proguard-rules.pro"
      )
    }
    getByName("release") {
      signingConfig = signingConfigs.getByName("release")
      isMinifyEnabled = true
      isDebuggable = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "research-proguard-rules.pro"
      )
//      signingConfig = signingConfigs.getByName("release")
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
  sourceSets {
    getByName("main").java.srcDirs("build/generated/source/navigation-args")
  }
  lintOptions.isAbortOnError = false
  lintOptions.isCheckReleaseBuilds = false
  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }
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
  implementation(Libs.mPAndroidChart)
  // Jsoup
  implementation(Libs.jsoup)
  // Lottie
  implementation(Libs.lottie)

  implementation("androidx.activity:activity-compose:1.3.1")
  implementation("androidx.compose.runtime:runtime:${Versions.compose}")
  implementation("androidx.compose.ui:ui:${Versions.compose}")
  implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
  implementation("androidx.compose.material:material:${Versions.compose}")
  implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
  implementation("androidx.compose.foundation:foundation:${Versions.compose}")
  implementation("androidx.compose.animation:animation:${Versions.compose}")
  implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
  implementation("androidx.compose.runtime:runtime-livedata:${Versions.compose}")

  implementation(project(Modules.research))
  implementation(project(Modules.core))

  testImplementation(TestLibs.junit)
  androidTestImplementation(TestLibs.junitExtension)
  androidTestImplementation(TestLibs.espressoCore)
}
repositories {
  mavenCentral()
}