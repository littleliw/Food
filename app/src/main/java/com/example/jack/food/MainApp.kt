package com.example.jack.food

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Keep
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.jack.food.MainActivity.Companion.KeepFooddata
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_food.view.*
import kotlinx.android.synthetic.main.activity_main_app.*


class MainApp : AppCompatActivity(), SensorEventListener {

    private var mSensorManager : SensorManager ?= null
    private var lastUpdate : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lastUpdate = System.currentTimeMillis()


        val k=KeepFooddata.size
        Log.d("numbers",k.toString())




    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
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
