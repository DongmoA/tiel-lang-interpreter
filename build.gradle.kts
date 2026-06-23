import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("application")
}

group = "de.thm.asc.tiel.interpreter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("info.picocli:picocli:4.7.7")
}

application {
    mainClass.set("de.thm.asc.tiel.interpreter.Main")
    // Enable preview features at runtime (gradle run)
    applicationDefaultJvmArgs = listOf("--enable-preview")
}

// No toolchain block here: Jenkins already runs Gradle with the JDK-23 tool
// (see Jenkinsfile `tools { jdk 'JDK-23' }`). Adding a toolchain would make
// Gradle search for a separate JDK 23 install and fail with
// "Cannot find a Java installation".

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("tiel")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.thm.asc.tiel.interpreter.Main"))
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

// Enable preview features at COMPILE time.
// release 23 is valid because Jenkins now provides a real JDK 23.
tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
    options.release.set(23)
}

// Enable preview features when RUNNING tests
tasks.withType<Test> {
    jvmArgs("--enable-preview")
}

// Enable preview features for any Java execution (e.g. run task)
tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}
