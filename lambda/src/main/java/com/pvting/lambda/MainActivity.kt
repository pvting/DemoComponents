package com.pvting.lambda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show {
            println("我就是：$it")
        }

        val r1 = ::fun1
        val r2:(Int)->String = r1
        val r3:Int.()->String=r2

        222.method()


        222.method2{
            it
        }
    }

    private fun show(name :String="aaa", lambda : (String)->Unit){
        lambda(name)
    }

    private fun fun1(num:Int):String{
        return "111";
    }

    fun Int.method(){
        println("aaa")
    }

    fun <T> T.method2(mm:(Double)->Unit) {

    }

}