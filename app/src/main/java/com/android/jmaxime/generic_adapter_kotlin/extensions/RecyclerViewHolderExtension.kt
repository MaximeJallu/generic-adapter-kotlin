package com.decathlon.manager.internal.extensions


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.PluralsRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView

fun RecyclerView.ViewHolder.getContext(): Context {
    return this.itemView.context
}

fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(getContext(), id)
}

fun RecyclerView.ViewHolder.getString(@StringRes id: Int): String {
    return getContext().getString(id)
}

fun RecyclerView.ViewHolder.getString(@StringRes stringRes: Int, string: String): String {
    return getContext().resources.getString(stringRes, string)
}

fun RecyclerView.ViewHolder.getString(@StringRes stringRes: Int, vararg formatArg: Any): String {
    return getContext().resources.getString(stringRes, formatArg)
}

fun RecyclerView.ViewHolder.getQuantityStringFormat(@PluralsRes pluralRes: Int, quantity: Int): String {
    return getContext().resources.getQuantityString(pluralRes, quantity, quantity)
}

fun RecyclerView.ViewHolder.getDrawable(@DrawableRes drawableResId: Int): Drawable? {
    return ContextCompat.getDrawable(getContext(), drawableResId)
}

fun RecyclerView.ViewHolder.getDrawable(@DrawableRes drawableId: Int, @ColorRes colorId: Int): Drawable? {
    val drawable = getDrawable(drawableId)
    DrawableCompat.setTint(drawable?.mutate()!!, getColor(colorId))
    return drawable
}