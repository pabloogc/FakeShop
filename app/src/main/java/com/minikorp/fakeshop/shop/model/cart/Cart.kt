package com.minikorp.fakeshop.shop.model.cart

import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.discount.ApplicableDiscount
import java.util.concurrent.atomic.AtomicInteger


/**
 * Cart of selected products, and the discount to apply to them.
 */
class Cart private constructor(
    val discounts: List<ApplicableDiscount> = emptyList(),
    val discountedProducts: List<CartProduct> = emptyList()
) {

    companion object {
        private val counter = AtomicInteger()

        fun create(
            products: List<Product> = emptyList(),
            discounts: List<ApplicableDiscount> = emptyList()
        ): Cart {
            return Cart(discounts = discounts)
                .addProducts(*products.toTypedArray())
        }
    }

    /**
     * Add a new product(s) to the cart and return it.
     *
     * @return The new cart, with recalculated discounts
     */
    fun addProducts(vararg products: Product): Cart {
        //Add the product, and recompute discounted product list
        //maintaining ID's
        val toDiscount = this.discountedProducts
            .plus(products.map { newProduct ->
                CartProduct(
                    id = counter.getAndIncrement(),
                    product = newProduct
                )
            })
        val discounted = recomputeDiscounts(toDiscount)

        return Cart(
            discounts = this.discounts,
            discountedProducts = discounted
        )
    }

    /**
     * Remove product(s) from the cart and return it.
     *
     * @return The new cart, with recalculated discounts
     */
    fun removeProducts(vararg cartProducts: CartProduct): Cart {
        val toDiscount = this.discountedProducts.toMutableList()
        val removed = toDiscount.removeAll(cartProducts)
        require(removed) {
            "Trying to remove item that is not in cart"
        }
        val discounted = recomputeDiscounts(toDiscount)

        return Cart(
            discounts = this.discounts,
            discountedProducts = discounted
        )
    }

    fun totalPrice(): Price {
        val sum = discountedProducts.sumBy { it.discountedPrice.value }
        return Price(sum)
    }

    /**
     * Clear current discounts and apply again.
     */
    private fun recomputeDiscounts(toDiscount: List<CartProduct>): List<CartProduct> {
        //This can be done with a simple for loop
        //but since we are in a functional style let's keep rolling.

        //*Note* I would _never_ write this code in a production app.
        //It's terrible to read

        //Clear current discounts, and apply again
        return toDiscount
            .map { CartProduct(id = it.id, product = it.product, discounts = emptyList()) }
            .let { productsToDiscount ->
                discounts.fold(productsToDiscount) { acc, discount ->
                    discount.apply(acc)
                }
            }
    }
}