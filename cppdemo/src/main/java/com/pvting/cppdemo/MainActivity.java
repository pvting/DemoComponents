package com.pvting.cppdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
//        tv.setText(stringFromJNI());
        int[] intArray = {2,3,4,5,6};
        String[] strArray={"aaa","bbb","ccc","ddd"};
        //各种类型的值传递，包括数组
        testAnyArgs(true,99,11.11,12.12f,9999,"你好",intArray,strArray);
        for (int i : intArray) {
            System.out.println("java _intP value:"+i);
        }
        System.out.println("value:"+strArray.length);
        System.out.println("value:"+ Arrays.toString(strArray));

        for (int i = 0; i < strArray.length; i++) {
            System.out.println("value:"+strArray.length);
            System.out.println("java _strP value:"+strArray[i]);
        }
        //在c中修改对象、调用对象的方法
        Stu stu = new Stu(12,"weiwei");
        testObject(stu,"xiaopei");
        System.out.println("#####"+stu.toString());

        reflectStaticMethod();
    }

    public static void doStatic(String str){
        System.out.println("****:"+str);
    }

    public native String stringFromJNI();

    static {
        System.loadLibrary("cppdemo");
    }

    //java的各种类型传递到C中
    public native void testAnyArgs(boolean b,int i,double d,float f,long l,String str,int[] intArray,String[] strArray);
    //传递一个对象和值，在c中修改该对象的属性值
    public native void testObject(Stu stu,String name);
    //在c中创建一个java对象，并调用其方法
    public native void createObject();
    //在c中反射一个static方法
    public native void reflectStaticMethod();

}