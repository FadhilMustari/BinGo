package com.example.capstonefix.ui.scan

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.capstonefix.databinding.ActivityResultScanBinding
import com.example.capstonefix.helper.ImageClassifierHelper
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.text.NumberFormat

class ResultScanActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var binding : ActivityResultScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            binding.gambarResult.setImageURI(it)
        }


        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@ResultScanActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResults(results: TensorBuffer?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {
                            val outputArray = it.floatArray
                            val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1

                            if (maxIndex != -1) {
                                val maxLabel = imageClassifierHelper.labels.getOrNull(maxIndex) ?: "Unknown"
                                val maxScore = NumberFormat.getPercentInstance().format(outputArray[maxIndex]).trim()
                                binding.resultNamaSampah.text = maxLabel
                                when(maxLabel){
                                    "Biological" -> {
                                        binding.resultJenisSampah.text = "Organik"
                                    }
                                    "Metal" , "Glass" , "Paper", "Cardboard" , "Shoes", "Clothes", "Plastic"-> {
                                        binding.resultJenisSampah.text = "Anorganik"
                                    }
                                    "Battery" , "Trash" -> {
                                        binding.resultJenisSampah.text = "B3 (Bahan Berbahaya dan Beracun)"
                                    }
                                }
                            } else {
                                binding.resultNamaSampah.text = ""
                                binding.resultJenisSampah.text = ""
                            }
                        } ?: run {
                            binding.resultNamaSampah.text = ""
                            binding.resultJenisSampah.text = ""
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(imageUri)
    }
}