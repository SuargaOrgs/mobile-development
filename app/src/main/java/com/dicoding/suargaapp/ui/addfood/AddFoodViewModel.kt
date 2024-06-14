package com.dicoding.suargaapp.ui.addfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.data.remote.response.Food
import com.dicoding.suargaapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class AddFoodViewModel(private val repository: UserRepository) : ViewModel() {

    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>> get() = _foodList

    init {
        fetchFoodList()
    }

    private fun fetchFoodList() {
        viewModelScope.launch {
            try {
                val response = repository.listFood()
                _foodList.value = response.data ?: emptyList()
            } catch (e: Exception) {
                _foodList.value = emptyList()
            }
        }
    }
}