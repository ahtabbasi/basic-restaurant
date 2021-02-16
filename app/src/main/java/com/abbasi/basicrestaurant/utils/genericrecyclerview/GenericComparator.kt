package com.abbasi.basicrestaurant.utils.genericrecyclerview

import androidx.recyclerview.widget.DiffUtil


/**
 * This is a generic comparator that adapter can use to compare items
 */
object GenericComparator : DiffUtil.ItemCallback<GenericRecyclerItem>() {
    override fun areItemsTheSame(oldItem: GenericRecyclerItem, newItem: GenericRecyclerItem) =
        oldItem.hashCode() == newItem.hashCode()


    override fun areContentsTheSame(oldItem: GenericRecyclerItem, newItem: GenericRecyclerItem) =
        oldItem.hashCode() == newItem.hashCode()
}