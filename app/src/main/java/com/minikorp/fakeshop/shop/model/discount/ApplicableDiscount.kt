package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.cart.CartProduct

/**
 * Common interface for discounts that can be applied over a list of products.
 *
 * @property code a unique code for the discount.
 */
interface ApplicableDiscount {
    val code: DiscountCode

    /**
     * Apply the discount to the list of products.
     * Order of must be maintained.
     *
     * If it's not applicable same list may be returned.
     *
     * @return the products, with discounts applied, in same order.
     */
    fun apply(products: List<CartProduct>): List<CartProduct>
}