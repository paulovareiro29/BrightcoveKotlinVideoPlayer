// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("kotlin_version", "1.3.50")
    }

    repositories {
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("http://repo.brightcove.com/releases") }
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("http://repo.brightcove.com/releases") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
