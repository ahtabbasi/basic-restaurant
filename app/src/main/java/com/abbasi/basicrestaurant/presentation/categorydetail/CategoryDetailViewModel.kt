package com.abbasi.basicrestaurant.presentation.categorydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbasi.basicrestaurant.models.toGenericRecyclerItemsFromDomain
import com.abbasi.basicrestaurant.utils.genericrecyclerview.GenericRecyclerItem
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.transform
import com.abbasi.domain.usecases.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    lateinit var categoryId: String
    lateinit var categoryName: String
    val products = MutableLiveData<Resource<List<GenericRecyclerItem>>>()

    fun initialize(categoryId: String, categoryName: String) {

        this.categoryId = categoryId
        this.categoryName = categoryName

        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.getByCategoryId(categoryId).map {
                it.transform { list -> list.toGenericRecyclerItemsFromDomain() }
            }.collect {
                products.postValue(it)
            }
        }
    }
}