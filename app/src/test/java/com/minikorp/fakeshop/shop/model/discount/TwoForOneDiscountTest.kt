package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.TestData
import com.minikorp.fakeshop.shop.model.cart.Cart
import com.minikorp.fakeshop.shop.model.cart.CartProduct
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

/**
 * Sample tests for discount.
 */
class TwoForOneDiscountTest {

    @Test
    fun `items have discount`() {
        val cart = Cart(
            listOf(
                CartProduct(product = TestData.sampleProduct2),
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct3),
                CartProduct(product = TestData.sampleProduct1)
            )
        )
        val discount = TwoForOneDiscount(TestData.sampleProduct1.code)
        val discountedCart = discount.apply(cart)

        expect {
            that(discountedCart.products) {
                hasSize(cart.products.size)
                get(1).get {
                    expectThat(discounts.first().price.value) {
                        isEqualTo(0)
                    }
                }
                get(3).get {
                    expectThat(discounts.first().price.value) {
                        isEqualTo(-TestData.sampleProduct1.price.value)
                    }
                }
            }
        }

        expectThat(discountedCart.totalPrice().value) {
            //One is discounted
            isEqualTo(
                TestData.sampleProduct1.price.value
                        + TestData.sampleProduct2.price.value
                        + TestData.sampleProduct3.price.value
            )
        }
    }

    @Test
    fun `discounts are only applied once`() {
        val cart = Cart(
            listOf(
                CartProduct(product = TestData.sampleProduct2),
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct3),
                CartProduct(product = TestData.sampleProduct1)
            )
        )
        val discount = TwoForOneDiscount(TestData.sampleProduct1.code)
        //Apply two times
        val discountedCart = discount.apply(discount.apply(cart))
        expectThat(discountedCart.totalPrice().value) {
            //One is discounted
            isEqualTo(
                TestData.sampleProduct1.price.value
                        + TestData.sampleProduct2.price.value
                        + TestData.sampleProduct3.price.value
            )
        }
    }

    @Test
    fun `discounts_applied_in_pairs`() {
        val cart = Cart(
            listOf(
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct3),
                CartProduct(product = TestData.sampleProduct1)
            )
        )
        val discount = TwoForOneDiscount(TestData.sampleProduct1.code)
        val discountedCart = discount.apply(cart)
        expectThat(discountedCart.totalPrice().value) {
            //One is discounted, other is same
            isEqualTo(
                TestData.sampleProduct1.price.value
                        + TestData.sampleProduct1.price.value
                        + TestData.sampleProduct3.price.value
            )
        }
    }

    @Test
    fun `order is preserved`() {
        val cart = Cart(
            listOf(
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct1),
                CartProduct(product = TestData.sampleProduct3),
                CartProduct(product = TestData.sampleProduct1)
            )
        )
        val discount = TwoForOneDiscount(TestData.sampleProduct1.code)
        val discountedCart = discount.apply(cart)
        expectThat(discountedCart.products.map { it.product }) {
            isEqualTo(cart.products.map { it.product })
        }
    }

    //TODO: Add more tests for other cases
}