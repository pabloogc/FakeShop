package com.minikorp.fakeshop.util

import android.view.View
import com.minikorp.fakeshop.shop.model.Resource

/**
 * Toggle two views between content / loading / error based on [Resource] state.
 */
fun toggleViewsVisibility(
    resource: Resource<*>,
    contentView: View,
    loadingView: View,
    errorView: View,
    invisibilityType: Int = View.INVISIBLE
) {
    val (content, loading, error) = when (resource) {
        is Resource.Loading -> {
            Triple(invisibilityType, View.VISIBLE, invisibilityType)
        }

        is Resource.Success -> {
            Triple(View.VISIBLE, invisibilityType, invisibilityType)
        }
        is Resource.Error -> {
            Triple(invisibilityType, invisibilityType, View.VISIBLE)
        }
    }
    contentView.visibility = content
    loadingView.visibility = loading
    errorView.visibility = error
}



