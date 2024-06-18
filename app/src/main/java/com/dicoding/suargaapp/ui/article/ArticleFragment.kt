package com.dicoding.suargaapp.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.adapter.ArticleAdapter
import com.dicoding.suargaapp.adapter.ViewPagerAdapter
import com.dicoding.suargaapp.data.local.Article
import com.dicoding.suargaapp.databinding.FragmentArticleBinding
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.ui.profile.ProfileFragment
import com.dicoding.suargaapp.viewmodelfactory.ArticleViewModelFactory
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private val articleViewModel by viewModels<ArticleViewModel> {
        ArticleViewModelFactory.getInstance(requireActivity().application)
    }
    private val mainViewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        observeViewModel()

        viewPager = binding.viewPager
        recyclerView = binding.recyclerView

        val hotArticleList = listOf(
            Article("Pentingnya Nutrisi untuk Mencegah Stunting",
                "Stunting adalah kondisi di mana tinggi badan anak lebih rendah dari standar usianya akibat kekurangan gizi kronis. Nutrisi yang tepat sangat penting untuk mencegah stunting dan memastikan pertumbuhan optimal anak.",
                "https://rsudmangusada.badungkab.go.id/uploads/promosi/STUNTING_530430.png"
            ),
            Article( "Dampak Jangka Panjang Stunting pada Anak",
                "Stunting tidak hanya mempengaruhi tinggi badan anak, tetapi juga dapat berdampak pada perkembangan kognitif dan kesehatan secara keseluruhan. Anak-anak yang mengalami stunting berisiko memiliki kemampuan belajar yang lebih rendah",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBSNYPwjkQT2vxz-kjjFwjqXOHaqRMQwzxguDqFTb0EIEkqAIWi3ad-tJI26HW"
            ),
            Article( "Peran Orang Tua dalam Mencegah Stunting",
                "Orang tua memainkan peran kunci dalam mencegah stunting dengan memberikan makanan bergizi, memantau pertumbuhan anak secara teratur, dan memastikan anak mendapatkan imunisasi yang lengkap.",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRhY3oSKbcMYhRUGr65a8KV3lYLKzSEEjC_ImtRdB1jv3lEzW0D-FHhFcVOTcVO"
            )
        )

        val articleList = listOf(
            Article("Upaya Pemerintah dalam Mengatasi Stunting",
                "Pemerintah Indonesia telah meluncurkan berbagai program untuk mengatasi stunting, termasuk pemberian makanan tambahan, edukasi gizi, dan peningkatan akses ke pelayanan kesehatan. Program-program ini bertujuan untuk menurunkan prevalensi stunting secara signifikan.",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSiaORMNGYnxKv6QcNaSv0k0yo8rxoSVZjSmhgy3i0XTEpmM5wlUSup8CxaIgRd"
            ),
            Article("Pola Makan Seimbang untuk Anak-Anak",
                "Memberikan pola makan seimbang yang mencakup karbohidrat, protein, lemak sehat, serta vitamin dan mineral penting dapat membantu mencegah stunting. Pengenalan makanan sehat sejak dini sangat penting untuk pertumbuhan anak.",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQhzMSJ57M8DWMxGxnA2TqLTATU9EKlxsOl_56ssB-SsLxUNoBziuOWnABm9IUs"
            ),
            Article("Mitos dan Fakta tentang Stunting",
                "Banyak mitos yang beredar tentang stunting, seperti bahwa stunting hanya disebabkan oleh genetik. Faktanya, stunting lebih sering disebabkan oleh kekurangan gizi kronis dan bisa dicegah dengan intervensi yang tepat.",
                "https://p2ptm.kemkes.go.id/uploads//VHcrbkVobjRzUDN3UCs4eUJ0dVBndz09/1_dari_3_balita_derita_stunting.jpg"
            ),
            Article("Mengapa 1000 Hari Pertama Kehidupan Sangat Penting",
                "Periode 1000 hari pertama kehidupan, mulai dari kehamilan hingga usia dua tahun, adalah masa kritis untuk mencegah stunting. Nutrisi yang baik selama periode ini sangat penting untuk pertumbuhan dan perkembangan optimal anak.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRP0GgbmQJLj75NEn7CbUktLDrRoXWhnbCkWef8w7RdsaRFEC-x-F9Lfe_YuTXN"
            ),
            Article("Stunting di Indonesia: Fakta dan Angka",
                "Indonesia menghadapi tantangan besar dengan tingginya angka stunting. Data terbaru menunjukkan bahwa sekitar 30% anak-anak di bawah usia lima tahun mengalami stunting. Upaya kolaboratif diperlukan untuk mengatasi masalah ini.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4-rlE7_WIeVDzBTu0YQZGGARbxnZfOIFmqvUD8mT_EfnBeyaNC_4hygbHXrqt"
            ),
            Article("Peran Edukasi dalam Pencegahan Stunting",
                "Edukasi gizi dan kesehatan bagi ibu hamil dan orang tua sangat penting dalam pencegahan stunting. Informasi yang tepat tentang pemberian makan dan perawatan anak dapat membantu mengurangi risiko stunting.",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR8dxer9ItiG8YwQ4zD7Z7I85IGipwFSUCqNTOh6Q9kwXtdPGg8G3d5YSX2z_bm"
            ),
            Article("Hubungan Antara Sanitasi dan Stunting",
                "Sanitasi yang buruk dapat berkontribusi pada stunting karena meningkatkan risiko infeksi dan penyakit yang mengganggu penyerapan nutrisi. Akses ke sanitasi yang baik adalah bagian penting dari strategi pencegahan stunting.",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRlfFquGKL_zhpgkRaUncCC5wD5M9OFlTUjVQn7g_FlgZei76GGWgFe_n3GzvSf"
            )
        )

        articleViewModel.insert(hotArticleList)
        articleViewModel.insert(articleList)

        val viewPagerAdapter = ViewPagerAdapter(requireContext(), hotArticleList)
        viewPager.adapter = viewPagerAdapter

        showArticleList()
    }

    private fun showArticleList() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val adapter = ArticleAdapter()
        recyclerView.adapter = adapter

        articleViewModel.getAllArticles().observe(viewLifecycleOwner) { articleList ->
            adapter.submitList(articleList)
        }
    }

    private fun setupAction(){
        binding.avatar.setOnClickListener {
            val profileFragment = ProfileFragment()

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, profileFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeViewModel() {
        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"
            val firstLetter = user.name.firstOrNull()?.toString() ?: ""

            binding.apply {
                tvGreeting.text = greetings
                avatar.text = firstLetter
            }
        }
    }
}
