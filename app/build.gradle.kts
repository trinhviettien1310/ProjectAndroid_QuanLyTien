plugins {
    id("com.android.application")

    id("com.google.gms.google-services")


}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

android {
    namespace = "com.example.projectver3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projectver3"
        minSdk = 28
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    //realtime
    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))

    implementation("com.google.firebase:firebase-database")

    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-storage:17.0.0")
    implementation("com.google.firebase:firebase-auth:9.2.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    //swipe
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("com.squareup.okhttp3:okhttp:3.14.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.cardview:cardview:1.0.0")

    //mail
    implementation ("com.sun.mail:android-mail:1.6.6")
    implementation ("com.sun.mail:android-activation:1.6.7")

    //firebase
    implementation (platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth:9.0.2")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-storage:17.0.0")
    implementation("com.google.firebase:firebase-auth:9.2.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")

    //Glide
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Facebook
    implementation ("com.facebook.android:facebook-android-sdk:[4,5)")
    implementation ("com.facebook.android:facebook-android-sdk:latest.release")

    //gg
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // Play Services
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.gms:play-services-oss-licenses:17.0.1")


    //bo tron
    implementation ("de.hdodenhof:circleimageview:3.1.0")
}
apply (plugin = "com.google.gms.google-services")
