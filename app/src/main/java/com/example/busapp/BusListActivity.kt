package com.example.busapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.busapp.adapters.BusAdapter
import com.example.busapp.busDataObject.arr
import com.example.busapp.databinding.ActivityBusListBinding
import com.example.busapp.modelClass.BusModelClass
import com.example.busapp.objects.FindingBusObject
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BusListActivity : AppCompatActivity() {

    private lateinit var recycView: RecyclerView
    private lateinit var dbRef: DatabaseReference
    private lateinit var numberPlateList: ArrayList<BusModelClass>
    private lateinit var binding : ActivityBusListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arr = FindingBusObject.busNumbers

        binding.startStop.text = mainScreen.source
        binding.endStop.text = mainScreen.destination

        recycView = findViewById(R.id.busRecView)
        numberPlateList = ArrayList()
        recycView.layoutManager = LinearLayoutManager(this)
        recycView.setHasFixedSize(true)
        recycView.adapter = BusAdapter(this@BusListActivity, arr, mainScreen.busList)


    }

}