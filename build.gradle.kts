plugins {
    id("java")
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.faceless.swiftlib"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("com.h2database:h2:2.2.224")
}

tasks.assemble {
    dependsOn("sourcesJar")
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveClassifier.set("")
    val path = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\Servers\\Paper 1.20.4\\plugins"
    if (file(path).exists()) destinationDirectory.set(file(path))
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets["main"].allJava)
    archiveClassifier.set("sources")
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}



