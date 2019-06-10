package com.minikorp.fakeshop.app.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minikorp.fakeshop.shop.data.ShopRepository
import com.minikorp.fakeshop.shop.model.ProductList
import com.minikorp.fakeshop.shop.model.Resource
import kotlinx.coroutines.launch

class ProductListViewModel(private val shopRepository: ShopRepository) : ViewModel() {
    val products = MutableLiveData<Resource<ProductList>>()

    fun fetchProducts() {
        if (products is Resource.Loading<*>) {
            return //Already loading, nothing to do
        }
        products.postValue(Resource.Loading())
        viewModelScope.launch {
            val loadedProducts = shopRepository.getProducts()
            //This can be improved with proper mapping of exception to user message
            //with something like errorHandler.localizeException(result, ...)
            products.postValue(
                when {
                    loadedProducts.isSuccess -> Resource.Success(loadedProducts.getOrThrow())
                    else -> Resource.Error(loadedProducts.exceptionOrNull()?.message)
                }
            )
        }
    }
}