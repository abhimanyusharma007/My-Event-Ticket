package com.example.myeventtickets


import android.content.ClipData.Item
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var mEventDatbase:DatabaseReference?= null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        //var mAuth = FirebaseAuth.getInstance()
        //var currentuser = mAuth.currentUser

         return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        mEventDatbase = FirebaseDatabase.getInstance().reference.child("Events")


        eventrecyleid.setHasFixedSize(true)


        eventrecyleid.layoutManager = linearLayoutManager
        eventrecyleid.adapter = EventAdapter(mEventDatbase!!,context!!)




    }



}


