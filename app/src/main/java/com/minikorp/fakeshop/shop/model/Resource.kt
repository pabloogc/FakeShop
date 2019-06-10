package com.minikorp.fakeshop.shop.model

/**
 * Simple wrapper to map ongoing tasks (network) to LiveData for view implementation.
 */
sealed class Resource<T>(
   val data: T? = null,
   val message: String? = null
) {
   class Success<T>(data: T) : Resource<T>(data)
   class Loading<T>(data: T? = null) : Resource<T>(data)
   class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
}