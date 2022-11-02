package com.pvting.lambda.likeRxjava

fun<R> create(action:()->R):Helper<R>{
    val newItem = action()
    return Helper(newItem)
}

class Helper<T>(private val item:T){
    fun <R>map(action:(T)-> R):Helper<R>{
        val newItem = action(item)
        return Helper(newItem)
    }
    fun consumer(action: (T)->Unit){
        action(item)
    }
}
fun main() {
    create {
        1
    }.map {
        println("$it a")
    }.map {
        1.2
    }.consumer {
        println(it)
    }
}

