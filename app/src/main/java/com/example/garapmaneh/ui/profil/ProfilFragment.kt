package com.example.garapmaneh.ui.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.garapmaneh.LoginActivity
import com.example.garapmaneh.R
import com.example.garapmaneh.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val preferences = this.requireActivity()
            .getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val userName = preferences.getString("userName", null)
        val userEmail = preferences.getString("userEmail", null)
        val userChar = userName?.first()

        // Mengakses elemen-elemen dalam item_image layout
        val itemImageLayout = root.findViewById<View>(R.id.item_image_layout)
        val titleTextView = itemImageLayout.findViewById<TextView>(R.id.title)
        val usernameTextView = itemImageLayout.findViewById<TextView>(R.id.username)
        val emailTextView = itemImageLayout.findViewById<TextView>(R.id.email)

        // Mengatur teks untuk elemen-elemen tersebut
        titleTextView.text = userChar.toString()
        usernameTextView.text = userName
        emailTextView.text = userEmail

        val logoutTextView = binding.keluar
        logoutTextView.setOnClickListener {
            context?.getSharedPreferences("UserData", 0)?.edit()?.clear()?.commit();
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

