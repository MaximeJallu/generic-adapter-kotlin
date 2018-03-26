package com.android.jmaxime.generic_adapter_kotlin

class Container(private val item: Any? = null) {

    fun isEmpty(): Boolean {
        return item == null
    }

    @Suppress("UNCHECKED_CAST")
    fun <CAST> getValue(): CAST {
        return item as CAST
    }
}