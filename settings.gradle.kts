pluginManagement {
    repositories {
        google()
        mavenCentral() // Substituto para jcenter()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Primeiro para repositórios Android
        mavenCentral() // Substituto para jcenter()
        maven("https://jitpack.io") // JitPack para dependências personalizadas
    }
}

rootProject.name = "BolsoInteligente"
include(":app")
