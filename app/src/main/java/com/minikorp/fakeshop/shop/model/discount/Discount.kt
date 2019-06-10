package com.minikorp.fakeshop.shop.model.discount

import com.minikorp.fakeshop.shop.model.Price

/** Type safe discount code */
inline class DiscountCode(val code: String)

/**
 * An applied discount to a product
 * @param code unique id for the discount, from the [ApplicableDiscount] that applied it.
 * @param price relative price change, usually negative to subtract from base product price
 */
data class Discount(val code: DiscountCode, val price: Price)