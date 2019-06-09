package com.minikorp.fakeshop.app

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

abstract class BaseFragment : Fragment(), KodeinAware {
    /**
     * Container activity [Kodein].
     */
    override val kodein: Kodein
        get() = (requireActivity() as KodeinAware).kodein
}