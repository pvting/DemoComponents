package com.pvting.lambda.higerOrderFunctions

//手写高阶函数 apply
inline fun <T> T.myApply(m:T.()->Unit):T{
    m()
    return this
}

//run
inline fun <T> T.myRun(m:T.()->Unit) {
    m()
}

//let
inline fun <T,R> T.myLet(m:(T)->R):R{
    return m(this)
}

//also
inline fun <T> T.myAlso(m:T.()->T):T {
    m(this)
    return this
}

fun main() {
    val num = 111.myApply {
        println(this)
    }
    println(num)

}

