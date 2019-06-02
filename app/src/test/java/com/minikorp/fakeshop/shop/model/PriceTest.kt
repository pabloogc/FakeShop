package com.minikorp.fakeshop.shop.model

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

@RunWith(RobolectricTestRunner::class)
class PriceTest {

    @Test
    fun `usd price displayed with correct format`() {
        val price = Price(1234, Currency.getInstance("USD"))
        expectThat(price.displayPrice) {
            isEqualTo("USD12.34")
        }
    }

    @Test
    fun `eur price displayed with correct format`() {
        val price = Price(12, Currency.getInstance("EUR"))
        expectThat(price.displayPrice) {
            isEqualTo("€0.12")
        }
    }

    //TODO: More currency display testing
}