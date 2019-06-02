package com.minikorp.fakeshop.shop.model

/**
 * A product as it would be served from the server.
 *
 * Sample JSON:
 *
 * ```{"code":"VOUCHER","name":"Cabify Voucher","price":5}```
 */
class NetworkProduct(
    val code: String,
    /**
     * Localized product name
     */
    val name: String,
    /**
     * Price in euros, no cents.
     */
    val price: Int
)