package com.example.busapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.log

class BottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val share = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = share.edit()
        val dateOfBirth = view.findViewById<TextView>(R.id.dob)
        val aboutGender = view.findViewById<TextView>(R.id.gender)
        val genderImg = view.findViewById<ImageView>(R.id.genderImg)
        val logOutBtn = view.findViewById<Button>(R.id.logOut)

        logOutBtn.setOnClickListener {

            editor.apply{
                remove(gender_KEY)
                remove(DATE_KEY)
                remove(MONTH_KEY)
                remove(YEAR_KEY)
                putString(gender_KEY,"trans")
            }.apply()

            startActivity(Intent(requireContext(),MainActivity::class.java))
            requireActivity().finish()
        }

        dateOfBirth.apply {
            val date = sharedPref.getInt(DATE_KEY,1)
            val month = sharedPref.getInt(MONTH_KEY,1) + 1
            val year = sharedPref.getInt(YEAR_KEY,2000)
            text = ""+date + "/" +month + "/" + year
        }
        aboutGender.text = sharedPref.getString(gender_KEY,"Transgender")
        if(aboutGender.text.toString() == "Male"){
            genderImg.setImageResource(R.drawable.male_icon)
        }else{
            genderImg.setImageResource(R.drawable.female_icon)
        }

    }

}