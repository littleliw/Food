package com.example.jack.food

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.jack.food.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.IOException

class Setting: AppCompatActivity(), View.OnClickListener {

    private var filePath: Uri? = null;
    private var storage: FirebaseStorage? = null;
    private var storageReference: StorageReference? = null;
    private val IMAGE_REQUEST = 1234; //any number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;

        btn_choose.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        if(v === btn_choose){
            showFileChooseBtn();
        }else if(v === btn_upload){
            uploadFile();
        }else{}
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filePath = data.data;
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath);
                imageView2.setImageBitmap(bitmap);
            }catch (e: IOException){
                e.printStackTrace();
            }
        }
    }
}
