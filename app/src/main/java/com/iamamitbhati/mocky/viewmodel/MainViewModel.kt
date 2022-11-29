package com.iamamitbhati.mocky.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.repository.ProductRepository
import com.iamamitbhati.mocky.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    val stateChange: MutableLiveData<Resource<List<ProductDetail>>> = MutableLiveData()
    fun getProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts().collect {
                stateChange.postValue(it)
            }
        }
    }

    fun setFavoriteUnFavorite(id: Long, isFav: Boolean) {
        viewModelScope.launch {
            productRepository.setFavoriteUnFavorite(id,isFav)
        }

    }
}