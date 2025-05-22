plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.gft.example.observablesession"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gft.example.observablesession"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName  = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility  = Java.sourceCompatibility
        targetCompatibility = Java.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Java.jvmTarget
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":observable-session"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.espresso)
}
