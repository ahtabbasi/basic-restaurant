package com.abbasi.basicrestaurant.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.abbasi.basicrestaurant.models.toGenericRecyclerItems
import com.abbasi.domain.models.transform
import com.abbasi.domain.usecases.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    val categoriesWithProducts = liveData(Dispatchers.IO) {
        emitSource(
            categoryUseCase.getAllWithProducts()
                .map { resource ->
                    resource.transform {
                        it.toGenericRecyclerItems()
                    }
                }.asLiveData()
        )
    }
}