package com.minikorp.fakeshop.app.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minikorp.fakeshop.shop.data.ShopRepository
import com.minikorp.fakeshop.shop.model.ProductList
import kotlinx.coroutines.launch

class ProductListViewModel(private val shopRepository: ShopRepository) : ViewModel() {
    val products = MutableLiveData<Result<ProductList>>()
    val productsLoading = MutableLiveData<Boolean>(false)

    fun fetchProducts() {
        if (productsLoading.value == true) {
            return //Already loading
        }
        productsLoading.postValue(true)
        viewModelScope.launch {
            val loadedProducts = shopRepository.getProducts()
            products.postValue(loadedProducts)
            productsLoading.postValue(false)
        }
    }
}