object Apps {
    const val compileSdk = 30
    const val buildToolsVersion = "30.0.2"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0-alpha"
}
object Versions {
    const val gradle = "4.1.0"
    const val kotlin = "1.4.31"
    const val appcompat = "1.2.0"
    const val coreKtx = "1.3.2"
    const val designSupport = "1.2.1"
    const val constraintLayout = "2.0.2"
    const val securityCrypto = "1.0.0-rc04"
    const val lifecycle = "2.2.0"
    const val recyclerView = "1.1.0"
    const val cardView = "1.0.0"
    const val swipeRefresh = "1.0.0"
    const val navComponent = "2.3.0"
    const val hilt = "2.28-alpha"
    const val hiltSnapShot = "1.0.0-alpha01"
    const val okHttp = "4.8.1"
    const val retrofit = "2.9.0"
    const val moshi = "1.9.2"
    const val gson = "2.8.6"
    const val room = "2.2.5"
    const val kotPref = "2.6.0"
    const val threetenAbp = "1.2.0"
    const val timber = "4.7.1"
    const val eventBus = "3.1.1"
    const val everionDecoder = "2.9.5@aar"
    const val mpChart = "v3.1.0"
    const val firebaseMessaging = "20.2.4"
    const val firebaseAnalytics = "17.5.0"
    const val googleServices = "4.3.3"
    const val slidingUpPanel = "3.4.0"
    const val twilioVideo = "5.12.0"
    const val twilioChat = "6.1.0"
    const val lottie = "3.1.0"
    const val glide = "4.11.0"
    const val glideKapt = "4.11.0"
    const val glideVector = "v2.0.0"
    const val lottieSwipeRefresh ="1.0.0"
    const val androidSvg = "1.3"
    // const val countlyVersion = "18.08.1"
    const val countlyVersion = "19.09.3"
    const val loader = "1.4.0"
    // Jsoup
    const val jsoup = "1.13.1"
    // Test
    const val junit = "4.12"
    const val junit_extension = "1.1.2"
    const val espresso_core = "3.3.0"

    const val youtubePlayerView = "10.0.5"
}
object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val swipeRefresh =  "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
    const val designSupportLibrary =
        "com.google.android.material:material:${Versions.designSupport}"
    const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCrypto}"
    // Hilt
    const val hiltCore = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltDaggerAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltCommon = "androidx.hilt:hilt-common:${Versions.hiltSnapShot}"
    const val hiltViewModelLifecycle =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltSnapShot}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltSnapShot}"
    // Navigation component
    const val navComponentFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navComponent}"
    const val navComponentUi = "androidx.navigation:navigation-ui-ktx:${Versions.navComponent}"
    // OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    // Retrofit and Mosi
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    // Gson
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.8.1"
    // Kotlin preferences
    const val kotPref = "com.chibatching.kotpref:kotpref:${Versions.kotPref}"
    const val kotPrefEnumSupport = "com.chibatching.kotpref:enum-support:${Versions.kotPref}"
    // Room Database
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKts = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    // Date time
    const val threetenAbp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenAbp}"
    // Lifecycle
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val mPAndroidChart = "com.github.PhilJay:MPAndroidChart:${Versions.mpChart}"
    // Firebase
    const val firebaseMessaging =
        "com.google.firebase:firebase-messaging:${Versions.firebaseMessaging}"
    const val firebaseAnalytics =
        "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"
    //Lottie & Glide
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideKapt = "com.github.bumptech.glide:compiler:${Versions.glideKapt}"
    const val glideVector = "com.github.corouteam:GlideToVectorYou:${Versions.glideVector}"
    const val lottieSwipeRefresh = "com.github.nabil6391:LottieSwipeRefreshLayout:${Versions.lottieSwipeRefresh}"
    //Loader
    const val loader = "com.github.ybq:Android-SpinKit:${Versions.loader}"
    //Jsoup
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
}
object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val junitExtension = "androidx.test.ext:junit:${Versions.junit_extension}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
}
object Modules {
    const val datasource = ":datasource"
    const val core = ":core"
    const val recommendation = ":recommendation"
}