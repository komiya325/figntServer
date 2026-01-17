plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.komiya325"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("tools.jackson.module:jackson-module-kotlin")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// .envファイルから環境変数を読み込む
fun loadEnv(): Map<String, String> {
	val envFile = file(".env")
	if (!envFile.exists()) return emptyMap()
	return envFile.readLines()
		.filter { it.isNotBlank() && !it.startsWith("#") && it.contains("=") }
		.associate {
			val (key, value) = it.split("=", limit = 2)
			key.trim() to value.trim()
		}
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
	val envVars = loadEnv()
	println("Loaded env vars: ${envVars.keys}")
	// 環境変数として渡す（Spring Bootが自動的にプロパティに変換）
	environment(envVars)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
