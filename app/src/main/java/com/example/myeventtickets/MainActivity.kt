package com.example.myeventtickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth= FirebaseAuth.getInstance()
        mAuthListener =FirebaseAuth.AuthStateListener {
            firebaseAuth:FirebaseAuth->

            user = firebaseAuth.currentUser
            if (user!= null){
                 var intent = Intent(this,DashBoard::class.java)
                  intent.putExtra("uid","$user")
                startActivity(intent)
                finish()
            }
            else{
                var intent2 = Intent(this,loginpage::class.java)
                startActivity(intent2)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener!=null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

}

