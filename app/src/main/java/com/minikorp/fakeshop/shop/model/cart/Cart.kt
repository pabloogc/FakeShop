package com.minikorp.fakeshop.shop.model.cart

import com.minikorp.fakeshop.shop.model.Price


data class Cart(
    val products: List<CartProduct> = emptyList()
) {
    fun totalPrice(): Price {
        val sum = products.sumBy {
            it.product.price.value +
                    it.discounts.sumBy { discount -> discount.price.value }
        }
        return Price(sum)
    }
}