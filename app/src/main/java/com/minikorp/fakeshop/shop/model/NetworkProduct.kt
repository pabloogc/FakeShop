package com.minikorp.fakeshop.shop.model

/**
 * A product as it would be served from the server.
 *
 * Sample JSON:
 *
 * ```js
 * {"code":"VOUCHER","name":"Cabify Voucher","price":5}
 * ```
 */
class NetworkProduct(
    val code: String,
    /**
     * Localized product name
     */
    val name: String,
    /**
     * Price in euros, cents are decimal values.
     */
    val price: Float
)