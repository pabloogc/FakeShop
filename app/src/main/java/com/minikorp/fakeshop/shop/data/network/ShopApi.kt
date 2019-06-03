package com.minikorp.fakeshop.shop.data.network

import retrofit2.http.GET

interface ShopApi {
    @GET("4bwec")
    suspend fun getProducts(): NetworkProductList
}