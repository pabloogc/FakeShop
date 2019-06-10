package com.minikorp.fakeshop.app

import com.minikorp.fakeshop.app.cart.CartModule
import com.minikorp.fakeshop.app.products.ProductModule
import com.minikorp.fakeshop.util.KodeinViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Module for activity
 */
object MainModule {
    fun create() = Kodein.Module("MainModule") {
        //Bind view model factory so ViewModels can be created with dependencies
        bind<KodeinViewModelFactory>() with singleton { KodeinViewModelFactory(kodein.direct) }
        import(ProductModule.create())
        import(CartModule.create())
    }
}