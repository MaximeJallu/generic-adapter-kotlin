package com.decathlon.manager.internal.common

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class RecyclerViewHolder<T>(parent: ViewGroup, @LayoutRes idRes: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(idRes, parent, false)) {

    protected var mItem: T? = null
    protected var isBound: Boolean = false
        private set
    private var callback: (() -> Unit)? = null

    fun bindApply(item: T) {
        mItem = item
        isBound = false
        onBind(mItem!!)
        isBound = true
    }

    abstract protected fun onBind(item: T)

    @Suppress("UNCHECKED_CAST")
    protected fun <I> getCommunication(): I? {
        var i: I? = null
        try {
            i = callback as I
        } catch (e: ClassCastException) {
            Log.e("ViewHolderFactory", "getInterfaceCallback: ", e)
        }

        return i
    }
}