package com.minikorp.fakeshop.app

import com.minikorp.fakeshop.shop.data.ShopModule
import org.kodein.di.Kodein

object AppModule {
    fun create() = Kodein.Module("AppModule") {
        import(ShopModule.create())
    }
}