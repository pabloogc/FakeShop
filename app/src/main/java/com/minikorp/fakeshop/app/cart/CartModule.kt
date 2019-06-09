package com.minikorp.fakeshop.app.cart

import com.minikorp.fakeshop.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object CartModule {
    fun create() = Kodein.Module("Cart Module") {
        bindViewModel<CartViewModel>() with provider { CartViewModel(instance()) }
    }
}