package com.pvting.democomponents

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pvting.democomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

        val finalPassedCount = MutableLiveData<Int>()
        finalPassedCount.value = 1
//        finalPassedCount.observe(this, Observer {
//            Log.d("TAG111", "onCreate: "+it)
//        })

        Thread(){
            run {
                SystemClock.sleep(1000)
                runOnUiThread(Runnable{
                    finalPassedCount.observe(this, Observer {
                        Log.d("TAG111", "onCreate2: "+it)
                    })
                })

            }
        }.start()

    }

    /**
     * A native method that is implemented by the 'democomponents' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'democomponents' library on application startup.
        init {
            System.loadLibrary("democomponents")
        }
    }
}