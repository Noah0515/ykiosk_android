plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.ykiosk_android_test"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ykiosk_android_test"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
val LATEST_VERSION = "2.20.0"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material3:material3-window-size-class")

    implementation("androidx.navigation:navigation-compose:2.9.6")
    //implementation("androidx.navigation3:navigation-core:1.") // 예시 버전
    //implementation("androidx.navigation3:navigation-compose:1.1.0") // 예시 버전

    implementation("com.kakao.sdk:v2-all:${LATEST_VERSION}")
// 전체 모듈 추가, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:${LATEST_VERSION}")
// 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-share:${LATEST_VERSION}")
// 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-talk:${LATEST_VERSION}")
// 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-friend:${LATEST_VERSION}")
// 피커 API 모듈
    implementation("com.kakao.sdk:v2-navi:${LATEST_VERSION}")
// 카카오내비 API 모듈
    implementation("com.kakao.sdk:v2-cert:${LATEST_VERSION}")
// 카카오톡 인증 서비스 API 모듈


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    implementation("io.coil-kt:coil-compose:2.6.0")

    implementation("androidx.compose.material:material-icons-extended:1.6.0")
}