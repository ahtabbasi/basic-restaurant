package com.abbasi.basicrestaurant.utils.genericrecyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abbasi.domain.models.Resource
import com.abbasi.domain.models.getDataOrNull


@BindingAdapter("app:bound_items")
fun setRecyclerViewResourceItems(
    recyclerView: RecyclerView,
    items: Resource<List<GenericRecyclerItem>>?
) = setupDataBoundListAdapter(recyclerView, items?.getDataOrNull() ?: emptyList())


@BindingAdapter("app:bound_items")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<GenericRecyclerItem>?
) = setupDataBoundListAdapter(recyclerView, items.orEmpty())


private fun setupDataBoundListAdapter(
    recyclerView: RecyclerView,
    items: List<GenericRecyclerItem>
) {
    var adapter = (recyclerView.adapter as? DataBoundListAdapter)
    if (adapter == null) {
        adapter = DataBoundListAdapter()
        recyclerView.adapter = adapter
    }

    adapter.submitList(items)
}