package com.decathlon.manager.internal.common

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class RecyclerViewHolder<T>(parent: ViewGroup, @LayoutRes idRes: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(idRes, parent, false)) {

    protected var mItem: T? = null
    protected var isBound: Boolean = false

    fun bindApply(item: T) {
        mItem = item
        isBound = false
        onBind(mItem!!)
        isBound = true
    }

    abstract protected fun onBind(item: T)
}