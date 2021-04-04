package com.example.myeventtickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception

class QRcoder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcoder)

        var mAuth = FirebaseAuth.getInstance()
        var currentuser = mAuth.currentUser
        var uid = currentuser!!.uid

        try {
            val encoder = BarcodeEncoder()
            val bitmap = encoder.encodeBitmap(uid, BarcodeFormat.QR_CODE,400,400)

            var qrimage = findViewById<ImageView>(R.id.qrcodeid)
            qrimage.setImageBitmap(bitmap)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}
