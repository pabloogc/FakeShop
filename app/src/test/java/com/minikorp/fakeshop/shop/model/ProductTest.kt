package com.minikorp.fakeshop.shop.model

import com.minikorp.fakeshop.shop.data.network.NetworkProduct
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo

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
        val anyId = "any_id"
        val anyName = "test_product"
        val networkProduct = NetworkProduct(
            code = anyId,
            name = anyName,
            price = 7.5f
        )
        val mapped = Product.fromNetworkProduct(networkProduct)
        expectThat(mapped.code) { isEqualTo(ProductCode(anyId)) }
        expectThat(mapped.name) { isEqualTo(anyName) }
        expectThat(mapped.price.value) { isEqualTo(750) }
    }
}