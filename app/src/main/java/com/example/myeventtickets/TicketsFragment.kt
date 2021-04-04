package com.example.myeventtickets


import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tickets.*

/**
 * A simple [Fragment] subclass.
 */
class TicketsFragment : Fragment() {
    var mEventDatbase: DatabaseReference?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       // var mAuth = FirebaseAuth.getInstance()
       // var currentuser = mAuth.currentUser


        return inflater.inflate(R.layout.fragment_tickets, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        var mAuth = FirebaseAuth.getInstance()
        var currentuser = mAuth.currentUser
        var uid = currentuser!!.uid

        mEventDatbase = FirebaseDatabase.getInstance().reference.child("User-Ticket-view").child(uid).child("usertickets")

        eventrecyleid.setHasFixedSize(true)


        eventrecyleid.layoutManager = linearLayoutManager
        eventrecyleid.adapter = EventAdapter(mEventDatbase!!,context!!)
    }


}
