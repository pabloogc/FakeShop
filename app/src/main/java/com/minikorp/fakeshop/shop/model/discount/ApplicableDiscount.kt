package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.cart.Cart

interface ApplicableDiscount {
    val priority: Int
    val code: DiscountCode
    fun apply(cart: Cart): Cart
}