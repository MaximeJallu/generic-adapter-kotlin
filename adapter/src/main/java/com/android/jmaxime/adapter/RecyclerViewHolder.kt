package com.android.jmaxime.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.android.jmaxime.adapter.extensions.inflate
import java.lang.ref.WeakReference

abstract class RecyclerViewHolder<T>(
    parent: ViewGroup, @LayoutRes idRes: Int
) : RecyclerView.ViewHolder(parent.inflate(idRes)) {

    init {
        bindingViewHolder?.initBinding(target = this, view = itemView)
    }

    protected val item: T get() = value!!
    internal var viewType: Int = -1
    private var value: T? = null
    private var listener: WeakReference<RecyclerViewListener?>? = null

    protected var isBindCompleted: Boolean = false
        private set

    fun initialize(listener: RecyclerViewListener?, viewType: Int) {
        this.listener = WeakReference(listener)
        this.viewType = viewType
        onCreated()
    }

    fun bindApply(item: T?) {
        this.value = item
        isBindCompleted = false
        onBind()
        isBindCompleted = true
    }

    protected abstract fun onBind()

    protected open fun onCreated() {

    }

    @Suppress("UNCHECKED_CAST")
    fun <LISTENER> getCommunication(): LISTENER {
        return listener?.get() as LISTENER
    }

    companion object {
        internal var bindingViewHolder: InitBindingViewHolder? = null
    }
}