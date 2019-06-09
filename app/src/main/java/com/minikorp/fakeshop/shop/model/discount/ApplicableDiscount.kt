package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.cart.CartProduct

interface ApplicableDiscount {
    val priority: Int
    val code: DiscountCode
    fun apply(products: List<CartProduct>): List<CartProduct>
}