plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
}

dependencies {
    modCompileOnly(libs.puzzleslib.common)
    modApi(libs.puzzleslib.neoforge)
    modCompileOnly(libs.statuemenus.common)
    modApi(libs.statuemenus.neoforge)
    include(libs.statuemenus.neoforge)
}
