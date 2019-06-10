package com.minikorp.fakeshop.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.instanceOrNull

/** Utility function to bind ViewModel to kodein module so it can retrieve dependencies. */
inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null)
        : Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.name, overrides)
}

/** The view model factory using Kodein. */
class KodeinViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.name) as T? ?: modelClass.newInstance()
    }
}

/** Utility property to avoid boilerplate injecting view models */
inline fun <reified VM : ViewModel, T> T.injectableViewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
    return lazy {
        ViewModelProviders.of(this, direct.instance()).get(VM::class.java)
    }
}

/** Utility property to avoid boilerplate injecting view models */
inline fun <reified VM : ViewModel, T> T.injectableViewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy {
        ViewModelProviders.of(this.requireActivity(), direct.instance<KodeinViewModelFactory>()).get(VM::class.java)
    }
}
