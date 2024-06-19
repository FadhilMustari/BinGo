package com.example.capstonefix.ui.Dashboard.dashboard.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.example.capstonefix.databinding.FragmentProfileBinding
import com.example.capstonefix.repository.Preference
import com.example.capstonefix.ui.Login.LoginActivity
import com.example.capstonefix.ui.Profile.EditProfileActivity
import androidx.core.util.Pair
import com.example.capstonefix.R

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val username = it.getString("username")
            val email = it.getString("email")

            binding.editProfile.setOnClickListener{
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        view.context as Activity,
                        Pair(binding.gambarProfile, "gambar"),
                        Pair(binding.judulProfile, "judul"),
                        Pair(binding.linearLayout,"input"),
                        Pair(binding.btnLogout,"button")
                    )

                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                intent.putExtra("username",username)
                intent.putExtra("email",email)

                startActivity(intent,optionsCompat.toBundle())
            }

            binding.namaProfile.text = ": $username"
            binding.emailProfile.text = ": $email"

        }

        binding.btnLogout.setOnClickListener{
            Preference.logOut(requireContext())
            val intent = Intent(requireContext(), LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
        }

        val info = Preference.getInfo(requireContext())
        val point = info.third

        binding.infoPoint.text = "Point : $point"
        // menggunakan ViewTreeObserver untuk mendapatkan lebar parent dari lineBar
        binding.lineBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //agar tidak dipanggil lagi setiap kali layout berubah
                binding.lineBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val parentWidth = binding.lineBar.width

                val maxPoint = 500
                val percentage = (point!!.toFloat() / maxPoint.toFloat()) * 100
                if(percentage <= 100){
                    val layoutParams = binding.lineBar.layoutParams
                    layoutParams.width = (parentWidth * (percentage / 100)).toInt()
                    binding.lineBar.layoutParams = layoutParams
                }else{
                    val layoutParams = binding.lineBar.layoutParams
                    layoutParams.width = parentWidth
                    binding.lineBar.layoutParams = layoutParams
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
