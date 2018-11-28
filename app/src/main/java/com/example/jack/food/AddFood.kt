package com.example.jack.food

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_add_food.*
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.BitmapDrawable
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase





class AddFood : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        var keepfood=" "

        addBtn.setOnClickListener {

            showFood.setText(food.text.toString())
            keepfood=showFood.text.toString()

            showPic.setImageBitmap((imv.getDrawable() as BitmapDrawable).bitmap)
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Name and Picture")
            myRef.setValue(keepfood)
        }
        if(!hasCamera()){
            btn_Photo.isEnabled = false;
        }
    }

    private fun hasCamera(): Boolean{
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    fun launchCamera(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras;
            val photo = extras.get("data") as Bitmap;
            imv.setImageBitmap(photo);
        }
    }
}
