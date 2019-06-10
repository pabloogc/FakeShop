package com.minikorp.fakeshop.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.app.cart.CartModule
import com.minikorp.fakeshop.app.products.ProductModule
import com.minikorp.fakeshop.util.KodeinViewModelFactory
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein = Kodein {
        extend(app.kodein)
        //Bind view model factory so ViewModels can be created with dependencies
        bind<KodeinViewModelFactory>() with singleton { KodeinViewModelFactory(kodein.direct) }
        import(ProductModule.create())
        import(CartModule.create())
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        val nav = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(nav.graph)
        toolbar.setupWithNavController(nav, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() or super.onSupportNavigateUp()
    }
}