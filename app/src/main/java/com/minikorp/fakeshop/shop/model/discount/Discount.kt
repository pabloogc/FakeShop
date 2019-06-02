package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.Price

inline class DiscountCode(val code: String)

/**
 * An applied discount to a product
 * @param code unique id for the discount
 * @param price price, usually negative to subtract from base product price
 */
data class Discount(val code: DiscountCode, val price: Price)