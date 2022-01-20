package com.example.busapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.busapp.adapters.BUS_NUMBER_KEY
import com.example.busapp.adapters.LAST_STOP_KEY
import com.example.busapp.adapters.ROUTE_KEY
import com.example.busapp.databinding.ActivitySeatBinding
import com.example.busapp.modelClass.DatabaseModelClass
import com.google.firebase.database.*
import java.util.*

class SeatActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatBinding
    lateinit var imgView: ImageView
    lateinit var selectedSeat: ImageView
    var count = 1;
    private lateinit var combString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val destination = mainScreen.destination
        val source = mainScreen.source
        val routeNum = intent.getStringExtra(ROUTE_KEY) ?: "NULL"
        val lastStop = intent.getStringExtra(LAST_STOP_KEY) ?: "NULL"
        val busNumber = intent.getStringExtra(BUS_NUMBER_KEY) ?: "NULL"

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val date = sharedPref.getInt(DATE_KEY, 1)
        val month = sharedPref.getInt(MONTH_KEY, 1)
        val year = sharedPref.getInt(YEAR_KEY, 2000)

        binding.sourceTxt.text = mainScreen.source
        binding.destTxt.text = mainScreen.destination

        val userAge = getAge(year, month, date)
        if (userAge > 65) {
            adultSeat(userAge)
            Toast.makeText(this, userAge.toString(), Toast.LENGTH_SHORT).show()
        }


        val dbRef = FirebaseDatabase.getInstance().getReference()
        imgView = findViewById(R.id.S1)

        combString = ""

        binding.bookSeatBtn.setOnClickListener {

            combString =
                source + "\n" + destination + "\n" + routeNum + "\n" + selectedSeat.tag.toString() + "\n" + busNumber
            makeQr(combString)

            dbRef.child(routeNum).child("BUSES").child(busNumber).child("OWNERS")
                .child(selectedSeat.tag.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChildren()) {
                            Toast.makeText(applicationContext, "already exists", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            dbRef.child(routeNum).child("BUSES").child(busNumber).child("SEATS")
                                .child(selectedSeat.tag.toString())
                                .setValue("TRUE")
                            dbRef.child(routeNum).child("BUSES").child(busNumber).child("OWNERS")
                                .child(selectedSeat.tag.toString()).setValue(
                                    DatabaseModelClass(
                                        destination,
                                        source,
                                        routeNum,
                                        lastStop,false
                                    )
                                )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
            finish()


        }

        dbRef.child(routeNum).child("BUSES").child(busNumber).child("SEATS")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val idArr = arrayOf(
                        R.id.S1,
                        R.id.S2,
                        R.id.S3,
                        R.id.S4,
                        R.id.S5,
                        R.id.S6,
                        R.id.S7,
                        R.id.S8,
                        R.id.S9,
                        R.id.S10,
                        R.id.S11,
                        R.id.S12,
                        R.id.S13,
                        R.id.S14,
                        R.id.S15,
                        R.id.S16,
                        R.id.S17,
                        R.id.S18,
                        R.id.S33,
                        R.id.S34,
                        R.id.S35,
                        R.id.S36,
                    )

                    for (i in idArr) {
                        val img: ImageView = findViewById(i)
                        if (img.tag == snapshot.key.toString()) {
                            if (snapshot.value.toString() == "TRUE") {
                                img.setImageResource(R.drawable.booked_img)
                            } else {
                                img.setImageResource(R.drawable.available_img)
                            }
                        }
                    }

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                    Log.d(
                        "vinshu",
                        "key is " + snapshot.key + snapshot.getValue(String::class.java).toString()
                    )

                    if (snapshot.value.toString() == "TRUE") {
                        imgView.setImageResource(R.drawable.booked_img)
                    } else {
                        imgView.setImageResource(R.drawable.available_img)
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

    private fun adultSeat(age: Int) {

        val seat33: ImageView = findViewById(R.id.S33)
        val seat34: ImageView = findViewById(R.id.S33)
        val seat35: ImageView = findViewById(R.id.S33)
        val seat36: ImageView = findViewById(R.id.S33)

        seat33.isEnabled = true
        seat34.isEnabled = true
        seat35.isEnabled = true
        seat36.isEnabled = true

    }

    fun seatClickedFun(view: android.view.View) {

        when (view.id) {

            R.id.S1 -> imgView = findViewById(R.id.S1)
            R.id.S2 -> imgView = findViewById(R.id.S2)
            R.id.S3 -> imgView = findViewById(R.id.S3)
            R.id.S4 -> imgView = findViewById(R.id.S4)
            R.id.S5 -> imgView = findViewById(R.id.S5)
            R.id.S6 -> imgView = findViewById(R.id.S6)
            R.id.S7 -> imgView = findViewById(R.id.S7)
            R.id.S8 -> imgView = findViewById(R.id.S8)
            R.id.S9 -> imgView = findViewById(R.id.S9)
            R.id.S10 -> imgView = findViewById(R.id.S10)
            R.id.S11 -> imgView = findViewById(R.id.S11)
            R.id.S12 -> imgView = findViewById(R.id.S12)
            R.id.S13 -> imgView = findViewById(R.id.S13)
            R.id.S14 -> imgView = findViewById(R.id.S14)
            R.id.S15 -> imgView = findViewById(R.id.S15)
            R.id.S16 -> imgView = findViewById(R.id.S16)
            R.id.S17 -> imgView = findViewById(R.id.S17)
            R.id.S18 -> imgView = findViewById(R.id.S18)
            R.id.S33 -> imgView = findViewById(R.id.S33)
            R.id.S34 -> imgView = findViewById(R.id.S34)
            R.id.S35 -> imgView = findViewById(R.id.S35)
            R.id.S36 -> imgView = findViewById(R.id.S36)
        }

        if (imgView.drawable.constantState == this.resources.getDrawable(R.drawable.booked_img).constantState) {
            Toast.makeText(this, "this seat is already booked", Toast.LENGTH_SHORT).show()
        } else {

            if (imgView.drawable.constantState == this.resources.getDrawable(R.drawable.your_seat_img).constantState) {
                count--
                imgView.setImageResource(R.drawable.available_img)
            } else {


                if (count <= 1) {
                    selectedSeat = imgView
                    imgView.setImageResource(R.drawable.your_seat_img)
                    count++
                } else {
                    Toast.makeText(this, "u already selected a seat", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    private fun getAge(year: Int, month: Int, day: Int): Int {
        val dob: Calendar = Calendar.getInstance()
        val today: Calendar = Calendar.getInstance()
        dob.set(year, month, day)
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age
        return ageInt
    }

    companion object {
        var encoder = QRGEncoder("my name is vinshu", null, QRGContents.Type.TEXT, 800)
        fun makeQr(comb: String) {
            encoder = QRGEncoder(comb, null, QRGContents.Type.TEXT, 800)
        }

    }

}