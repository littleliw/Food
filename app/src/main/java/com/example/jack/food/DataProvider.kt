package com.example.jack.food

import android.support.annotation.Keep

object DataProvider {
    private  val data = ArrayList<KeepFoodObject>()
    fun getData(): ArrayList<KeepFoodObject>
    {return data}

    fun adddata(name:String,restaurant:String){

        data.add(KeepFoodObject(name,restaurant))
    }


}