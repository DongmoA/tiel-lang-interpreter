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

// Pin the JDK version (must match the JDK-23 configured in Jenkins)
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}

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

// Enable preview features at COMPILE time (+ release so javac accepts --enable-preview)
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
