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


        Picasso.with(applicationContext).load("https://firebasestorage.googleapis.com/v0/b/food-73d82.appspot.com/o/burger.jpg?alt=media&token=173f92ce-e45f-4597-bf27-a0e9918b1526").into(imageView3)


        start.setOnClickListener {
            val intent = Intent(this,AddFood::class.java )
            startActivity(intent)
        }

        setting.setOnClickListener {

            val intent = Intent(this, Setting::class.java)
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

    fun getBitmap() {


        val imageUrl:URL = URL("gs://food-73d82.appspot.com/burger.jpg")

        val `in` = imageUrl.openStream()
        val bmp:Bitmap = BitmapFactory.decodeStream(`in`)

        imageView3.setImageBitmap(bmp)
        Picasso.with(applicationContext).load(R.drawable.abc_ab_share_pack_mtrl_alpha).into(imageView3)
imageView3.setImageResource(R.drawable.abc_ab_share_pack_mtrl_alpha)
    }
}
