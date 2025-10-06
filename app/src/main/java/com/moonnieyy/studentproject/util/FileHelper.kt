package com.moonnieyy.studentproject.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context: Context) {
    val folderName = "myfolder"
    val filenName = "mydata.txt"

    private fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        if (!dir.exists()) {
            dir.mkdirs() // bikin folder kalau gaada
        }
        return File(dir, filenName)
    }

    fun writeToFile(data:String) {
        try {
            val file = getFile()
            FileOutputStream(file, false).use { // false disini itu boolean jaid kayak akan mereplace isi file
                    output -> output.write(data.toByteArray())
            }
        } catch (e:IOException) {
            e.printStackTrace() // untuk menampilkan pesan error
        }
    }

    fun readFromFile(): String {
        return try {
            val file = getFile()
            file.bufferedReader().useLines { lines ->
                lines.joinToString("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }
    }

    fun deleteFile(): Boolean {
        return getFile().delete()
    }

    fun getFilePath():String {
        return getFile().absolutePath
    }
}