import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.jvm.get().pluginId)
  alias(libs.plugins.ksp)
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(libs.kotlin.stdlib)
  testImplementation(libs.kotlinx.knit.test)
  testImplementation(libs.arrow.core)
  testImplementation(libs.arrow.fx.coroutines)
  testImplementation(libs.arrow.resilience)
  testImplementation(libs.arrow.fx.stm)
  testImplementation(libs.arrow.optics)
  testImplementation(libs.arrow.opticsReflect)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.property)
  testImplementation(libs.kotest.runner.junit)
  testImplementation(libs.kotest.framework.engine)
  testImplementation(libs.suspendapp)
  testImplementation(libs.kafka.kotlin)
  ksp(libs.arrow.opticsPlugin)
}

sourceSets.test {
  java.srcDirs("examples", "test")
}

tasks {
  withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
      setExceptionFormat("full")
      setEvents(listOf("passed", "skipped", "failed", "standardOut", "standardError"))
    }
  }

  withType<KotlinCompile>().configureEach {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
  }
}
