package com.decathlon.manager.internal.common

import android.support.v7.widget.RecyclerView

abstract class RecyclerAdapter<ITEM>(protected var items: List<ITEM> = ArrayList()) : RecyclerView.Adapter<RecyclerViewHolder<ITEM>>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<ITEM>, position: Int) {
        holder.bindApply(items[position])
    }

    fun update(data: List<ITEM>) {
        items = data
        notifyDataSetChanged()
    }

    fun getItem(position: Int): ITEM {
        return items[position]
    }
}