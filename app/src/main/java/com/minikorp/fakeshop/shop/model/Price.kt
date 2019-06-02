package com.minikorp.fakeshop.shop.model

import java.text.NumberFormat
import java.util.*

data class Price(
    /**
     * Minimum common denominator for currency (cents), ex: 200 -> 2 euros
     */
    val price: Int,
    /**
     * The currency the price is expressed with.
     */
    val currency: Currency
) {
    /**
     * Human readable price in EU locale.
     *
     * This method is should _not_ be here, it's model responsibility to map
     * the price to human readable text, however,
     * formatting and locales goes beyond scope of this demo.
     */
    val displayPrice by lazy {
        val floatingPrice = price.toFloat() /
                Math.pow(10.0, currency.defaultFractionDigits.toDouble())
        NumberFormat
            //Hardcoded UK locale to make it pretty for demo
            .getCurrencyInstance(Locale.UK)
            .apply {
                currency = this@Price.currency
            }
            .format(floatingPrice)
    }

    override fun toString(): String = displayPrice
}