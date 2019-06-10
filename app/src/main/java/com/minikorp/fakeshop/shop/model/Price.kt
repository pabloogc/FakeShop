package com.minikorp.fakeshop.shop.model

import java.text.NumberFormat
import java.util.*

data class Price(
    /**
     * Minimum common denominator for currency (cents), ex: 200 -> 2 euros
     */
    val value: Int
) {
    /**
     * Human readable price in EU locale.
     *
     * This method is should _not_ be here, it's view responsibility to map
     * the price to human readable text, however,
     * formatting and locales goes beyond scope of this demo.
     *
     * Also lazy, since this kind of formatting is expensive inside lists.
     */
    val displayPrice: String by lazy {
        val floatingPrice = value / 100f
        //Hardcoded locale to make it appear as euros, for demo only
        NumberFormat
            //Germany just to get actual euro symbol instead of EUR
            .getCurrencyInstance(Locale.GERMANY)
            .format(floatingPrice)
    }

    override fun toString(): String = displayPrice

    operator fun plus(other: Price): Price {
        return Price(this.value + other.value)
    }
}