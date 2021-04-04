package com.example.myeventtickets

import android.app.ProgressDialog
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_eventinfo.*
import kotlinx.android.synthetic.main.activity_eventinfo.textView
import kotlinx.android.synthetic.main.dialog_layout.*
import java.util.HashMap
import java.util.concurrent.Executors

class eventinfo : AppCompatActivity() {
    var mDatabase: DatabaseReference? =null
    var eventaddress:Any?= null
    var eventcity:Any?= null
    var eventstates:Any?= null
    var eventpin:Any?= null
    var eventdesc:Any?= null
    var finalquantity:Any?=null
    var finalprice:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventinfo)

        var eventdata = intent.extras
        var eventid = eventdata!!.get("eventkey").toString()
        var eventtitle = eventdata.get("eventtile")
        var eventimage = eventdata.get("eventimage")
        var eventprice = eventdata.get("eventprice")
        var eventdate= eventdata.get("eventdate")
        var eventtime = eventdata.get("eventtime")
        var eventstate=eventdata.get("eventstate")

        var mAuth = FirebaseAuth.getInstance()
        var currentuser = mAuth.currentUser
        var uid = currentuser!!.uid
        var databaseReference = FirebaseDatabase.getInstance().getReference()


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                 eventaddress = dataSnapshot.child("Events").child(eventid).child("event_address").value
                 eventcity = dataSnapshot.child("Events").child(eventid).child("event_city").value
                 eventstates = dataSnapshot.child("Events").child(eventid).child("event_state").value
                 eventpin = dataSnapshot.child("Events").child(eventid).child("event_pin").value
                 eventdesc = dataSnapshot.child("Events").child(eventid).child("event_des").value


                adresstextView.setText("$eventaddress")
                citytextView.setText("$eventcity")
                statetext.setText(" $eventstates ")
                pintextView.setText("$eventpin")
                destextView.setText("$eventdesc")




            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })




        Glide.with(this)
            .load(eventimage)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(eventimageView)


        textView.setText("$eventtitle")
        eventpriceid.setText("₹$eventprice")
        datetextView.setText("Event Date: $eventdate")
        timetextView.setText("Event Time: $eventtime")
        statetextview.setText("State: $eventstate")


        bookingbtnid.setOnClickListener {

            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_layout,null)
            val picker= view.findViewById<NumberPicker>(R.id.numberpic)
            picker.minValue=1
            picker.maxValue=100
            picker.wrapSelectorWheel=false
            val pricetags =view.findViewById<TextView>(R.id.pricetag)
            pricetags.setText("₹$eventprice")

            picker.setOnValueChangedListener { picker, oldvalue, newvalue ->
                  finalprice = eventprice.toString().toInt()*newvalue
                finalquantity=newvalue

                pricetags.setText("₹$finalprice")
            }
            val ticketbooking = view.findViewById<Button>(R.id.button)

            ticketbooking.setOnClickListener {// ticket booking button bottomsheet dialog



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




                        var ticket = HashMap<String,String>()
                        ticket.put("ticket_quantity",finalquantity.toString())

                        var currentuid = FirebaseAuth.getInstance().currentUser!!.uid
                        mDatabase = FirebaseDatabase.getInstance().reference
                            .child("Tickets").child(eventid).child(currentuid)
                            .child(eventid)
                        //var eventkey = mDatabase!!.key.toString()// get uid of new create event
                        //get uid for-
                        // -current admin who upload the events

                        mDatabase!!.setValue(ticket).addOnCompleteListener {
                                task: Task<Void> ->
                            if(task.isSuccessful){
                                var userObject = HashMap<String,String>()
                                userObject.put("event_image",eventimage.toString())
                                userObject.put("event_title",eventtitle.toString())
                                userObject.put("event_price",eventprice.toString())
                                userObject.put("event_time",eventtime.toString())
                                userObject.put("event_date",eventdate.toString())
                                userObject.put("event_address",eventaddress.toString())
                                userObject.put("event_state",eventstate.toString())
                                userObject.put("event_city",eventcity.toString())
                                userObject.put("event_pin",eventpin.toString())
                                userObject.put("event_des",eventdesc.toString())
                                FirebaseDatabase.getInstance().reference
                                    .child("User-Ticket-view").child(currentuid).child("usertickets")
                                    .child(eventid).setValue(userObject)// it create separate node of admin view


                                Toast.makeText(this@eventinfo,"Your ticket is Booked", Toast.LENGTH_LONG)
                                    .show()

                                var intent = Intent(this@eventinfo,DashBoard::class.java)
                                startActivity(intent)



                            }else{
                                Toast.makeText(this@eventinfo,"Your ticket is not booked", Toast.LENGTH_LONG)
                                    .show()

                            }

                        }


                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()



                    }

                })

                val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Verify purchase")
                    .setDescription("Total amount: ₹$finalprice")
                    //.setNegativeButtonText("Negative Button")
                    .setDeviceCredentialAllowed(true)
                    .build()



                biometricPrompt.authenticate(promptInfo)



            }
            dialog.setContentView(view)
            dialog.show()




        }
    }
}
