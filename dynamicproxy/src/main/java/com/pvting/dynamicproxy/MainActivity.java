package com.pvting.dynamicproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.pvting.dynamicproxy.agent.Agent;
import com.pvting.dynamicproxy.agent.IShop;
import com.pvting.dynamicproxy.agent.PenShop;
import com.pvting.dynamicproxy.retrofit.NetworkService;

import java.lang.reflect.Proxy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IShop penShop = new PenShop();
        Agent agent = new Agent(penShop);
        IShop a = (IShop) Proxy.newProxyInstance(getClassLoader(), new Class[]{IShop.class}, agent);
        Log.d(TAG, "IShop: "+a.sell());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.baidu.com/")
                .build();
        NetworkService service = retrofit.create(NetworkService.class);
        Call<Object> call = service.baiduSearch();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}