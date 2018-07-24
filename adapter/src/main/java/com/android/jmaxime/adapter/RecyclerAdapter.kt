package com.android.jmaxime.adapter

import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.ViewGroup
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

open class RecyclerAdapter<T>(
    var items: MutableList<T> = ArrayList(),
    defaultViewHolder: Class<out RecyclerViewHolder<*>>? = null,
    listener: RecyclerViewListener? = null,
    private val logger: Logger? = null
) : RecyclerView.Adapter<RecyclerViewHolder<T>>() {

    constructor(
        items: MutableList<T> = ArrayList(),
        defaultViewHolder: KClass<out RecyclerViewHolder<*>>? = null,
        listener: RecyclerViewListener? = null,
        logger: Logger? = null
    ) : this(items, defaultViewHolder?.java, listener, logger)


    private val viewTypeCache = SparseIntArray()
    private val register:
            HashMap<Int, Pair<Class<out RecyclerViewHolder<*>>, RecyclerViewListener?>> = HashMap()
    private var strategyViewType: ViewTypeStrategy<T>? = null

    var comparator: Comparator<T>? = null

    init {
        if (defaultViewHolder != null) {
            register[0] = Pair(defaultViewHolder, listener)
        }
    }

    override fun onViewRecycled(holder: RecyclerViewHolder<T>) {
        super.onViewRecycled(holder)
        logger?.log("A-RECYCLERADAPTER", "onViewRecycled pos: ${holder.adapterPosition}")
    }

    override fun onFailedToRecycleView(holder: RecyclerViewHolder<T>): Boolean {
        logger?.log("B-RECYCLERADAPTER", "onFailedToRecycleView")
        return super.onFailedToRecycleView(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        logger?.log("C-RECYCLERADAPTER", "onAttachedToRecyclerView")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        logger?.log("C-RECYCLERADAPTER", "onDetachedFromRecyclerView")
    }

    override fun getItemViewType(position: Int): Int {
        val item = this.getItem(position)
        val viewType = if (viewTypeCache.indexOfKey(position) > -1) {
            viewTypeCache[position]!!
        } else if (strategyViewType != null && item != null) {
            strategyViewType!!.getItemViewType(position, item)
        } else if (item is Container) {
            return Math.max(super.getItemViewType(position), item.viewType)
        } else {
            super.getItemViewType(position)
        }
        viewTypeCache.put(position, viewType)
        logger?.log("D-RECYCLERADAPTER", "getItemViewType pos: $position --> viewType: $viewType")
        return viewType
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<T> {
        logger?.log("E-RECYCLERADAPTER", "onCreateViewHolder viewType: $viewType")
        val classType = register[viewType]!!
        val instance = classType.first.getConstructor(ViewGroup::class.java)?.newInstance(parent)
        val viewHolder: RecyclerViewHolder<T> = instance as RecyclerViewHolder<T>
        viewHolder.listener = WeakReference(classType.second)
        viewHolder.viewType = viewType
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<T>, position: Int) {
        /*get viewModel*/
        logger?.log(
            "F-RECYCLERADAPTER",
            "onBindViewHolder pos: $position -> viewType: ${holder.viewType}"
        )
        val viewModel = getItem(position)
        when {
            viewModel is Container -> holder.bindApply((viewModel as Container).getValue())
            viewModel != null -> holder.bindApply(viewModel)
            else -> throw IllegalArgumentException("at position : $position,  viewModel must not be null.")
        }
    }

    fun registerViewHolder(
        @IntRange(from = 1) viewType: Int,
        view: KClass<out RecyclerViewHolder<*>>,
        listener: RecyclerViewListener? = null
    ) {
        registerViewHolder(viewType, view.java, listener)
    }

    fun registerViewHolder(
        @IntRange(from = 1) viewType: Int,
        view: Class<out RecyclerViewHolder<*>>,
        listener: RecyclerViewListener? = null
    ) {
        if (register.containsKey(viewType)) {
            throw IllegalArgumentException("ViewType $viewType is already exist.")
        }
        register[viewType] = Pair(view, listener)
    }

    fun setItemViewTypeStrategy(strategy: ViewTypeStrategy<T>) {
        strategyViewType = strategy
    }

    override fun getItemCount(): Int {
        logger?.log("F-RECYCLERADAPTER", "getItemCount : ${items.size}")
        return items.size
    }

    fun getItem(position: Int): T? {
        return if (position >= 0 && position < items.size) {
            items[position]
        } else {
            logger?.log("F-RECYCLERADAPTER", "getItem null pos: $position")
            null
        }
    }

    fun applySort(predicate: Comparator<T>? = null) {
        when {
            predicate != null -> Collections.sort(items, predicate)
            comparator != null -> Collections.sort(items, comparator)
        }
    }

    fun add(element: T, pos: Int = -1) {
        val position = Math.max(pos, items.size)
        items.add(position, element)
        notifyItemInserted(position)
    }

    fun add(elements: List<T>, pos: Int = -1) {
        val position = Math.max(pos, items.size)
        items.addAll(position, elements)
        notifyItemRangeInserted(position, elements.size)
    }

    fun update(items: List<T>, sorting: Boolean = true) {
        this.items = ArrayList(items)
        if (sorting) {
            applySort()
        }
        notifyDataSetChanged()
    }

    companion object {

        class Helper {

            fun attachInitBindingView(decorator: InitBindingViewHolder) {
                RecyclerViewHolder.bindingViewHolder = decorator
            }
        }

    }
}

interface Logger {
    fun log(tag: String, msg: String)
}

interface RecyclerViewListener

@Suppress("UNCHECKED_CAST")
class Container(val item: Any? = null, val viewType: Int = -1) {

    fun isEmpty(): Boolean {
        return item == null
    }

    fun <CAST> getValue(): CAST {
        return item as CAST
    }
}

interface ViewTypeStrategy<T> {
    fun getItemViewType(position: Int, item: T): Int
}