package com.example.garapmaneh.ui.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        val itemImageUsername = root.findViewById<TextView>(R.id.username)
        val itemImageEmail = root.findViewById<TextView>(R.id.email)
        val itemImageTitle = root.findViewById<TextView>(R.id.title)

        val userName = itemImageUsername.text.toString()
        val userEmail = itemImageEmail.text.toString()
        val userChar = itemImageTitle.text.toString()

        binding.apply {
            itemImageUsername.text = userName
            itemImageEmail.text = userEmail
            itemImageTitle.text = userChar
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

