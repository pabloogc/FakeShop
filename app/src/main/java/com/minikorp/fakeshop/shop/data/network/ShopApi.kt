package com.minikorp.fakeshop.shop.data.network

import retrofit2.http.GET

/**
 * Cabify Shop API, with one one modest call.
 */
interface ShopApi {
    @GET("4bwec")
    suspend fun getProducts(): NetworkProductList
}