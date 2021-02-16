package com.abbasi.basicrestaurant.utils.genericrecyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * A generic recycler view item that can hold [Any] data
 *
 * @param data data stored in item
 * @param layoutId xml to inflate for this item
 * @param variableId dynamic data binding variable in the xml. Data is assigned to it
 */
data class GenericRecyclerItem(
    val data: Any,
    @LayoutRes val layoutId: Int,
    val variableId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(variableId, data)
    }
}