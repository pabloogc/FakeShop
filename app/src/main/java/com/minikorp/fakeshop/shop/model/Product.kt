package com.minikorp.fakeshop.shop.model

import com.minikorp.fakeshop.shop.data.network.NetworkProduct

/** Type safe product code */
inline class ProductCode(val code: String)

/**
 * A product sold in the store.
 */
data class Product(
    val code: ProductCode,
    val name: String,
    val price: Price
) {
    companion object {
        /**
         * Mapper/factory function. Since transformation is a pure function
         * this works just fine, no need for more complicated
         * abstractions like mapper classes.
         */
        fun fromNetworkProduct(networkProduct: NetworkProduct): Product {
            return Product(
                code = ProductCode(networkProduct.code),
                name = networkProduct.name,
                price = Price(
                    //Network products are in full euros,
                    //but we want to represent in cents to avoid
                    //potential floating errors
                    //also makes formatting easier
                    value = Math.round(networkProduct.price * 100)
                )
            )
        }
    }
}

inline class ProductList(val products: List<Product>)