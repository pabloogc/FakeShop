package com.minikorp.fakeshop.shop.data.network

import retrofit2.http.GET

interface ProductsApi {
    @GET("4bwec")
    suspend fun getProducts(): NetworkProductList
}