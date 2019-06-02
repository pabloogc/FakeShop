package com.minikorp.fakeshop.shop.model.cart

import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.discount.Discount
import com.minikorp.fakeshop.shop.model.discount.DiscountCode

/**
 * A product inside a cart, with discounts applied.
 *
 * @param product actual product.
 * @param discounts discounts applied to the product.
 *
 */
data class CartProduct(
    val product: Product,
    val discounts: List<Discount> = emptyList()
) {

    /**
     * Check if a discount with specific code has already been applied.
     */
    fun hasDiscount(code: DiscountCode): Boolean {
        return discounts.find { it.code == code } != null
    }
}