package com.minikorp.fakeshop.app.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.minikor.fakeshop.testdata.TestData
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.fake.FakeShopRepository
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.cart.Cart
import com.minikorp.fakeshop.util.KodeinViewModelFactory
import com.minikorp.fakeshop.util.bindViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import strikt.api.expect
import strikt.assertions.isEqualTo

/**
 * Basic UI test to showcase how they could be implemented
 * overriding view model creation, mutating live data
 * and then testing UI.
 *
 * Strategy would be similar for other views / fragments.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    private fun createCartFragment(products: List<Product>) {
        val repository = FakeShopRepository()
        val kodein = Kodein.lazy {
            bind<KodeinViewModelFactory>() with singleton { KodeinViewModelFactory(kodein.direct) }
            bindViewModel<CartViewModel>() with provider {
                CartViewModel(repository).apply {
                    cart.postValue(Cart.create(products))
                }
            }
        }
        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            CartFragment().apply { setTestKodein(kodein) }
        }
    }

    @Test
    fun adapter_has_items() {
        createCartFragment(
            listOf(
                TestData.sampleProduct1,
                TestData.sampleProduct2,
                TestData.sampleProduct3,
                TestData.sampleProduct1
            )
        )

        onView(withId(R.id.cart_list)).check { view, _ ->
            view as RecyclerView

            expect {
                that(view.adapter!!.itemCount) {
                    isEqualTo(4)
                }
            }
        }
    }
}