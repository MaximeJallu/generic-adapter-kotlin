package com.android.jmaxime.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.android.jmaxime.adapter.InitBindingViewHolder
import com.android.jmaxime.adapter.RecyclerAdapter
import com.android.jmaxime.adapter.RecyclerViewHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecyclerAdapter.Companion.Helper()
            .attachInitBindingView(decorator = object : InitBindingViewHolder {
                override fun initBinding(target: Any, view: View) {
                    if (view.javaClass.simpleName.contains(".java")) {
                        //TODO e.g. butter-knife binding...
                    }
                }
            })

        val adapter = RecyclerAdapter<Int>(
            items = ArrayList(),
            defaultViewHolder = ViewOne::class,
            listener = null
        )

    }

    class ViewOne(parent: ViewGroup) :
        RecyclerViewHolder<Int>(
            parent,
            R.layout.support_simple_spinner_dropdown_item
        ) {

        override fun onBind() {
            Log.d("MY VALUE", "value is $item")
        }

    }
}
