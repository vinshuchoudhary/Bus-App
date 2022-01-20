package com.example.busapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.busapp.databinding.ActivityMainScreenBinding
import com.example.busapp.modelClass.BusModelClass
import com.example.busapp.objects.FindingBusObject
import com.google.firebase.database.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class mainScreen : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding
    val stops = arrayOf(
        "Anand Vihar ISBT",
        "Ram Prastha",
        "Ram Prastha Temple",
        "Surya Nagar",
        "Shahdra Border",
        "Jhilmil X-Ing",
        "Dilshad Garden GT Road",
        "Dilshad Garden School",
        "GTB Hospital",
        "GTB Hospital X-Ing",
        "Nand Nagri Terminal",
        "Nand Nagari E-Block",
        "Gagan Cinema",
        "Bank Colony",
        "Mandoli Chungi",
        "Ashok Nagar",
        "MIG Flats Loni Road",
        "Loni Road X-Ing",
        "Yamuna Vihar C-4 Block",
        "Yamuna Vihar Brijpuri",
        "Yamuna Vihar Block C-1",
        "Bhajan Pura",
        "Khajoori Khas",
        "Rajiv Vihar",
        "Nanak Sar",
        "Wazirabad Bridge",
        "Jagatpur X-Ing",
        "Gandhi Vihar",
        "Burari X-ing",
        "Mukund Pur X-ing",
        "Jahanhgir Puri X-ing",
        "Bhalaswa X-ing",
        "Outer Ring Road Makbara Chowk",
        "GTK By Pass",
        "Sanjay Ghandhi Transport Nagar",
        "Samay Pur",
        "Badli Railway Staion",
        "Rohini Sector-18",
        "Rohini Sector-16",
        "Rohini Sector-16 X-Ing",
        "Delhi Engineering College",
        "Shahbad Daulat Pur",
        "St Micle School",
        "Shivaji Stadium Terminal",
        "Super Bazar",
        "Govt Press",
        "DDU Marg",
        "Govt Sr.Sec. School",
        "Bal Bhawan",
        "ITO",
        "Delhi Secretarait",
        "Ramesh Park",
        "Bank Enclave",
        "Sahstri Nagar Pushta",
        "Laji enclave",
        "Chacha Nehru Hospital",
        "Gurudwara Jheel Colony",
        "Jheel Dispensary",
        "Jheel Terminal",
        "Lala Quarter",
        "Azad Nagar Mod",
        "Shyam Lal College",
        "Nand Nagari Terminal",
        "GTB X-ing",
        "Nand Nagari C Block",
        "Jagat Puri",
        "Jyoti Colony",
        "Durga Puri",
        "Babar Pur",
        "Mog Pur Crossing",
        "Zafrabad School",
        "Selam Pur",
        "Welcome Colony",
        "Jharkhandi",
        "Azad Nagar",
        "Krishna Nagar",
        "Hans Apartment",
        "Radhe Puri",
        "Jagat Puri A Block",
        "Jagat Puri F Block",
        "KK X-ing",
        "New Rajdhani State",
        "Sawath Vihar",
        "Nirman Vihar",
        "Shankar Mode",
        "Shakarpur",
        "Lakshmi Nagar",
        "Rainy Well",
        "Delhi Secretiriat",
        "IP Power Station",
        "IP Depot",
        "Railway X-ing",
        "Pargati Madain",
        "National Stadium",
        "Zoo",
        "Sunder Nagar",
        "DPS School",
        "Obrai Hotel",
        "Lodhi Hoetl",
        "Panth Nagar",
        "Deffence Colony",
        "Mool Chand Hospital",
        "Central School",
        "LSR College",
        "Kailash Colony",
        "Sant Nagar",
        "Nehru Place",
        "Paras Cinema",
        "NARELA",
        "MCD Primary School Mandi Narela",
        "NARELA MANDI",
        "RLY XING NARELA",
        "Vishal Bagh",
        "Swatantra Nagar",
        "Bharat Mata Public School",
        "SANOTH XING",
        "GHOGA AIRFORCE STN.",
        "Bawana",
        "BAWANA SCHOOL",
        "Industrial Area Bawana",
        "Maharshi Balmiki Government Hospital",
        "POOTH KHURD",
        "ASTHAL POOTH",
        "Barwala",
        "BARWALA PATHSHALA",
        "Prahlad Pur",
        "PREHLAD PUR SCHOOL",
        "Shahbad Dairy Terminal",
        "Shahbad Dairy",
        "ST. MICLE SCHOOL",
        "SHAHBAD DAULAT PUR",
        "ENG. COLLEGE",
        "Rohoni Sector 16",
        "ROHINI SEC-15.16 XING",
        "Rohoni Sector 11",
        "ROHINI SECT-D-2",
        "VIDYA VIHAR MARG XING",
        "Rohini Sector-7 & 8 X-Ing",
        "Fire Station Sector-8",
        "Madhuvan Chowk",
        "SARSWATI VIAHR C-BLOCK",
        "Dipali Chowk",
        "kali Mandir",
        "Pushpanjali",
        "Rohini Depot III",
        "MANGOL PUR SCHOOL",
        "West Enclave",
        "MANGOL PURI B- BLK",
        "Peera Garhi Chowk",
        "Peera Garhi Depot",
        "POWER HOUSE",
        "RBI Colony",
        "Sundar Vihar",
        "Meera Bagh",
        "Meera Bagh Appartment",
        "KESHO PUR DEPOT",
        "MJR. BHUPENDER SINGH NGR",
        "CRPF Camp",
        "KRISHANA PURI",
        "Kangra Niketan",
        "District Centre",
        "Dholi Piao",
        "VIKAS PURI XING",
        "ISBT Maharana Pratap Bus (T)",
        "ISBT Kashmiri Gate",
        "Shyam Giri Mandir",
        "Shastri Park",
        "Dharam Pura",
        "Seelam Pur",
        "Welcome",
        "Kanti Nagar",
        "East Azad Nagar",
        "Swarn Cinema",
        "East Krishna Nagar",
        "Hans Appartment",
        "Arjun Nagar",
        "Radhey Puri",
        "Jagatpuri A-Block",
        "Jagatpuri F-Block",
        "Hasanpur Depot",
        "Hasanpur Village",
        "Gazipur Depot"
    )
    private lateinit var dbRef: DatabaseReference
    private lateinit var numberPlateList: ArrayList<BusModelClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, stops)

        numberPlateList = ArrayList()

        binding.sourceEt.setAdapter(arrayAdapter)
        binding.destinationEt.setAdapter(arrayAdapter)

        val bottomSheetFragment = BottomSheetFragment()
        numberPlateList.clear()
        busList.clear()
        binding.searchBuses.setOnClickListener {
            val intent = Intent(this, BusListActivity::class.java)
            val destination = binding.destinationEt.text.toString()
            val source = binding.sourceEt.text.toString()

            FindingBusObject.busNumbers.clear()
            numberPlateList.clear()

            FindingBusObject.findingBus(source, destination)

            var count = 0
            val arr = FindingBusObject.busNumbers
                    for (i in arr) {
                        dbRef = FirebaseDatabase.getInstance().reference
                        dbRef.child(i).child("BUSES")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (ds in snapshot.children) {
                                        Log.d("vinshu", numberPlateList.size.toString())
                                        numberPlateList.add(BusModelClass(ds.key.toString(),i))
                                        count++
                                        run loop@{
                                            if(numberPlateList.size<arr.size*3)return@loop
                                            else{
                                                mapChange(numberPlateList)
                                                valueChange(destination, source)
                                                startActivity(intent)
                                            }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })


            }

        }

        binding.aboutSection.setOnClickListener {

            bottomSheetFragment.show(supportFragmentManager, "bottomSheetDialog")

        }

    }

    companion object {
        var destination: String = "london"
        var source: String = "sydney"
        var busList: ArrayList<BusModelClass> = ArrayList()

        fun mapChange(numberPlateList: ArrayList<BusModelClass>){
            busList = numberPlateList
        }

        fun valueChange(a: String, b: String) {
            destination = a
            source = b
        }
    }

}