package com.example.capstonefix.ui.Dashboard.dashboard.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.capstonefix.databinding.FragmentProfileBinding
import com.example.capstonefix.repository.Preference
import com.example.capstonefix.ui.Login.LoginActivity
import com.example.capstonefix.ui.Profile.EditProfileActivity
import androidx.core.util.Pair
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
