package com.salman.trycar_test.data.util

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
object ImageGenerator {

    fun generate(uniqueNumber: Int): String {
        return "https://i.pravatar.cc/150?u=$uniqueNumber@pravatar.com"
    }
}