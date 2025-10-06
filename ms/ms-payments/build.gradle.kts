plugins {
    id("java")
    id("io.quarkus")
    id("skullprogrammer-quarkus-conventions") version "1.0.0"
    id("skullprogrammer-java-conventions") version "1.0.0"
}

group = "tech.skullprogrammer"
version = "1.0.0-SNAPSHOT"


dependencies {
    implementation(project(":ms:commons-event"))

    implementation("io.quarkus:quarkus-rest-jackson")

    implementation("tech.skullprogrammer.framework:framework-core:0.1.1")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    //KAFKA
    implementation("io.quarkus:quarkus-messaging-kafka")

    //TOOLS
    implementation ("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}