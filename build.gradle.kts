// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  val kotlin_version by extra("1.4.31")
    repositories {
    google()
    jcenter()
      maven { url = uri("https://jitpack.io") }
      maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
  }
  dependencies {
    classpath("com.android.tools.build:gradle:${Versions.gradle}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navComponent}")
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
    classpath("com.google.gms:google-services:${Versions.googleServices}")
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven { url = uri("https://jitpack.io") }
    maven {
      url  = uri("http://dl.bintray.com/countly/maven")
    }
    maven {
      url = uri("https://androidx.dev/snapshots/builds/6543454/artifacts/repository/")
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}