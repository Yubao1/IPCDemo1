package com.xe.demo.ipcdemo1.bean
import java.io.Serializable

/**
 * Created by 86188 on 2021/6/8.
 */

class MyObject: Serializable{
    var name: String
    var age: Int
   constructor(name: String,age: Int) {
       this.name = name
       this.age = age
   }

}