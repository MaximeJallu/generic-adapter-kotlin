package com.android.jmaxime.generic_adapter_kotlin

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import com.decathlon.manager.internal.common.RecyclerViewHolder

abstract class EmptyRecyclerViewHolder(parent: ViewGroup, @LayoutRes idRes: Int) : RecyclerViewHolder<Void>(parent, idRes) {

    override fun onBind(item: Void) {
        throw RuntimeException("The method onBind (Object item) should not be called for this type of viewHolder")
    }

    abstract fun onBind()
}