package com.minikorp.fakeshop.fake

import com.minikorp.fakeshop.shop.data.ShopRepository
import com.minikorp.fakeshop.shop.model.ProductList
import com.minikorp.fakeshop.shop.model.discount.ApplicableDiscountList

class FakeShopRepository : ShopRepository {
    var products: Result<ProductList> =
        Result.success(ProductList(emptyList()))
    var discounts: Result<ApplicableDiscountList> =
        Result.success(ApplicableDiscountList(emptyList()))

    override suspend fun getProducts(): Result<ProductList> {
        return products
    }

    override fun getActiveDiscounts(): Result<ApplicableDiscountList> {
        return discounts
    }
}