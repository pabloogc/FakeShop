package com.minikorp.fakeshop.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.shop.data.network.NetworkModule
import com.minikorp.fakeshop.shop.data.network.ProductsApi
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein = Kodein {
        import(NetworkModule.create())
    }

    private val scope = CoroutineScope(Job())
    private val productsApi: ProductsApi by kodein.instance()

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch(Dispatchers.IO) {
            val products = productsApi.getProducts()
            val out = products
        }
    }
}
