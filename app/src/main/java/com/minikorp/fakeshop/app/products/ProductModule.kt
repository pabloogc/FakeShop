package com.minikorp.fakeshop.app.products

import com.minikorp.fakeshop.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object ProductModule {
    fun create() = Kodein.Module("ProductModule") {
        bindViewModel<ProductListViewModel>() with provider {
            ProductListViewModel(instance())
        }
    }
}