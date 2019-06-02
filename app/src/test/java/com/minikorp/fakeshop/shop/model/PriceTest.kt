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
    fun `eur price displayed with correct format`() {
        expectThat(Price(12).displayPrice) {
            isEqualTo("0,12 €")
        }

        expectThat(Price(9000).displayPrice) {
            isEqualTo("90,00 €")
        }
    }
}