package com.example.myeventtickets

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_eventinfo.*
import java.util.HashMap

class eventinfotwo : AppCompatActivity() {
    var mDatabase: DatabaseReference? =null
    var eventaddress:Any?= null
    var eventcity:Any?= null
    var eventstates:Any?= null
    var eventpin:Any?= null
    var eventdesc:Any?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventinfotwo)
        var eventdata = intent.extras
        var eventid = eventdata!!.get("eventkey2").toString()
        var eventtitle = eventdata.get("eventtile2")
        var eventimage = eventdata.get("eventimage2")
        var eventprice = eventdata.get("eventprice2")
        var eventdate= eventdata.get("eventdate2")
        var eventtime = eventdata.get("eventtime2")
        var eventstate=eventdata.get("eventstate2")

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
                citytextView.setText("$eventcity ,sv d")
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






        }
    }

