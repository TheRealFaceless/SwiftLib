plugins {
    id("java")
    id("maven-publish")
}

group = "dev.faceless.swiftlib"
version = "1.1"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.assemble {
    dependsOn("sourcesJar")
}

tasks.jar {
    val path = "C:\\Users\\Faceless\\Desktop\\Minecraft DevKit\\Servers\\Paper 1.20.4\\plugins"
    if (file(path).exists()) {
        destinationDirectory.set(file(path))
    }
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets["main"].allJava)
    archiveClassifier.set("sources")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.wrapper {
    gradleVersion = "7.3.3"
    distributionType = Wrapper.DistributionType.ALL
}




