package com.example.capstonefix.ui.Dashboard.dashboard.Fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.capstonefix.R
import com.example.capstonefix.databinding.FragmentScanBinding
import com.example.capstonefix.helper.ImageClassifierHelper
import com.example.capstonefix.ui.camera.CameraActivity
import com.example.capstonefix.ui.scan.ResultScanActivity
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.text.NumberFormat


class ScanFragment : Fragment() {
    companion object {
        private const val TAG = "ScanFragment"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
    private var currentImageUri: Uri? = null
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            ScanFragment.REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                currentImageUri = it
                showImage()
            } else {
                showToast("Gambar Gagal Diambil")
            }
        }

    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    val selectedImageUri = data.getStringExtra("selected_image")?.let { Uri.parse(it) }
                    currentImageUri = selectedImageUri
                    Log.d("test gambar", selectedImageUri.toString())
                    showImage()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.PopupContainer.visibility == View.VISIBLE) {
                    binding.PopupContainer.visibility = View.GONE
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentImageUri != null) {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.GONE
        }
        binding.PopupContainer.visibility = View.INVISIBLE
        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonCheck.setOnClickListener { showResultImage() }
        binding.btnDetail.setOnClickListener{analyzeImage()}
        binding.buttonCapture.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            cameraActivityResultLauncher.launch(intent)
        }
        binding.btnClose.setOnClickListener{
            binding.PopupContainer.visibility = View.GONE
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.selectedImageView.setImageURI(it)
        }
        if (currentImageUri != null) {
            binding.buttonCheck.visibility = View.VISIBLE
        } else {
            binding.buttonCheck.visibility = View.GONE
        }
    }

    private fun analyzeImage() {
        val intent = Intent(requireContext(), ResultScanActivity::class.java)
        intent.putExtra(ResultScanActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
        startActivity(intent)
        binding.PopupContainer.visibility = View.GONE
    }
    private fun showResultImage(){
        binding.PopupContainer.visibility = View.VISIBLE
        currentImageUri?.let {
            binding.gambarPopup.setImageURI(it)
        }
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResults(results: TensorBuffer?, inferenceTime: Long) {
                    activity?.runOnUiThread {
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
        imageClassifierHelper.classifyStaticImage(Uri.parse(currentImageUri.toString()))
    }
}