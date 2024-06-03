plugins {
    id ("com.android.application")
    id ("kotlin-android")
}


android {
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.vineelsai.rootchecker"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.1"
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        getByName("release") {
//            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    bundle {
        storeArchive {
            enable = true
        }
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }

    namespace ="com.vineelsai.rootchecker"
}

dependencies {
    // UI
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // 3rd Party
    implementation("eu.chainfire:libsuperuser:1.1.0")
    implementation(group = "org.bitbucket.b_c", name = "jose4j", version = "0.7.12")
    implementation("com.google.android.gms:play-services-fido:18.1.0")
//    implementation ("com.elvishew:xlog:1.10.1")
    implementation ("com.scottyab:rootbeer-lib:0.1.0")


    debugImplementation("com.apkfuns.logutils:library:1.7.5")
    releaseImplementation("com.apkfuns.logutils:logutils-no-op:1.7.5")


}

