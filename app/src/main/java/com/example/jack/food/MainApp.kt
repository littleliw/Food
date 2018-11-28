package com.example.jack.food

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Keep
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.example.jack.food.MainActivity.Companion.KeepFooddata
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_app.*


class MainApp : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Data/Food")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()) {
                    for (i in dataSnapshot.children) {
                        val value = i.getValue(KeepFoodObject::class.java)
                        KeepFooddata.add(value!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        tries.setOnClickListener {
            tries.setText(KeepFooddata.size.toString())
            tries.setText(KeepFooddata[1].restaurant.toString()+ "  "+ KeepFooddata[1].name)
        }
    }
}
