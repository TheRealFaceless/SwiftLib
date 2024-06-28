plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "dev.faceless.swiftlib"
version = "1.0"

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
}

// Change Path
tasks.jar {
    manifest.attributes(
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version,
        "Implementation-Vendor" to group,
        "Paper-Mappings-Namespace" to "mojang"
    )

    val libPath = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\libraries"
    val default = "${layout.buildDirectory}\\libs"

    if (file(libPath).exists()) destinationDirectory.set(file(libPath))
    else destinationDirectory.set(file(default))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}