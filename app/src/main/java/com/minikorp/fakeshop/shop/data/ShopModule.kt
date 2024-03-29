package com.minikorp.fakeshop.shop.data

import com.minikorp.fakeshop.shop.data.network.ShopApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Application wide module, holding bindings to communicate with the API (Retrofit, Moshi, API itself..)
 */
object ShopModule {
    fun create(): Kodein.Module = Kodein.Module("NetworkModule") {

        bind<Moshi>() with singleton {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/") //TODO not hardcoded
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build()
        }

        bind<ShopApi>() with singleton {
            instance<Retrofit>().create(ShopApi::class.java)
        }

        bind<ShopRepository>() with singleton {
            NetworkShopRepository(instance())
        }
    }
}