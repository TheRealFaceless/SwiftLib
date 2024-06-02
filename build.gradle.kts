plugins {
    id("java")
}

group = "dev.faceless.swiftlib"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}


tasks.assemble {
    dependsOn("sourcesJar")
}
tasks.jar {
    val path = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\libraries"
    val default = "${layout.buildDirectory}\\libs"

    if (file(path).exists()) destinationDirectory.set(file(path))
    else {
        destinationDirectory.set(file(default))
    }
}
tasks.register<Jar>("sourcesJar") {
    val path = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\libraries"
    val default = "${layout.buildDirectory}\\libs"

    from(sourceSets["main"].allJava)
    archiveClassifier.set("sources")

    if (file(path).exists()) destinationDirectory.set(file(path))
    else {
        destinationDirectory.set(file(default))
        logger.warn("Default directory $path does not exist.")
    }
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}