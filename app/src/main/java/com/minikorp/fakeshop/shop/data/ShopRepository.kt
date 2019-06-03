package com.minikorp.fakeshop.shop.data

import com.minikorp.fakeshop.shop.data.network.ShopApi
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.ProductList

interface ShopRepository {
    /**
     * Fetch products or wrap exception.
     */
    suspend fun getProducts(): Result<ProductList>
}

class NetworkShopRepository(private val api: ShopApi) : ShopRepository {

    override suspend fun getProducts(): Result<ProductList> {
        return runCatching {
            ProductList(api.getProducts().products.map {
                Product.fromNetworkProduct(it)
            })
        }
    }
}