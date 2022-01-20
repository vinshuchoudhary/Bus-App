package com.example.busapp.modelClass

data class DatabaseModelClass(
    val destination : String = "",
    val source : String = "",
    val routeNumber : String = "",
    val LastStop : String = "",
    val isScanned : Boolean = false

)