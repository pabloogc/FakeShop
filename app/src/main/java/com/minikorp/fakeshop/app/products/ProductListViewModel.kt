package com.minikorp.fakeshop.app.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minikorp.fakeshop.shop.data.ShopRepository
import com.minikorp.fakeshop.shop.model.ProductList
import com.minikorp.fakeshop.shop.model.Resource
import kotlinx.coroutines.launch

/**
 * View model for [ProductListFragment] mainly.
 */
class ProductListViewModel(private val shopRepository: ShopRepository) : ViewModel() {
    /**
     * The products you can buy.
     */
    val products = MutableLiveData<Resource<ProductList>>()

    /**
     * Fetch products
     *
     * Add as many layers as needed here, Interactor -> Controller -> Repository.
     *
     * Not included since I am not testing those layers in isolation, I prefer to
     * this simple. Real application would most likely require *at least* another abstraction
     * layer before network / database call.
     */
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