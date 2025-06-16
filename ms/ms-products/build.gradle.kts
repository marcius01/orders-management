plugins {
    id("java")
//    id("skullprogrammer-java-conventions")
//    id("skullprogrammer-quarkus-conventions")
    id("skullprogrammer-quarkus-conventions") version "1.0.0"
    id("skullprogrammer-java-conventions") version "1.0.0"
}

group = "tech.skullprogrammer"
version = "1.0.0-SNAPSHOT"

//repositories {
//    mavenCentral()
//}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}