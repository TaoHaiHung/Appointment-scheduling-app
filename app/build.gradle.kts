plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.example.appdoctor"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.appdoctor"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    val lifecycle_version = "2.3.1"
    val nav_version = "2.5.0"
    val hilt_version = "2.44"
    implementation ("androidx.annotation:annotation:1.6.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Circle Image View
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //ViewPager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    //Material Design
    implementation ("com.google.android.material:material:1.3.0-alpha02")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Saved state module for ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    // optional - helpers for implementing LifecycleOwner in a Service
    implementation ("androidx.lifecycle:lifecycle-service:$lifecycle_version")
// optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation ("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    // optional - ReactiveStreams support for LiveData
    implementation ("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // For Kotlin use navigation-fragment-ktx
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")

    implementation ("com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-compiler:2.44")

    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    // radio Group
    implementation ("io.github.ihermandev:advanced-radio-group:1.0.0")
}
kapt {
    correctErrorTypes = true
}

