plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {

    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            // DI
            implementation(libs.koin.android)
            //implementation(libs.koin.androidx.compose)

            // Room
            implementation(libs.room.runtime.android)
        }
        commonMain.dependencies {
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlin.datetime)

            // DI
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.vm)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
    }
}

android {
    namespace = "com.mytrainer.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mytrainer.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata" ) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}