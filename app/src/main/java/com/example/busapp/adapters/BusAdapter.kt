package com.example.busapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.busapp.BusListActivity
import com.example.busapp.R
import com.example.busapp.SeatActivity
import com.example.busapp.modelClass.BusModelClass
import com.google.firebase.database.DatabaseReference

const val ROUTE_KEY = "routeKey"
const val LAST_STOP_KEY = "lastStopKey"
const val BUS_NUMBER_KEY = "busNumberKey"

private lateinit var dbRef : DatabaseReference

class BusAdapter(
    val context: BusListActivity,
    val arr: ArrayList<String>,
    var numberPlateList: ArrayList<BusModelClass>,
) : RecyclerView.Adapter<BusAdapter.BusViewHolder>() {
    var list = numberPlateList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {


        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bus_item_list,parent,false)
        return BusViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {

        val obj = list[position]
            holder.busNumber.text = "DL1LZ" + obj.busNumber
            holder.routeNum.text = obj.route

        holder.card.setOnClickListener {
            val intent = Intent(context, SeatActivity::class.java)
            intent.putExtra(ROUTE_KEY,holder.routeNum.text.toString())
            intent.putExtra(LAST_STOP_KEY,holder.lastStop.text.toString())
            intent.putExtra(BUS_NUMBER_KEY,obj.busNumber)
            holder.card.context.startActivity(Intent(intent))
        }

    }

    override fun getItemCount(): Int {
        Log.d("vinshu","adapter size is ${numberPlateList.size}")
        return numberPlateList.size
    }

    class BusViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){


        val card = itemView.findViewById<CardView>(R.id.busCard)
        val routeNum = itemView.findViewById<TextView>(R.id.routeNumber)
        val lastStop = itemView.findViewById<TextView>(R.id.lastStop)
        val busNumber = itemView.findViewById<TextView>(R.id.busNumber)

    }

}