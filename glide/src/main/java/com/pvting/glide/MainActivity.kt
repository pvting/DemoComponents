package com.pvting.glide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val TAG="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var iv = findViewById<ImageView>(R.id.iv)

        thread {
            val requestManager =  Glide.with(this)
            val requestBuilder = requestManager.load("https://zrzy.wuxi.gov.cn/uploadfiles/202207/26/202207261648068351500.jpg")
//            Glide.with(this).load("https://zrzy.wuxi.gov.cn/uploadfiles/202207/26/202207261648068351500.jpg").into(iv)
            runOnUiThread {
                requestBuilder.into(iv)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }
}