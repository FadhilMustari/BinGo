package com.example.capstonefix.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import com.example.capstonefix.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import com.example.capstonefix.ml.Model
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassifierHelper(
    val context: Context,
    val classifierListener: ClassifierListener?,
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "model.tflite",
) {
    private var model: Model? = null
    val labels = listOf("Battery", "Biological", "Cardboard", "Clothes", "Glass","Metal","Paper","Plastic","Shoes","Trash") // Daftar label manual

    init {
        setupModel()
    }

    private fun setupModel() {
        try {
            model = Model.newInstance(context)
        } catch (e: IllegalStateException) {
            classifierListener?.onError("${e.message}")
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        if (model == null) {
            setupModel()
        }

        val bitmap = uriToBitmap(imageUri)
        if (bitmap == null) {
            classifierListener?.onError("Failed to convert URI to Bitmap.")
            return
        }

        // Ubah ukuran bitmap ke ukuran yang diharapkan oleh model
        val resizedBitmap = resizeBitmap(bitmap, 150, 150)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap, 150, 150)
        inputFeature0.loadBuffer(byteBuffer)

        var inferenceTime = SystemClock.uptimeMillis()
        val outputs = model?.process(inputFeature0)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        val outputFeature0 = outputs?.outputFeature0AsTensorBuffer

        classifierListener?.onResults(outputFeature0, inferenceTime)
        model?.close()
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
            null
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap, width: Int, height: Int): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * width * height * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(width * height)
        bitmap.getPixels(intValues, 0, width, 0, 0, width, height)
        var pixel = 0
        for (i in 0 until width) {
            for (j in 0 until height) {
                val value = intValues[pixel++]
                byteBuffer.putFloat((value shr 16 and 0xFF).toFloat())
                byteBuffer.putFloat((value shr 8 and 0xFF).toFloat())
                byteBuffer.putFloat((value and 0xFF).toFloat())
            }
        }
        return byteBuffer
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: TensorBuffer?, inferenceTime: Long)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
