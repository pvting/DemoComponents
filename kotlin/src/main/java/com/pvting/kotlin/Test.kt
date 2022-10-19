package com.pvting.kotlin

import java.security.acl.Owner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main(){
    //不支持隐式转换
    var age:Double = 100.toDouble()
    println(age)
    //高级字符串
    var str="""
        你好哈哈哈
        呵呵呵呵
    """.trimIndent()
    println(str)
    //数组和集合
    var array = arrayOf(1,2,3,4)
    var list = listOf("e1","e2")
    println(array[0])
    println(list[1])
    //创建一个无元素的数组
    var array2 = arrayOfNulls<String>(1000)
    println(array2.size)
    //判空
    var str2:String? = null
    println(str2?.length ?: -1)
    //循环和遍历
    for(i in array){
        println(i)
    }
    for (i in 100..103){
        println(i)
    }
    for (i in 106 downTo 100 step 3){
        println(i)
    }

    val method01 = {}

    var a:Any = "aaa"

    //lambda入门
    val method1 :(String) -> Unit = {
        str:String ->
            println(str)

    }

    fun method2(str:String,methodx:(String)->Unit){
        methodx(str)
    }

    method2("普通输出：你好",method1)

    //简化写法, method2不动
    fun method12(str:String,methodx:(String)->Unit){
        methodx(str)
    }
    //开始调用
    method12("lambda：niahao"){
        println(it)
    }

    //对象的委托 1个接口，一个被代理的类对象，一个委托类
    class Keyboard : IUSB{
        override fun insert() {
            println("代理、委托：键盘插入了")
        }
    }
    class Agent(iusb: IUSB):IUSB by iusb

    //这里开始调用
    Agent(Keyboard()).insert()

    //成员的委托
    class AClass{
        var num : Int? = 1
            set(value) {
                field=value
                println("set:"+value)
            }
            get() {
                println("get:"+field)
                return field
            }
        var num2 : Int? by :: num
        val num3 : Int? by lazy{
            println("计算中")
            1
        }
    }
    var cc = AClass();
    cc.num2 = 2
    println(cc.num2)
    println(cc.num)
    //lazy 委托
    val ac = AClass()
    println(ac.num3)
    println(ac.num3)
    println(ac.num3)

    //kotlin自定义委托
    val owner = com.pvting.kotlin.Owner()
    owner.text = "abc"
    println("自定义委托："+owner.text)
    //使用ReadWriteProperty实现更简单的写法
    owner.text2 = "def"
    println("自定义委托简化版本："+owner.text2)

    //委托提供者
    println("=======")
    val owner2 = com.pvting.kotlin.Owner2()
//    owner2.text="xyz"
    println(owner2.text)
    println(owner2.text2)
        owner2.text="xyz"
        owner2.text2="mnk"
    println(owner2.text)
    println(owner2.text2)

    //私有化成员，不让外部修改
    val simple3 = Simple3()
//    simple3.num =1

}

interface IUSB{
    fun insert()
}

class Owner{
    var text:String by Simple()
    var text2:String by Simple2()
}

class Owner2{
    var text:String by SmartDelegator()
    var text2:String by SmartDelegator()
}

class Simple{
    private var str:String="Default"
    operator fun getValue(owner: com.pvting.kotlin.Owner,property:KProperty<*>):String{
        println("getValue执行啦")
        return str;
    }
    operator fun setValue(owner: com.pvting.kotlin.Owner,property: KProperty<*>, value:String){
        str=value
        println("setValue执行啦")
    }
}

class Simple2 : ReadWriteProperty<com.pvting.kotlin.Owner,String>{
    private var str:String="Default"
    override fun getValue(thisRef: com.pvting.kotlin.Owner, property: KProperty<*>): String {
        println("getValue执行啦")
        return str;
    }

    override fun setValue(thisRef: com.pvting.kotlin.Owner, property: KProperty<*>, value: String) {
        str=value
        println("setValue执行啦")
    }
}

class SmartDelegator{
    operator fun provideDelegate(
        thisRef: com.pvting.kotlin.Owner2,
        property: KProperty<*>):ReadWriteProperty<com.pvting.kotlin.Owner2,String>{
        return if(property.name.contains("text2")) {
            StringDelegateUpadate("aaa")
        }else{
            StringDelegateUpadate("bbb")
        }
    }
}

class StringDelegateUpadate(var str:String="Default"):ReadWriteProperty<Owner2,String>{
    override fun getValue(thisRef: Owner2, property: KProperty<*>): String {
        println("2getValue执行啦")
        return str;
    }

    override fun setValue(thisRef: Owner2, property: KProperty<*>, value: String) {
        str=value
        println("2setValue执行啦")
    }
}

class Simple3{
    var num = 0
        private  set
}