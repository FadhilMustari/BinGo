package com.example.capstonefix.ui.scan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.capstonefix.databinding.ActivityResultScanBinding
import com.example.capstonefix.helper.ImageClassifierHelper
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity
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

        binding.back.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
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
                                        showOrganikDetail()
                                    }
                                    "Metal" , "Glass" , "Paper", "Cardboard" , "Shoes", "Clothes", "Plastic"-> {
                                        binding.resultJenisSampah.text = "Anorganik"
                                        showAnorganikDetail()

                                    }
                                    "Battery" , "Trash" -> {
                                        binding.resultJenisSampah.text = "B3 (Bahan Berbahaya dan Beracun)"
                                        showB3Detail()
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
    private fun showOrganikDetail() {
        val bingoTipeSampah = """BinGo! Sampah kamu termasuk tipe sampah Organik!"""
        val detailSampah = """Apa Itu? Sampah organik adalah sampah yang berasal dari makhluk hidup, mudah terurai, dan bisa membusuk. Contohnya seperti sisa makanan, daun kering, kulit buah, dan rumput. Eits, jangan dibuang sembarangan! Sampah organik ini punya manfaat lho! Yuk, ikuti langkah bijak BinGo untuk mengolahnya!"""
        val judulLangkahPertama ="""Langkah 1: Pilah Sampahmu!"""
        val langkahPertama = """Pisahkan sampah organikmu dari sampah lain. Pastikan sampahnya bersih dari plastik, karet, atau bahan lain yang tidak mudah terurai."""
        val judulLangkahKedua ="""Langkah 2: Pilih Opsi Terbaik!"""
        val judulLangkahKetiga ="""Langkah 3: Jaga Bumi Kita!"""

        binding.bingoTipeSampah.text = bingoTipeSampah
        binding.bingoTipeSampah.visibility = android.view.View.VISIBLE
        binding.detailSampah.text = detailSampah
        binding.detailSampah.visibility = android.view.View.VISIBLE
        binding.judulLangkahPertama.text = judulLangkahPertama
        binding.judulLangkahPertama.visibility = android.view.View.VISIBLE
        binding.langkahPertama.text = langkahPertama
        binding.langkahPertama.visibility = android.view.View.VISIBLE
        binding.judulLangkahKedua.text = judulLangkahKedua
        binding.judulLangkahKedua.visibility = android.view.View.VISIBLE
        binding.bullet12.visibility = android.view.View.VISIBLE
        binding.text12.visibility = android.view.View.VISIBLE
        binding.bullet13.visibility = android.view.View.VISIBLE
        binding.text13.visibility = android.view.View.VISIBLE
        binding.bullet14.visibility = android.view.View.VISIBLE
        binding.text14.visibility = android.view.View.VISIBLE
        binding.bullet19.visibility = android.view.View.GONE
        binding.text19.visibility = android.view.View.GONE
        binding.bullet20.visibility = android.view.View.GONE
        binding.text20.visibility = android.view.View.GONE
        binding.bullet21.visibility = android.view.View.GONE
        binding.text21.visibility = android.view.View.GONE
        binding.bullet22.visibility = android.view.View.GONE
        binding.text22.visibility = android.view.View.GONE
        binding.bullet23.visibility = android.view.View.GONE
        binding.text23.visibility = android.view.View.GONE
        binding.bullet24.visibility = android.view.View.GONE
        binding.text24.visibility = android.view.View.GONE
        binding.judulLangkahKetiga.text = judulLangkahKetiga
        binding.judulLangkahKetiga.visibility = android.view.View.VISIBLE
        binding.bullet15.visibility = android.view.View.VISIBLE
        binding.text15.visibility = android.view.View.VISIBLE
        binding.bullet16.visibility = android.view.View.VISIBLE
        binding.text16.visibility = android.view.View.VISIBLE
        binding.bullet17.visibility = android.view.View.VISIBLE
        binding.text17.visibility = android.view.View.VISIBLE
        binding.bullet18.visibility = android.view.View.VISIBLE
        binding.text18.visibility = android.view.View.VISIBLE
        binding.bullet25.visibility = android.view.View.GONE
        binding.text25.visibility = android.view.View.GONE
    }

    private fun showAnorganikDetail() {
        val bingoTipeSampah = """BinGo! Sampah kamu termasuk tipe sampah Anorganik!"""
        val detailSampah = """Apa Itu? Sampah anorganik adalah sampah yang tidak dapat terurai secara alami dan terbuat dari berbagai bahan sintetis. Contohnya seperti plastik, kaleng, kaca, dan elektronik."""
        val judulLangkahPertama ="""Langkah 1: Perhatikan Sampahmu!"""
        val langkahPertama = """Perhatikan apakah sampahmu ini masih layak guna? Bila iya kamu bisa pisahkan sampah-mu itu dari sampah lain dan bersihkan dari kotoran."""
        val judulLangkahKedua ="""Langkah 2: Pilih Opsi Terbaik!"""
        val langkahKeduaPartSatu="""Donasi: Berikan sampah layak guna-mu ke orang yang membutuhkan melalui organisasi sosial atau bank sampah."""
        val langkahKeduaPartDua="""Jual Kembali: Jual sampah-mu dalam kondisi baik di platform online atau pasar loak."""
        val langkahKeduaPartTiga="""Daur Ulang: Hubungi jasa daur ulang untuk sepatu yang sudah tidak terpakai."""
        val langkahKeduaPartEmpat="""Kreasi Ulang: Sulap sampah-mu menjadi dekorasi rumah, pot tanaman, atau karya seni unik, kamu bisa cari berbagai inspirasi kreasi sampah di internet!"""
        val judulLangkahKetiga ="""Langkah 3: Jaga Bumi Kita!"""
        val langkahKetigaPartSatu="""Hindari pembelian berlebihan dan pilih produk yang tahan lama dan eco-friendly."""
        val langkahKetigaPartDua="""Rawat barangmu dengan baik agar awet dan tidak cepat dibuang."""
        val langkahKetigaPartTiga="""Ingat selalu 3R! Reduce, Reuse, dan Recycle."""

        binding.bingoTipeSampah.text = bingoTipeSampah
        binding.bingoTipeSampah.visibility = android.view.View.VISIBLE
        binding.detailSampah.text = detailSampah
        binding.detailSampah.visibility = android.view.View.VISIBLE
        binding.judulLangkahPertama.text = judulLangkahPertama
        binding.judulLangkahPertama.visibility = android.view.View.VISIBLE
        binding.langkahPertama.text = langkahPertama
        binding.langkahPertama.visibility = android.view.View.VISIBLE
        binding.judulLangkahKedua.text = judulLangkahKedua
        binding.judulLangkahKedua.visibility = android.view.View.VISIBLE
        binding.bullet12.visibility = android.view.View.VISIBLE
        binding.text12.text = langkahKeduaPartSatu
        binding.text12.visibility = android.view.View.VISIBLE
        binding.bullet13.visibility = android.view.View.VISIBLE
        binding.text13.text = langkahKeduaPartDua
        binding.text13.visibility = android.view.View.VISIBLE
        binding.bullet14.visibility = android.view.View.VISIBLE
        binding.text14.text = langkahKeduaPartTiga
        binding.text14.visibility = android.view.View.VISIBLE
        binding.bullet19.visibility = android.view.View.VISIBLE
        binding.text19.text = langkahKeduaPartEmpat
        binding.text19.visibility = android.view.View.VISIBLE
        binding.bullet20.visibility = android.view.View.GONE
        binding.text20.visibility = android.view.View.GONE
        binding.bullet21.visibility = android.view.View.GONE
        binding.text21.visibility = android.view.View.GONE
        binding.bullet22.visibility = android.view.View.GONE
        binding.text22.visibility = android.view.View.GONE
        binding.bullet23.visibility = android.view.View.GONE
        binding.text23.visibility = android.view.View.GONE
        binding.bullet24.visibility = android.view.View.GONE
        binding.text24.visibility = android.view.View.GONE
        binding.judulLangkahKetiga.text = judulLangkahKetiga
        binding.judulLangkahKetiga.visibility = android.view.View.VISIBLE
        binding.bullet15.visibility = android.view.View.VISIBLE
        binding.text15.text = langkahKetigaPartSatu
        binding.text15.visibility = android.view.View.VISIBLE
        binding.bullet16.visibility = android.view.View.VISIBLE
        binding.text16.text = langkahKetigaPartDua
        binding.text16.visibility = android.view.View.VISIBLE
        binding.bullet17.visibility = android.view.View.VISIBLE
        binding.text17.text = langkahKetigaPartTiga
        binding.text17.visibility = android.view.View.VISIBLE
        binding.bullet18.visibility = android.view.View.GONE
        binding.text18.visibility = android.view.View.GONE
        binding.bullet25.visibility = android.view.View.GONE
        binding.text25.visibility = android.view.View.GONE
    }

    private fun showB3Detail() {
        val bingoTipeSampah = """BinGo! Sampah kamu termasuk tipe sampah B3!"""
        val detailSampah = """Apa Itu? Sampah B3 atau Bahan Berbahaya dan Beracun adalah sampah yang mengandung zat berbahaya dan beracun yang dapat mencemari lingkungan dan membahayakan kesehatan manusia. Contohnya seperti baterai, cat bekas, lampu neon, dan obat-obatan.\n Eits, hati-hati! jangan dibuang sembarangan! Pengelolaan sampah B3 harus dilakukan dengan cara khusus agar tidak membahayakan lingkungan dan manusia. Yuk, ikuti langkah bijak BinGo untuk mengolahnya!"""
        val judulLangkahPertama ="""Langkah 1: Kenali Sampahmu!"""
        val langkahPertama = """Pastikan kamu mengetahui jenis dan kategori sampah B3 yang kamu miliki. Jangan mencampur sampah B3 dengan jenis sampah lainnya. Pahami bahaya dan risiko yang ditimbulkan oleh sampah B3 tersebut."""
        val judulLangkahKedua ="""Langkah 2: Pilih Opsi Terbaik!"""
        val langkahKeduaPartSatu="""Hubungi perusahaan pengelola sampah B3 yang memiliki izin resmi dari pemerintah."""
        val langkahKeduaPartDua="""Pastikan perusahaan tersebut memiliki kualifikasi dan pengalaman yang memadai dalam menangani sampah B3."""
        val langkahKeduaPartTiga="""Ikuti prosedur dan ketentuan yang ditetapkan oleh perusahaan pengelola sampah B3."""
        val langkahKeduaPartEmpat="""Siapkan tempat penyimpanan khusus untuk sampah B3 yang aman dan kedap udara."""
        val judulLangkahKetiga ="""Langkah 3: Jaga Bumi Kita!"""
        val langkahKetigaPartSatu="""Hindari penggunaan bahan-bahan berbahaya dan beracun yang menghasilkan sampah B3."""
        val langkahKetigaPartDua="""Jika terpaksa menggunakan bahan B3, pilihlah produk yang ramah lingkungan dan mudah didaur ulang."""
        val langkahKetigaPartTiga="""Gunakan kembali (reuse) dan daur ulang (recycle) sampah B3 yang memungkinkan."""
        val langkahKetigaPartEmpat="""Tingkatkan pengetahuan dan pemahaman tentang bahaya dan pengelolaan sampah B3."""

        binding.bingoTipeSampah.text = bingoTipeSampah
        binding.bingoTipeSampah.visibility = android.view.View.VISIBLE
        binding.detailSampah.text = detailSampah
        binding.detailSampah.visibility = android.view.View.VISIBLE
        binding.judulLangkahPertama.text = judulLangkahPertama
        binding.judulLangkahPertama.visibility = android.view.View.VISIBLE
        binding.langkahPertama.text = langkahPertama
        binding.langkahPertama.visibility = android.view.View.VISIBLE
        binding.judulLangkahKedua.text = judulLangkahKedua
        binding.judulLangkahKedua.visibility = android.view.View.VISIBLE
        binding.bullet12.visibility = android.view.View.VISIBLE
        binding.text12.text = langkahKeduaPartSatu
        binding.text12.visibility = android.view.View.VISIBLE
        binding.bullet13.visibility = android.view.View.VISIBLE
        binding.text13.text = langkahKeduaPartDua
        binding.text13.visibility = android.view.View.VISIBLE
        binding.bullet14.visibility = android.view.View.VISIBLE
        binding.text14.text = langkahKeduaPartTiga
        binding.text14.visibility = android.view.View.VISIBLE
        binding.bullet19.visibility = android.view.View.VISIBLE
        binding.text19.text = langkahKeduaPartEmpat
        binding.text19.visibility = android.view.View.VISIBLE
        binding.bullet20.visibility = android.view.View.VISIBLE
        binding.text20.visibility = android.view.View.VISIBLE
        binding.bullet21.visibility = android.view.View.VISIBLE
        binding.text21.visibility = android.view.View.VISIBLE
        binding.bullet22.visibility = android.view.View.VISIBLE
        binding.text22.visibility = android.view.View.VISIBLE
        binding.bullet23.visibility = android.view.View.VISIBLE
        binding.text23.visibility = android.view.View.VISIBLE
        binding.bullet24.visibility = android.view.View.VISIBLE
        binding.text24.visibility = android.view.View.VISIBLE
        binding.judulLangkahKetiga.text = judulLangkahKetiga
        binding.judulLangkahKetiga.visibility = android.view.View.VISIBLE
        binding.bullet15.visibility = android.view.View.VISIBLE
        binding.text15.text = langkahKetigaPartSatu
        binding.text15.visibility = android.view.View.VISIBLE
        binding.bullet16.visibility = android.view.View.VISIBLE
        binding.text16.text = langkahKetigaPartDua
        binding.text16.visibility = android.view.View.VISIBLE
        binding.bullet17.visibility = android.view.View.VISIBLE
        binding.text17.text = langkahKetigaPartTiga
        binding.text17.visibility = android.view.View.VISIBLE
        binding.bullet18.visibility = android.view.View.VISIBLE
        binding.text16.text = langkahKetigaPartEmpat
        binding.text18.visibility = android.view.View.VISIBLE
        binding.bullet25.visibility = android.view.View.VISIBLE
        binding.text25.visibility = android.view.View.VISIBLE
    }
}