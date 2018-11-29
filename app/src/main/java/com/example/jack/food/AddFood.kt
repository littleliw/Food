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
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_setting.*


class AddFood : AppCompatActivity() {
    private var filePath: Uri? = null;
    private var storage: FirebaseStorage? = null;
    private var storageReference: StorageReference? = null;
    private val IMAGE_REQUEST = 1234; //any number
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        var keepfood=" "
        var keepres=" "

        addBtn.setOnClickListener {

            showFood.setText(restaurant.text.toString())
            keepfood=fooddd.text.toString()
            keepres=restaurant.text.toString()

            val keeploc="https://firebasestorage.googleapis.com/v0/b/food-73d82.appspot.com/o/burger.jpg?alt=media&token=173f92ce-e45f-4597-bf27-a0e9918b1526"
            DataProvider.adddata(keepfood,keepres,keeploc)
            showPic.setImageBitmap((imv.getDrawable() as BitmapDrawable).bitmap)

            val image= (imv.getDrawable() as BitmapDrawable).bitmap
            val database = FirebaseDatabase.getInstance()

            val myRef = database.getReference("Data")
            myRef.child("Food").setValue(DataProvider.getData())

        }


        if(!hasCamera()){
            btn_Photo.isEnabled = false;
        }
    }
    override fun onClick(v: View?) {
        if(v === choosebtn){
            showFileChooseBtn();
        }else if(v === addBtn){
            uploadFile();
        }else{}
    }

    private fun hasCamera(): Boolean{
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    fun launchCamera(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }
    private fun showFileChooseBtn(){
        val intent = Intent();
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_REQUEST);
    }

    private fun uploadFile(){
        if(filePath != null){
            Toast.makeText(applicationContext, "Uploading...", Toast.LENGTH_SHORT).show();
            val imageRef = storageReference!!.child(edt_foldername.text.toString() + "/" + edt_filename.text.toString());
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras;
            val photo = extras.get("data") as Bitmap;
            imv.setImageBitmap(photo);

        }
    }

}
