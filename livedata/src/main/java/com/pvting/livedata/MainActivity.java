package com.pvting.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String TAG="MainActivity";

//    static class MLiveData<T> extends MutableLiveData<T> {
//        public void observe(@NonNull LifecycleOwner owner,boolean sticky, @NonNull Observer observer) {
//            super.observe(owner, wrapObserver(sticky, observer));
//
//            private EventObserverWrapper wrapObserver(Boolean sticky, Observer<? extends T> observer) Observer<T> {
//                return new EventObserverWrapper(this, sticky, observer);
//            };
//        }
//    }
//        @Override
//        public void observe(@NonNull LifecycleOwner owner,boolean sticky, @NonNull Observer observer) {
//            super.observe(owner, observer);
//        }
//        private void wrapObserver(Boolean sticky, observer: Observer<in T>): Observer<T> {
////                return EventObserverWrapper(this, sticky, observer)
//        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(getMainLooper());
        setContentView(R.layout.activity_main);

        EventLiveData<Integer> liveData = new EventLiveData<>();
        //        class EventLiveData<T> : MutableLiveData<T>() {
//
//            fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
//                observe(owner, wrapObserver(sticky, observer))
//            }
//
//            private fun wrapObserver(sticky: Boolean, observer: Observer<in T>): Observer<T> {
//                return EventObserverWrapper(this, sticky, observer)
//            }
//        }


        liveData.observe(this, false,new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e(TAG, "onChanged: ssss"+integer);
            }
        });
        liveData.setValue(1);
        liveData.setValue(2);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        liveData.observe(MainActivity.this, false,new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                Log.e(TAG, "onChanged2: ssss"+integer);

                            }
                        });
//                        liveData.setValue(3);

//                        fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
//                            observe(owner, wrapObserver(sticky, observer))
//                        }
                    }
                },1000);
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveData.setValue(100);
            }
        });

    }
}