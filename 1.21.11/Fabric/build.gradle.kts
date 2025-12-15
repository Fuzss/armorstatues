plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

dependencies {
    modApi(libs.fabricapi.fabric)
    modApi(libs.puzzleslib.fabric)
    modApi(libs.statuemenus.fabric)
    include(libs.statuemenus.fabric)
}
