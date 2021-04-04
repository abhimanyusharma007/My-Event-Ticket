package com.example.myeventtickets

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.fragment_qr.*
import kotlinx.android.synthetic.main.fragment_qr.view.*
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DashBoard : AppCompatActivity() {
  // create three objects for fragment as create in java
    lateinit var homeFragment: HomeFragment
    lateinit var qrFragment: QrFragment
    lateinit var ticketsFragment: TicketsFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)



//        var data = intent.extras
//        var useruid= data!!.get("uid")




        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_nav)

        homeFragment= HomeFragment() // this is for open home fragment in starting of app ( make
        supportFragmentManager       // default when the app starts.
            .beginTransaction()
            .replace(R.id.frame_layout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()



        bottomNavigation.setOnNavigationItemSelectedListener { item->

            when(item.itemId){

                // now create three fragment
                R.id.home->{

                    homeFragment= HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout,homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()

                }

                R.id.qrcode->{

                    val executor = Executors.newSingleThreadExecutor()
                    val activity: FragmentActivity = this // reference to activity
                    val biometricPrompt = androidx.biometric.BiometricPrompt(activity, executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                                // user clicked negative button

                            } else if (errorCode==androidx.biometric.BiometricPrompt.ERROR_CANCELED){
                                finish()
                                TODO("Called when an unrecoverable error has been encountered and the operation is complete.")
                            }
                        }

                        override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)

                            var intent= Intent(this@DashBoard,QRcoder::class.java)
                            startActivity(intent)


                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()


                        }

                    })

                    val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Scan your fingerprint")
//            .setDescription("Set the description to display")
                        //.setNegativeButtonText("Negative Button")
                        .setDeviceCredentialAllowed(true)
                        .build()



                        biometricPrompt.authenticate(promptInfo)




//                    qrFragment= QrFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout,qrFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()


                }

                R.id.order->{ // order is the name of tickets navigation button id

                    ticketsFragment= TicketsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout,ticketsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()


                }
            }

            true
        }



    }




}
