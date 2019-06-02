package com.minikorp.fakeshop.shop.model

object TestData {
    val sampleProduct1 = Product(
        code = ProductCode("TEST_PRODUCT_1"),
        name = "Test Product 1",
        price = Price(100)
    )

    val sampleProduct2 = Product(
        code = ProductCode("TEST_PRODUCT_2"),
        name = "Test Product 2",
        price = Price(2000)
    )

    val sampleProduct3 = Product(
        code = ProductCode("TEST_PRODUCT_3"),
        name = "Test Product 3",
        price = Price(30000)
    )
}