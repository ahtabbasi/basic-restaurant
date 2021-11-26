package com.abbasi.basicrestaurant.utils.genericrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * A generic list adapter which takes [GenericRecyclerItem] items and display the using data binding
 */
class DataBoundListAdapter(
    comparator: DiffUtil.ItemCallback<GenericRecyclerItem> = GenericComparator
) : ListAdapter<GenericRecyclerItem, DataBoundListAdapter.BindingViewHolder>(comparator) {

    override fun getItemCount() = currentList.size
    override fun getItemViewType(position: Int) = getItem(position).layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }

    class BindingViewHolder(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}


