# Status
![alt text](https://travis-ci.org/MaximeJallu/generic-adapter-kotlin.svg?branch=develop) [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)


# Generic-Adapter-Kotlin:

This tool allows you to no longer worry about adapters. Now you will only create your ViewHolder.
Communication management between your Views and your ViewHolders is possible.
Creating sections is now very easily.
Enjoy.

# Download [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.maximejallu/adapters-kotlin/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.maximejallu/adapters-kotlin)
buildtool used is 27
use {exclude group: 'com.android.support'} only if you have problems
```groovy
dependencies {
    ...
    implementation ('com.github.maximejallu:adapters-kotlin:{version}')
    ...
}
```
# Decorators for : [ButterKnife][1] - [Picasso][2] - [Glide][3] ...
```kotlin
class SampleApplication: Application {

    override 
    fun onCreate() {
        super.onCreate()

        RecyclerAdapter.Companion.Helper()
                    .attachInitBindingView(decorator = object : InitBindingViewHolder {
                        override fun initBinding(target: Any, view: View) {
                            if (view.javaClass.simpleName.contains(".java")) {
                                //TODO e.g. butter-knife binding...
                            }
                        }
                    })
    }
}
```

# 1- Create yours RecyclerViewHolder (Easy sample method)
```kotlin
class ViewOne(parent: ViewGroup) : RecyclerViewHolder<Int>(
            parent, R.layout.support_simple_spinner_dropdown_item
        ) {

        init {
            itemView.setOnClickListener {
                (getCommunication() as ViewOneListener).onClick(item)
            }
        }
       
        override fun onBind() {
            itemView.myTextViewId.text = "text: $item"
        }

        interface ViewOneListener: RecyclerViewListener{
            fun onClick(item: Int)
        }  
    }
```

# 2 - Create and init Adapter
```kotlin
fun sample(){
val adapter = RecyclerAdapter<Int>(
            items = ArrayList(),
            defaultViewHolder = ViewOne::class, /*for getItemViewType == 0*/
            listener = object :ViewOne.ViewOneListener{
                            override fun onClick(item: Int) {
                                TODO("not implemented")
                            }
            
                        }
        )
    /*attache others viewHolder*/    
    adapter.registerViewHolder(TYPE_2, ViewTwo::class, listener = null)      
} 
```

# 3 - Declare strategy view type is necessary (Multi cell method)
```kotlin
fun multicellSample(){
        /*sample for multi Cell [3 viewholder resgistered for this sample]*/
        adapter.setItemViewTypeStrategy(strategy = object : ViewTypeStrategy<Int> {
            override fun getItemViewType(position: Int, item: Int): Int {
                return when{
                    position == 0 -> TYPE_1
                    item > 5 -> TYPE_2
                    else -> TYPE_3
                }
            }
        })
}
```

[1]: https://github.com/JakeWharton/butterknife
[2]: https://github.com/square/picasso
[3]: https://github.com/bumptech/glide