plugins {
    id("java")
    id("io.quarkus")
    id("skullprogrammer-quarkus-conventions") version "1.0.0"
    id("skullprogrammer-java-conventions") version "1.0.0"
}

group = "tech.skullprogrammer"
version = "1.0.0-SNAPSHOT"

dependencies {
    //To move to quarkus conventions plugin
    implementation("io.quarkus:quarkus-rest-jackson")

    implementation("tech.skullprogrammer.framework:framework-core:0.1.1")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-elytron-security-common")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    //TOOLS
    implementation ("org.mapstruct:mapstruct:1.6.3")

    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")
}

tasks.test {
    useJUnitPlatform()
}