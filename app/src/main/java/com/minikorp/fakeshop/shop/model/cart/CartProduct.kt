package com.minikorp.fakeshop.shop.model.cart

import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.discount.Discount

/**
 * A product inside a cart, with the discounts that have been applied.
 *
 * @param id unique id, to ensure products inside a
 * cart are unique even if same product and discounts are applied.
 * @param product actual product.
 * @param discounts discounts applied to the product.
 *
 */
data class CartProduct constructor(
    val id: Int,
    val product: Product,
    val discounts: List<Discount> = emptyList()
) {

    /**
     * The final price after discounts.
     */
    val discountedPrice: Price = Price(
        product.price.value + discounts.sumBy { it.price.value }
    )
}