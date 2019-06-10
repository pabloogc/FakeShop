package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.ProductCode
import com.minikorp.fakeshop.shop.model.cart.CartProduct

/**
 * Apply bulk discount for a product after count threshold.
 *
 * @param targetProduct The product that can receive the discount.
 * @param priceDiscount The discount to apply to base price (relative value).
 * @param minQuantity Minimum amount of products before discount can be applied.
 *
 * @see [TwoForOneDiscount] for more rationale.
 */
class BulkDiscount(
    val targetProduct: ProductCode,
    val priceDiscount: Price,
    val minQuantity: Int
) : ApplicableDiscount {

    override val code: DiscountCode = DiscountCode("Bulk($targetProduct)")

    //TODO: Test this similar to TwoForOne
    override fun apply(products: List<CartProduct>): List<CartProduct> {
        val out = ArrayList(products)
        //Work with index to keep original ordering
        //without doing functional spaghetti
        products
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

        return out
    }

}