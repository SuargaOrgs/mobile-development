package com.dicoding.suargaapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.FragmentHomeBinding
import com.dicoding.suargaapp.databinding.FragmentProfileBinding
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.ui.home.HomeViewModel
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()

    }

    private fun setupAction() {

//        viewModel.getSession().observe(viewLifecycleOwner) { user ->
//            if (user.hasCompletedAssessment) {
//                binding.tvAssessment.text = "sudah mengisi asesmen"
//            } else {
//                binding.tvAssessment.text = "belum mengisi asesmen"
//            }
//        }

        viewModel.hasCompletedAssessment.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvAssessment.text = "sudah mengisi asesmen"
            } else {
                binding.tvAssessment.text = "belum mengisi asesmen"
            }
        }

        binding.btnAssessment.setOnClickListener {
            val intent = Intent(requireContext(), AsesmenActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}