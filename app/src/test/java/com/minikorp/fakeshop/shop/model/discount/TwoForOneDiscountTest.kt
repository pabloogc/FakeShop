package com.minikorp.fakeshop.shop.model.discount

import com.minikor.fakeshop.testdata.TestData
import com.minikorp.fakeshop.shop.model.cart.Cart
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.isEqualTo

/**
 * Sample tests for discount.
 */
class TwoForOneDiscountTest {

    @Test
    fun `items have discount`() {
        val cart = Cart.create(
            products = listOf(
                TestData.sampleProduct2,
                TestData.sampleProduct1,
                TestData.sampleProduct3,
                TestData.sampleProduct1
            ),
            discounts = listOf(TwoForOneDiscount(TestData.sampleProduct1.code))
        )

        expect {
            that(cart.discountedProducts) {
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

        expectThat(cart.totalPrice()) {
            //One is discounted
            isEqualTo(
                TestData.sampleProduct1.price
                        + TestData.sampleProduct2.price
                        + TestData.sampleProduct3.price
            )
        }
    }

    @Test
    fun `discounts are only applied once`() {
        val cart = Cart.create(
            products = listOf(
                TestData.sampleProduct2,
                TestData.sampleProduct1,
                TestData.sampleProduct3,
                TestData.sampleProduct1
            ),
            discounts = listOf(TwoForOneDiscount(TestData.sampleProduct1.code))
        )
        expectThat(cart.totalPrice()) {
            //One is discounted
            isEqualTo(
                TestData.sampleProduct1.price
                        + TestData.sampleProduct2.price
                        + TestData.sampleProduct3.price
            )
        }
    }

    @Test
    fun `discounts applied in pairs`() {
        val cart = Cart.create(
            products = listOf(
                TestData.sampleProduct1,
                TestData.sampleProduct1,
                TestData.sampleProduct3,
                TestData.sampleProduct1
            ),
            discounts = listOf(TwoForOneDiscount(TestData.sampleProduct1.code))
        )

        expectThat(cart.totalPrice()) {
            //One is discounted, other is same
            isEqualTo(
                TestData.sampleProduct1.price
                        + TestData.sampleProduct1.price
                        + TestData.sampleProduct3.price
            )
        }
    }

    @Test
    fun `order is preserved when discounting`() {
        val products = listOf(
            TestData.sampleProduct1,
            TestData.sampleProduct1,
            TestData.sampleProduct3,
            TestData.sampleProduct1
        )
        val cart = Cart.create(
            products,
            discounts = listOf(TwoForOneDiscount(TestData.sampleProduct1.code))
        )
        expectThat(cart.discountedProducts.map { it.product }) {
            isEqualTo(products)
        }
    }
}