package com.minikorp.fakeshop.shop.model

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

@RunWith(RobolectricTestRunner::class)
class ProductTest {

    /**
     * Dummy test, if product model
     * grows with collections, optional values and so
     * mapper can get more complicated so it's not
     * worthless to write.
     */
    @Test
    fun `product from network has correct mapping`() {
        val networkProduct = NetworkProduct(
            code = "any_id",
            name = "test_product",
            price = 2000,
            currency = "EUR"
        )
        val mapped = Product.fromNetworkProduct(networkProduct)
        expectThat(mapped.code) { isEqualTo(ProductCode("any_id")) }
        expectThat(mapped.name) { isEqualTo("test_product") }
        expectThat(mapped.price.price) { isEqualTo(2000) }
        expectThat(mapped.price.currency) { isEqualTo(Currency.getInstance("EUR")) }
    }
}