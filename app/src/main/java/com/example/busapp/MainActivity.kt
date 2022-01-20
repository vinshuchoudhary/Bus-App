package com.example.busapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.busapp.databinding.ActivityMainBinding
import java.util.*

const val sharedPref_KEY = "myPref"
const val dob_KEY = "myDOB"
const val gender_KEY = "myGender"
const val DATE_KEY = "myDate"
const val MONTH_KEY = "myMonth"
const val YEAR_KEY = "myYear"

class MainActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener{

    lateinit var binding: ActivityMainBinding
    var dobString = "30/04/2001"
    private var userDate : Int = 1
    private var userMonth : Int = 1
    private var userYear : Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val share = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = share.edit()


        if(share.getString(gender_KEY,"trans")!="trans"){
            startActivity(Intent(this,mainScreen::class.java))
            finish()
        }

        binding.dobButton.setOnClickListener {
            showDatePickerDialog()
        }
        binding.chipGroup.selectableAmount=1

        binding.nextBtn.setOnClickListener {

            editor.apply{
                var gender: String = "trans"
//                when(binding.chipGrp.checkedChipId){
//                    R.id.male -> gender = binding.male.text.toString()
//                    R.id.female -> gender = binding.female.text.toString()
//                }
                if(binding.male.isSelected)gender = binding.male.text
                else gender = binding.female.text
                remove(gender_KEY)
                remove(DATE_KEY)
                remove(MONTH_KEY)
                remove(YEAR_KEY)
                putString(gender_KEY,gender)
                putInt(DATE_KEY,userDate)
                putInt(MONTH_KEY,userMonth)
                putInt(YEAR_KEY,userYear)
                apply()
            }

            startActivity(Intent(this,mainScreen::class.java))
            finish()
        }

    }

    fun showDatePickerDialog() {
        val datePickerDialog: DatePickerDialog = DatePickerDialog(
            this,R.style.DialogTheme, this,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        dobString ="" + dayOfMonth +"/" + month+"/"+year

        userDate = dayOfMonth
        userMonth = month
        userYear = year

    }

}