package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.ProductCode
import com.minikorp.fakeshop.shop.model.cart.Cart

/**
 * Apply bulk discount for a product after count threshold.
 *
 * @param targetProduct The product that can receive the discount.
 * @param priceDiscount The discount to apply
 * @param minQuantity Minimum amount of products before discount can be applied.
 *
 * @see [TwoForOneDiscount] for more rationale.
 */
class BulkDiscount(
    val targetProduct: ProductCode,
    val priceDiscount: Price,
    val minQuantity: Int
) : ApplicableDiscount {

    override val priority: Int = 100
    override val code: DiscountCode = DiscountCode("Bulk($targetProduct)")

    override fun apply(cart: Cart): Cart {
        val out = ArrayList(cart.products)
        //Work with index to keep original ordering
        //without doing functional spaghetti
        cart.products
            .mapIndexedNotNull { index, product ->
                index.takeIf {
                    product.product.code == targetProduct && product.discounts.isEmpty()
                }
            }
            .takeIf { it.size >= minQuantity }
            ?.forEach { idx ->
                val item = out[idx]
                out[idx] = item.copy(
                    discounts = item.discounts + Discount(
                        code, priceDiscount
                    )
                )
            }

        return Cart(products = out)
    }

}