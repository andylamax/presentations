plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("net.kodein.cup") version "1.0.0-Beta-05"
}

cup {
    targetDesktop()
    targetWeb()
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(cup.sourceCode)
            implementation(cup.plugin.speakerWindow)
            implementation(cup.plugin.laser)
            implementation(cup.widgets.material)

            implementation(compose.material)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
            implementation("org.kodein.emoji:emoji-compose-m2:1.3.0")
        }
    }
}