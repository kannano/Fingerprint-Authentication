// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
    classpath("com.android.tools.build:gradle:8.7.0")
        }
}

plugins {
    id("com.android.application") version "8.7.0" apply false
    id("com.android.library") version "8.7.0" apply false
         
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}