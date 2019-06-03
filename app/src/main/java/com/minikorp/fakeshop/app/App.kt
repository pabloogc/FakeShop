package com.minikorp.fakeshop.app

import android.app.Application
import com.minikorp.fakeshop.shop.data.ShopModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

private lateinit var _app: App
val app get() = _app

class App : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        _app = this
    }

    override val kodein: Kodein by Kodein.lazy {
        import(ShopModule.create())
    }
}