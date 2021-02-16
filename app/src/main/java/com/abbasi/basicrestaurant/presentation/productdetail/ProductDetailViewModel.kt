package com.abbasi.basicrestaurant.presentation.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbasi.domain.models.Product
import com.abbasi.domain.models.Resource
import com.abbasi.domain.usecases.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    lateinit var productId: String
    lateinit var categoryId: String
    val product = MutableLiveData<Resource<Product>>()

    fun initialize(productId: String, categoryId: String) {

        this.productId = categoryId
        this.productId = productId

        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.get(productId, categoryId).collect {
                product.postValue(it)
            }
        }
    }
}