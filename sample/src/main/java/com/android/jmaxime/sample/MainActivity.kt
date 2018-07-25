package com.android.jmaxime.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.android.jmaxime.adapter.*

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
            listener = object :ViewOne.ViewOneListener{
                override fun onClick(item: Int) {
                    TODO("not implemented")
                }

            }
        )

        /*sample for multi Cell [3 viewholder resgistered for this sample]*/
        adapter.setItemViewTypeStrategy(strategy = object : ViewTypeStrategy<Int> {
            override fun getItemViewType(position: Int, item: Int): Int {
                return when{
                    position == 0 -> 0
                    item > 5 -> 1
                    else -> 2
                }
            }
        })
    }

    class ViewOne(parent: ViewGroup) : RecyclerViewHolder<Int>(
            parent, R.layout.support_simple_spinner_dropdown_item
        ) {

        init {
            itemView.setOnClickListener {
                (getCommunication() as ViewOneListener).onClick(item)
            }
        }
        override fun onBind() {
            Log.d("MY VALUE", "value is $item")
        }

        interface ViewOneListener: RecyclerViewListener{
            fun onClick(item: Int)
        }
    }
}
