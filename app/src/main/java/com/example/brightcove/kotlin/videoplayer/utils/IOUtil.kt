package com.example.brightcove.kotlin.videoplayer.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

object IOUtil {

    fun readStringFromAssets(context: Context, filePath: String): String =
        streamToString(context.assets.open(filePath))

    fun streamToString(inputStream: InputStream): String =
        readerToString(InputStreamReader(inputStream))

    //    @Throws(IOException::class)
    private fun readerToString(reader: Reader): String {
        var line: String? = null
        val sb = StringBuilder()
        val rd = BufferedReader(reader)
        rd.use { rd ->
            // Read response until the end
            while (rd.readLine().also { line = it } != null) {
                sb.append(line)
            }
        }
        return sb.toString()
    }
}