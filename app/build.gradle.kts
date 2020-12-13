plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    //id("kotlin-parcelize")
    id("org.jetbrains.dokka-android") version Versions.DOKKA_ANDROID
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)

    defaultConfig {
        applicationId = "com.specialschool.schoolapp"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = Versions.versionCodeMobile
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SCHOOL_DATA_URL", "\"https://firebasestorage.googleapis.com/v0/b/schoolapp-488dc.appspot.com/o/school_data.json?alt=media&token=5befdc11-1264-4500-8b5b-4f039d8c29c6\"")
        buildConfigField("String", "BOOTSTRAP_SCHOOL_DATA_FILENAME", "\"school_data.json\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {

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
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}

dependencies {
    // Kotlin
    implementation(Libs.KOTLIN_STDLIB)

    implementation(Libs.CORE_KTX)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.MATERIAL)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.NAVIGATION_UI_KTX)
    testImplementation(Libs.ARCH_TESTING)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    kapt(Libs.LIFECYCLE_COMPILER)

    implementation(Libs.LEGACY_SUPPORT)
    implementation(Libs.GOOGLE_PLAY_SERVICES_MAPS)

    // Room
    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    kapt(Libs.ROOM_COMPILER)
    testImplementation(Libs.ROOM_KTX)
    testImplementation(Libs.ROOM_RUNTIME)

    // Test
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.HAMCREST)
    testImplementation(Libs.MOCKITO_CORE)
    testImplementation(Libs.MOCKITO_KOTLIN)
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.RULES)
    androidTestImplementation(Libs.RUNNER)

    // Hilt
    implementation(Libs.HILT_ANDROID)
    implementation(Libs.HILT_VIEWMODEL)
    androidTestImplementation(Libs.HILT_TESTING)
    kapt(Libs.HILT_COMPILER)
    kapt(Libs.ANDROIDX_HILT_COMPILER)
    kaptAndroidTest(Libs.HILT_COMPILER)
    kaptAndroidTest(Libs.ANDROIDX_HILT_COMPILER)

    // Coroutines
    api(Libs.COROUTINES)
    testImplementation(Libs.COROUTINES_TEST)

    // Firebase
    implementation(platform(Libs.FIREBASE_BOM))
    api(Libs.FIREBASE_AUTH)
    api(Libs.FIREBASE_CONFIG)
    api(Libs.FIREBASE_ANALYTICS)
    api(Libs.FIREBASE_FIRESTORE)
    api(Libs.FIREBASE_FUNCTIONS)
    api(Libs.FIREBASE_MESSAGING)

    // Firebase UI auth
    implementation(Libs.FIREBASE_UI_AUTH)

    // GSON
    implementation(Libs.GSON)

    // OkHttp
    implementation(Libs.OKHTTP)
    implementation(Libs.OKHTTP_LOGGING_INTERCEPTOR)

    // Glide
    implementation(Libs.GLIDE)
    kapt(Libs.GLIDE_COMPILER)
}

apply(plugin = "com.google.gms.google-services")

tasks {
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        includeNonPublic = true
    }
}
