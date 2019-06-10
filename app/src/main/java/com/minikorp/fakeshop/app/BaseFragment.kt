package com.minikorp.fakeshop.app

import androidx.fragment.app.Fragment
import org.jetbrains.annotations.TestOnly
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

abstract class BaseFragment : Fragment(), KodeinAware {

    /**
     * Container activity [Kodein].
     * Since we are single activity this should always hold true.
     */
    override val kodein: Kodein
        get() = testKodein ?: (requireActivity() as KodeinAware).kodein

    private var testKodein: Kodein? = null

    /**
     * Set the dependency injector, only for testing.
     */
    @TestOnly
    fun setTestKodein(kodein: Kodein?) {
        testKodein = kodein
    }
}