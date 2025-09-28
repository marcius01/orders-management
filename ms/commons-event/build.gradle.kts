plugins {
    id("java")
    id("skullprogrammer-java-conventions") version "1.0.0"
}

group = "tech.skullprogrammer"
version = "1.0.0"

val quarkusPlatformVersion: String by project

dependencies {
    compileOnly("io.quarkus:quarkus-core:${quarkusPlatformVersion}")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}