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
     * This method is should _not_ be here, it's model responsibility to map
     * the price to human readable text, however,
     * formatting and locales goes beyond scope of this demo.
     */
    val displayPrice by lazy {
        val floatingPrice = value / 100f
        //Hardcoded locale to make it appear as euros, for demo only
        NumberFormat
            .getCurrencyInstance(Locale.GERMANY)
            .format(floatingPrice)
    }

    override fun toString(): String = displayPrice

}