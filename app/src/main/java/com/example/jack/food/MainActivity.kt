package com.example.jack.food

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_food.*
import java.io.InputStream
import java.net.URL


class MainActivity : AppCompatActivity() {

    companion object  {
        var KeepFooddata:MutableList<KeepFoodObject> = mutableListOf()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Food")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()) {
                    Log.d("keeploc","COME ON!")
                    KeepFooddata.clear()
                    for (i in dataSnapshot.children) {

                        val value = i.getValue(KeepFoodObject::class.java)
                        KeepFooddata.add(value!!)

                        Log.d("keeploc","TESTTTTTTTTT!")
                    }
                    Log.d("keeploc","DONE!!!!!!!!!!")

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })


        start.setOnClickListener {
            val intent = Intent(this,AddFood::class.java )
            startActivity(intent)
        }

        mainpage.setOnClickListener {
            val intent = Intent(this,MainApp::class.java)
            startActivity(intent)
        }

        listitem.setOnClickListener {
            val intent = Intent(this,FoodList::class.java)
            startActivity(intent)
        }
    }
}
