package com.minikorp.fakeshop.app.cart

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minikorp.fakeshop.shop.data.ShopRepository
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.cart.Cart
import com.minikorp.fakeshop.shop.model.cart.CartProduct

class CartViewModel(shopRepository: ShopRepository) : ViewModel() {

    val cart = MutableLiveData<Cart>(
        Cart.create(
            //Maybe some persistence / network call here
            //To bring any active cart from website / iOS
            products = emptyList(),
            discounts = shopRepository.getActiveDiscounts().getOrThrow().discounts
        )
    )

    /**
     * Add a product to the cart.
     */
    @MainThread
    fun addProduct(product: Product) {
        val currentCart = cart.value!! //Can't be null
        cart.postValue(currentCart.addProducts(product))
    }

    /**
     * Remove a cartProduct from the cart.
     */
    @MainThread
    fun removeProduct(cartProduct: CartProduct) {
        val currentCart = cart.value!! //Can't be null
        cart.postValue(currentCart.removeProducts(cartProduct))
    }
}