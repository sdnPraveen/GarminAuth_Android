package com.app.garminauthdemo.Utility

fun String.seperatorWithCharacter(char: String): List<String> {
    return this.split(char)
}