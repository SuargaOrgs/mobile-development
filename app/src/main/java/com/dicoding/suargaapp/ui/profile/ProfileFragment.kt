package com.dicoding.suargaapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dicoding.suargaapp.databinding.FragmentProfileBinding
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.ui.detailprofile.DetailProfileActivity
import com.dicoding.suargaapp.ui.editpassword.EditPasswordActivity
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.ui.premium.PremiumActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import java.util.Locale

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        observeViewModel()
    }

    private fun setupAction() {

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.premiumButton.setOnClickListener {
            val intent = Intent(requireContext(), PremiumActivity::class.java)
            startActivity(intent)
        }
        binding.profileButton.setOnClickListener {
            val intent = Intent(requireContext(), DetailProfileActivity::class.java)
            startActivity(intent)
        }
        binding.changePassButton.setOnClickListener {
            val intent = Intent(requireContext(), EditPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.assesmentButton.setOnClickListener {
            val intent = Intent(requireContext(), AsesmenActivity::class.java)
            startActivity(intent)
        }
        binding.relativeLayout.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
        }
        binding.relativeLayout3.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"

            val firstLetter = user.name.firstOrNull()?.toString() ?: ""

            binding.apply {
                tvGreeting.text = greetings
                userName.text = user.name
                userEmail.text = user.email
                profileIcon.text = firstLetter.uppercase()
            }
        }
    }
}
