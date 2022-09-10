package com.example.demoapp.utils

import android.graphics.Bitmap
import java.io.*


object FileUtil {

    private var mInstance: FileUtil? = null

    private fun FileUtil() {}

    fun getInstance(): FileUtil? {
        if (mInstance == null) {
            synchronized(FileUtil::class.java) {
                if (mInstance == null) {
                    mInstance = FileUtil
                }
            }
        }
        return mInstance
    }

    /**
     * Stores the given [Bitmap] to a path on the device.
     *
     * @param bitmap   The [Bitmap] that needs to be stored
     * @param filePath The path in which the bitmap is going to be stored.
     */
    fun storeBitmap(bitmap: Bitmap, filePath: String?) {
        val imageFile = File(filePath)
        imageFile.parentFile.mkdirs()
        try {
            val fout: OutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout)
            fout.flush()
            fout.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}