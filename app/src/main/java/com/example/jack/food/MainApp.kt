package com.example.jack.food

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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


class MainApp : AppCompatActivity(), SensorEventListener {

    private var mSensorManager : SensorManager ?= null
    private var lastUpdate : Long = 0

    private var color : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lastUpdate = System.currentTimeMillis()

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


        /*tries.setOnClickListener {
            tries.setText(KeepFooddata.size.toString())
            tries.setText(KeepFooddata[1].name)
            restaurantdisplay.setText(KeepFooddata[1].restaurant)*/
        }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event)
        }
    }

    private fun getAccelerometer(event: SensorEvent){
        val values = event.values

        val x = values[0]
        val y = values[1]
        val z = values[2]

        val accel = ((x*x) + (y*y) + (z*z))/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)

        val actualTime = System.currentTimeMillis()

        if(accel >= 2){
            if((actualTime - lastUpdate) < 200){
                return
            }

            //TODO: SHOW PICTURES
            imageView1.setImageResource(R.drawable.start)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

}
