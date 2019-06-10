package com.minikorp.fakeshop.shop.model.cart

import com.minikor.fakeshop.testdata.TestData
import org.junit.Test
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class CartTest {

    @Test
    fun `adding product keeps order`() {
        val cart = Cart.create()
            .addProducts(TestData.sampleProduct1)
            .addProducts(TestData.sampleProduct2)
        expect {
            that(cart.discountedProducts) {
                hasSize(2)
            }
            that(cart.discountedProducts.map { it.product }) {
                isEqualTo(listOf(TestData.sampleProduct1, TestData.sampleProduct2))
            }
        }
    }

    @Test
    fun `ids are maintained`() {
        val cart = Cart.create()
            .addProducts(TestData.sampleProduct1)
            .addProducts(TestData.sampleProduct2)
            .addProducts(TestData.sampleProduct3)

        val originalIds = cart.discountedProducts.map { it.id }
        val afterAddCart = cart.addProducts(TestData.sampleProduct1)
        val afterAddIds = afterAddCart.discountedProducts.map { it.id }

        expect {
            that(originalIds.distinct()) {
                hasSize(3)
            }
            that(afterAddIds.distinct()) {
                hasSize(4)
            }
            that(originalIds + afterAddIds.last()) {
                isEqualTo(afterAddIds)
            }
        }
    }

    @Test
    fun `total adds all products`() {
        val cart = Cart.create()
            .addProducts(TestData.sampleProduct1)
            .addProducts(TestData.sampleProduct2)
        expect {
            that(cart.totalPrice()) {
                isEqualTo(
                    TestData.sampleProduct1.price +
                            TestData.sampleProduct2.price
                )
            }
        }
    }

    @Test
    fun `remove product deletes item`() {
        val cart = Cart.create()
            .addProducts(TestData.sampleProduct1)
            .addProducts(TestData.sampleProduct2)
            .addProducts(TestData.sampleProduct3)

        val afterRemove = cart.removeProducts(
            cart.discountedProducts[1]
        )

        expect {
            that(afterRemove.discountedProducts) {
                hasSize(2)
            }
            that(afterRemove.discountedProducts.map { it.product }) {
                isEqualTo(listOf(TestData.sampleProduct1, TestData.sampleProduct3))
            }
            that(afterRemove.discountedProducts.map { it.id }.distinct()) {
                hasSize(2)
            }
        }
    }
}