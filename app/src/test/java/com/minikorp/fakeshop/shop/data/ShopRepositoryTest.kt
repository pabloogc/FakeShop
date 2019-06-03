package com.minikorp.fakeshop.shop.data

import com.minikorp.fakeshop.shop.data.network.NetworkProduct
import com.minikorp.fakeshop.shop.data.network.NetworkProductList
import com.minikorp.fakeshop.shop.data.network.ShopApi
import com.minikorp.fakeshop.shop.model.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

class ShopRepositoryTest {

    @Test
    fun `items are mapped`() = runBlocking {
        val mockApi = mock<ShopApi> {
            onBlocking { getProducts() } doReturn NetworkProductList(
                products = TestData.sampleProducts.map {
                    NetworkProduct(
                        code = it.code.code,
                        name = it.name,
                        price = it.price.value / 100f
                    )
                }
            )
        }
        val repo = NetworkShopRepository(mockApi)
        val products = repo.getProducts().getOrThrow()
        expectThat(products.products) {
            hasSize(TestData.sampleProducts.size)
        }
        expectThat(products.products.first()) {
            isEqualTo(TestData.sampleProduct1)
        }
        Unit //Must be void
    }

    @Test
    fun `failures are wrapped`() = runBlocking {
        val mockApi = mock<ShopApi> {
            onBlocking { getProducts() } doThrow RuntimeException("Failed request")
        }
        val repo = NetworkShopRepository(mockApi)
        val products = repo.getProducts()
        expectThat(products.exceptionOrNull()) {
            isNotNull()
        }
        Unit //Must be void
    }

    //TODO: More tests like for data validation
}