package com.example.busapp.objects

import com.example.busapp.busDataObject

object FindingBusObject {
    var busNumbers : ArrayList<String> = ArrayList()
    fun findingBus(a : String , b: String){
        for((k,v) in busDataObject.arr){
            var count = 0;
            for( i in v){
                if(i == a || i == b){
                    count++
                    if(count>1){
                        busNumbers.add(k)
                        break
                    }
                }
            }
        }
    }

}