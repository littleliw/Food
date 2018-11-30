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
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.jack.food.MainActivity.Companion.KeepFooddata
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import android.R.attr.data
import java.net.URL


class AddFood : AppCompatActivity()  {

    private var storage: FirebaseStorage? = null;
    private var storageReference: StorageReference? = null;
    private var filePath: Uri? = null;
    private val IMAGE_REQUEST = 1234; //any number
    private var downloadUrl= ""
    var dUrl:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        var keepfood=" "
        var keepres=" "


        addBtn.setOnClickListener {

            //showFood.setText(restaurant.text.toString())
            keepfood=fooddd.text.toString()
            keepres=restaurant.text.toString()
            val database = FirebaseDatabase.getInstance()
            var keeploc=" "
            //DataProvider.adddata(keepfood,keepres,keeploc)
            //showPic.setImageBitmap((imv.getDrawable() as BitmapDrawable).bitmap)

            val image= (imv.getDrawable() as BitmapDrawable).bitmap
            /////////////////////////////////////


           /*

            val url=imageRef.getDownloadUrl().toString()
            keeploc=url*/
            val storageRef = storage!!.getReferenceFromUrl("gs://food-73d82.appspot.com")
            val mountainsRef = storageRef.child("img/"+keepfood +" "+keepres)
            val mountainImagesRef = storageRef.child("images/mountains.jpg")

            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false





            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            //Uploading to the Storage + Get URL Download
            val uploadTask = mountainsRef.putBytes(data).addOnSuccessListener () { taskSnapshot ->
                // success
                Toast.makeText(applicationContext, "Success...", Toast.LENGTH_SHORT).show();
                mountainsRef.downloadUrl.addOnCompleteListener() { taskSnapshot ->

                    dUrl = taskSnapshot.result.toString()
                    Log.d("keeploc", dUrl)
                    Toast.makeText(applicationContext, "Complete..", Toast.LENGTH_SHORT).show();

                    //Store values to the Firebase Database
                    val myRef = database.getReference("Food/"+ " "+keepfood+" "+keepres)
                    val data2 = KeepFoodObject(keepfood,keepres,dUrl)
                    Log.d("keeploc","Work")
                    myRef.setValue(data2)
                    uploadFile()
                }
            }


            ////////////////////////////////////////


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
            Toast.makeText(applicationContext, "Upload Succeeded...", Toast.LENGTH_SHORT).show();




/*
            if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
                Toast.makeText(applicationContext, "Test...", Toast.LENGTH_SHORT).show();
                filePath = data.data;
                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath);
                    imageView2.setImageBitmap(bitmap);
                }catch (e: IOException){
                    e.printStackTrace();
                }
            }
            */

        }
    }
    private fun uploadFile(){

        if(filePath != null){
            Toast.makeText(applicationContext, "Uploading...", Toast.LENGTH_SHORT).show();
            val imageRef = storageReference!!.child(fooddd.text.toString() + "/" + restaurant.text.toString());
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Failed...", Toast.LENGTH_SHORT).show();
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 + taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount;
                    Toast.makeText(applicationContext, "Uploaded " + progress.toInt() + "% ...", Toast.LENGTH_SHORT).show();
                }
        }

    }
}
