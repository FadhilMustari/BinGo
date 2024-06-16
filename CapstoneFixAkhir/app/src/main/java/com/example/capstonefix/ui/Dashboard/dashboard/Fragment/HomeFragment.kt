package com.example.capstonefix.ui.Dashboard.dashboard.Fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.translation.Translator
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.capstonefix.PanduanActivity
import com.example.capstonefix.ui.camera.CameraActivity
import com.example.capstonefix.ui.scan.ResultScanActivity
import com.example.capstonefix.databinding.FragmentHomeBinding
import com.example.capstonefix.helper.ImageClassifierHelper
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import org.json.JSONObject
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val CITY : String = "Jakarta,id"
    val API: String = "3690405814c5f58d29fd1fe91ffc748c"
    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    inner class weatherTask() : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                    .readText(Charsets.UTF_8)
            }
            catch (e:Exception){
                response = null
            }
            return response
        }

        override fun onPreExecute() {
            super.onPreExecute()
            binding.pbLogin.visibility = View.VISIBLE
            binding.pbsunrise.visibility = View.VISIBLE
            binding.pbsunset.visibility = View.VISIBLE
            binding.pbwind.visibility = View.VISIBLE

            binding.sunrise.visibility = View.GONE
            binding.sunset.visibility = View.GONE
            binding.wind.visibility = View.GONE
            binding.containerWeather.visibility = View.GONE

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // Check if _binding is not null
            _binding?.let { binding ->
                try {
                    val jsonOBJ = JSONObject(result)
                    Log.d("test bang", jsonOBJ.toString())
                    val main = jsonOBJ.getJSONObject("main")
                    val sys = jsonOBJ.getJSONObject("sys")
                    val wind = jsonOBJ.getJSONObject("wind")
                    val wind2 = wind.getString("speed")
                    val weather = jsonOBJ.getJSONArray("weather").getJSONObject(0)
                    val updateAt: Long = jsonOBJ.getLong("dt")
                    val updateAt2 = "diperbarui pada : " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updateAt * 1000)
                    )
                    val temp = main.getString("temp") + "°C"
                    val sunrise: Long = sys.getLong("sunrise")
                    val sunset: Long = sys.getLong("sunset")
                    val deskripsi = weather.getString("description")
                    val address = jsonOBJ.getString("name") + ", " + sys.getString("country")

                    translateText(deskripsi)

                    binding.lokasi.text = address
                    binding.update.text = updateAt2
                    binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                    binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                    binding.wind.text = wind2
                    binding.temp.text = temp

                    binding.pbLogin.visibility = View.GONE
                    binding.pbsunrise.visibility = View.GONE
                    binding.pbsunset.visibility = View.GONE
                    binding.pbwind.visibility = View.GONE

                    binding.sunrise.visibility = View.VISIBLE
                    binding.sunset.visibility = View.VISIBLE
                    binding.wind.visibility = View.VISIBLE
                    binding.containerWeather.visibility = View.VISIBLE

                } catch (e: Exception) {
                    binding.pbLogin.visibility = View.GONE
                    Toast.makeText(requireContext(), "Gagal Mengambil Data Cuaca", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    private fun translateText(detectedText: String?) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.INDONESIAN)
            .build()
        val indonesianEnglishTranslator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        indonesianEnglishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                indonesianEnglishTranslator.translate(detectedText.toString())
                    .addOnSuccessListener { translatedText ->
                        Log.d("test translate bang",translatedText)
                        binding.status.text = translatedText
                        indonesianEnglishTranslator.close()
                    }
                    .addOnFailureListener { exception ->
                        showToast("Gagal mendapat info cuaca")
                        print(exception.stackTrace)
                        indonesianEnglishTranslator.close()
                    }
            }
            .addOnFailureListener { exception ->
                showToast("Mengunduh resource cuaca, Buka kembali Aplikasi setelah unduhan selesai")
                Log.d("test cuaca", exception.message.toString())
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")
        binding.sapaan.text = "Halo, $username !!"

        binding.gambarArtikel.setOnClickListener{
            val intent = Intent(requireContext(), PanduanActivity::class.java)
            startActivity(intent)
        }
        binding.cardArtikel.setOnClickListener{
            val intent = Intent(requireContext(), PanduanActivity::class.java)
            startActivity(intent)
        }
        weatherTask().execute()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
