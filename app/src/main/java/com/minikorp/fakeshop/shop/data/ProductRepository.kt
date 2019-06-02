package com.minikorp.fakeshop.shop.data

import com.minikorp.fakeshop.shop.data.network.ProductsApi
import com.minikorp.fakeshop.shop.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}

class NetworkProductRepository(val productsApi: ProductsApi) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return runCatching {
            productsApi.getProducts().products.map {
                Product.fromNetworkProduct(it)
            }
        }
    }
}