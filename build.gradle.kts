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

/*
tasks.assemble {
    dependsOn("sourcesJar")
}
tasks.jar {
    val paths = listOf("C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\libraries",
        "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\Servers\\Paper 1.20.4\\plugins")

    paths.forEach { p ->
        if (file(p).exists()) destinationDirectory.set(file(p))
    }
}
tasks.register<Jar>("sourcesJar") {
    val path = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\libraries"
    from(sourceSets["main"].allJava)
    archiveClassifier.set("sources")
    if (file(path).exists()) {
        destinationDirectory.set(file(path))
    }
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
*/


