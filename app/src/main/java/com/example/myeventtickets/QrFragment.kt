package com.example.myeventtickets


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color

import android.hardware.biometrics.BiometricPrompt
import android.media.MediaCodec
import android.nfc.Tag
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_qr.*
import java.lang.Exception

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_qr.view.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * A simple [Fragment] subclass.
 */
class QrFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_qr, container, false)
        val executor = Executors.newSingleThreadExecutor()
        val activity: FragmentActivity = getActivity()!! // reference to activity
        val biometricPrompt = androidx.biometric.BiometricPrompt(activity, executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    TODO("Called when an unrecoverable error has been encountered and the operation is complete.")
                }
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                var mAuth = FirebaseAuth.getInstance()
                var currentuser = mAuth.currentUser
                var uid = currentuser!!.uid

                try {
                    val encoder = BarcodeEncoder()
                    val bitmap = encoder.encodeBitmap(uid,BarcodeFormat.QR_CODE,400,400)

                    var qrimage = view.findViewById<ImageView>(R.id.qrcodeid)
                    qrimage.setImageBitmap(bitmap)
                } catch (e:Exception){
                    e.printStackTrace()
                }

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                TODO("Called when a biometric is valid but not recognized.")
            }
        })

        val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
           .setTitle("Set the title to display.")
           .setSubtitle("Set the subtitle to display.")
//            .setDescription("Set the description to display")
            //.setNegativeButtonText("Negative Button")
            .setDeviceCredentialAllowed(true)
            .build()


        view.btnqr.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }


        return view

    }







}
