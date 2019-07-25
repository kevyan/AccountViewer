package com.example.mlappchallenge

import java.io.IOException
import java.io.InputStream

fun rawJSONToString(inputStream : InputStream): String? {
    var json: String? = null
    try {
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return json
}