package com.iamamitbhati.mocky.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    val stateChange: MutableLiveData<ProductDetail> = MutableLiveData()
    fun getProductDetail(id: Long) {
        viewModelScope.launch {
            productRepository.getProductDetail(id).collect {
                stateChange.postValue(it)
            }
        }
    }

    fun setUnFavorite(id: Long?, isFav: Boolean) {
        viewModelScope.launch {
            id?.let {
                productRepository.setFavoriteUnFavorite(id, isFav)
            }
        }

    }
}