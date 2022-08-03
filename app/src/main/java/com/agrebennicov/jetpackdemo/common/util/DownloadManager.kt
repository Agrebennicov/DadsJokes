package com.agrebennicov.jetpackdemo.common.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.agrebennicov.jetpackdemo.common.di.IO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.inject.Inject


data class DownloadManager @Inject constructor(
    @ApplicationContext
    private val context: Context,
    @IO
    private val IO: CoroutineDispatcher
) {
    suspend fun downloadImage(
        stringUrl: String,
        onDownloaded: () -> Unit,
        onError: () -> Unit
    ) {
        val result = runCatching {
            val image = withContext(IO) {
                changeBackgroundColor(
                    BitmapFactory.decodeStream(URL(stringUrl).openConnection().getInputStream())
                )
            }
            saveImageToGallery(image)
        }
        when {
            result.isSuccess -> onDownloaded()
            result.isFailure -> onError()
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= 29) {
            val values = imageContentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/DadJokes")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri: Uri? = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
        } else {
            val directory = File("${Environment.getExternalStorageDirectory()}/DadJokes")
            if (!directory.exists()) directory.mkdirs()
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            val values = imageContentValues()
            saveImageToStream(bitmap, FileOutputStream(file))
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            file.deleteOnExit()
        }
    }

    private fun imageContentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Changes transparent background colour that comes from API to white
     */
    private fun changeBackgroundColor(oldBitmap: Bitmap): Bitmap {
        val newBitmap = Bitmap.createBitmap(oldBitmap.width, oldBitmap.height, oldBitmap.config)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(oldBitmap, 0f, 0f, null)
        return newBitmap
    }
}
