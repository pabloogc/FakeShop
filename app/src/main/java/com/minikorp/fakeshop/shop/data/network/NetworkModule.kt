package com.minikorp.fakeshop.shop.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    fun create(): Kodein.Module = Kodein.Module("NetworkModule") {

        bind<Moshi>() with singleton {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        bind<ProductsApi>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/") //TODO not hardcoded
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build()
                .create(ProductsApi::class.java)
        }
    }
}