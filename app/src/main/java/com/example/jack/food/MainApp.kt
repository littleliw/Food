package com.example.jack.food

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_app.*
import java.util.*


class MainApp : AppCompatActivity(), SensorEventListener{

    private var mSensorManager : SensorManager? = null
    private var lastUpdate: Long = 0

    private var color: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        check.setBackgroundColor(Color.parseColor("#FF0000"))

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lastUpdate = System.currentTimeMillis()

        /*val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Name, Restaurant/0/name")
        //myRef.setValue("Hello, World!");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                //Log.d(FragmentActivity.TAG, "Value is: " + value!!)
                check.setText(value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })*/
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

        val accel = (x*x + y*y + z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)

        val actualTime = System.currentTimeMillis()
        if(accel>=2){
            if((actualTime - lastUpdate) < 200){
                return
            }
            lastUpdate = actualTime
            Toast.makeText(this, "Device was shaked", Toast.LENGTH_SHORT).show()
            val random = Random()

            check.setText((Random().nextInt(89)+10).toString())

            if(color){
                check.setBackgroundColor(Color.parseColor("#FF0000"))
            }
            else{
                check.setBackgroundColor(Color.parseColor("#FAC445"))
            }
            color = !color
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    fun Random.nextInt(range: IntRange): Int{
        return range.start + nextInt(range.last - range.start)
    }

}
