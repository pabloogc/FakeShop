package com.minikorp.fakeshop.shop.data

import com.minikorp.fakeshop.shop.data.network.ShopApi
import com.minikorp.fakeshop.shop.model.Price
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.ProductCode
import com.minikorp.fakeshop.shop.model.ProductList
import com.minikorp.fakeshop.shop.model.discount.ApplicableDiscount
import com.minikorp.fakeshop.shop.model.discount.BulkDiscount
import com.minikorp.fakeshop.shop.model.discount.TwoForOneDiscount

interface ShopRepository {
    /**
     * Fetch products or wrap exception.
     */
    suspend fun getProducts(): Result<ProductList>

    /**
     * Fetch active discounts.
     */
    fun getActiveDiscounts(): Result<List<ApplicableDiscount>>
}

class NetworkShopRepository(private val api: ShopApi) : ShopRepository {

    override suspend fun getProducts(): Result<ProductList> {
        return runCatching {
            ProductList(api.getProducts().products.map {
                Product.fromNetworkProduct(it)
            })
        }
    }

    override fun getActiveDiscounts(): Result<List<ApplicableDiscount>> {
        //This should come from network, hardcoded here for sake of simplicity
        return Result.success(
            listOf(
                TwoForOneDiscount(ProductCode("VOUCHER")),
                BulkDiscount(ProductCode("TSHIRT"), Price(-100), 3)
            )
        )
    }
}