package com.github.namhyungu.jetcard.ui.component

import androidx.compose.runtime.Composable
import com.github.namhyungu.jetcard.model.Resource

@Composable
fun <T> ResourceContent(
    resource: Resource<T>,
    onEmpty: @Composable () -> Unit,
    onError: @Composable (Exception) -> Unit,
    onSuccess: @Composable (T) -> Unit,
) {
    when {
        resource.data != null -> {
            onSuccess(resource.data)
        }
        resource.hasError -> {
            onError(resource.exception!!)
        }
        else -> {
            onEmpty()
        }
    }
}