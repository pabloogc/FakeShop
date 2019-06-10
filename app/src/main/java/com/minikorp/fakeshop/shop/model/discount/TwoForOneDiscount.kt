package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.ProductCode
import com.minikorp.fakeshop.shop.model.cart.CartProduct

/**
 * Two for one discount,
 * can be made more generic over what products is it applied,
 * just modify target to be a custom function / filter.
 *
 * @param targetProduct The product that can receive the discount.
 */
class TwoForOneDiscount(val targetProduct: ProductCode) : ApplicableDiscount {

    override val code: DiscountCode = DiscountCode("TwoForOne($targetProduct)")

    override fun apply(products: List<CartProduct>): List<CartProduct> {
        //Find products index that that match target product
        //And don't have any other discount applied
        //(this logic can be as complex as needed)

        //Work with index to keep original ordering
        //without doing functional spaghetti
        val out = ArrayList(products)
        products
            .mapIndexedNotNull { index, product ->
                index.takeIf {
                    product.product.code == targetProduct && product.discounts.isEmpty()
                }
            }
            .windowed(size = 2, step = 2) //Take by pairs
            .forEach { (firstIdx, secondIdx) ->
                //Keep as is
                //Add discount to make price 0 to second one
                val first = out[firstIdx]
                val second = out[secondIdx]
                out[firstIdx] = first.copy(
                    discounts = first.discounts
                            + Discount(code, Price(0))
                )
                out[secondIdx] = first.copy(
                    discounts = second.discounts
                            + Discount(code, Price(-second.product.price.value))
                )
            }

        return out
    }

}