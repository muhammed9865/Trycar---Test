package com.salman.trycar_test.logger

import android.util.Log

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */

fun logInfo(any: Any) {
    Log.i("TryCarTest", any.toString())
}

fun logError(any: Any) {
    Log.e("TryCarTest", any.toString())
}

fun logDebug(any: Any) {
    Log.d("TryCarTest", any.toString())
}