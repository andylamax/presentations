plugins {
    alias(androidx.plugins.library) apply false
    alias(kotlinz.plugins.root.multiplatform) apply false
    alias(kotlinz.plugins.root.serialization) apply false
    alias(kotlinz.plugins.root.compose.compiler) apply false
    alias(kotlinz.plugins.compose) apply false
    alias(kotlinz.plugins.root.android) apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

val v = "0.0.0"

group = "dev.andylamax"
version = v

subprojects {
    version = v
}