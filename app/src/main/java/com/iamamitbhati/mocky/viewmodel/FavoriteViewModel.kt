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
class FavoriteViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    val stateChange: MutableLiveData<List<ProductDetail>> = MutableLiveData()
    fun getFavProducts() {
        viewModelScope.launch {
            productRepository.getAllFavorite().collect {
                stateChange.postValue(it)
            }
        }
    }

    fun setFavoriteUnFavorite(id: Long, fav: Boolean) {
        viewModelScope.launch {
            productRepository.setFavoriteUnFavorite(id, fav)
        }
    }
}