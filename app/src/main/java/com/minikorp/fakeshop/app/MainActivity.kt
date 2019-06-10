package com.minikorp.fakeshop.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.minikorp.fakeshop.R
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein = Kodein {
        extend(app.kodein)
        import(MainModule.create())
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Setup toolbar (order of this calls is important, toolbar must be set before nav controller)
        setSupportActionBar(toolbar)
        val nav = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(nav.graph)
        toolbar.setupWithNavController(nav, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() or super.onSupportNavigateUp()
    }
}