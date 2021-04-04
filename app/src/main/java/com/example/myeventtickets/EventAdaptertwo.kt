package com.example.myeventtickets

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import java.time.temporal.TemporalQuery

class EventAdaptertwo(databaseQuery: DatabaseReference, var context: Context):FirebaseRecyclerAdapter<Events,
        EventAdaptertwo.ViewHolder>(Events::class.java,
    R.layout.event_row,EventAdaptertwo.ViewHolder::class.java,databaseQuery){

    override fun populateViewHolder(viewHolder: EventAdaptertwo.ViewHolder?, event: Events?, position: Int) {

        var eventid = getRef(position).key
        viewHolder!!.bindView(event!!,context)

        viewHolder.itemView.setOnClickListener{

            var eventtile2 = viewHolder.eventtitle //for sending data to eventinfo activity
            var eventiamge2 = viewHolder.eventimage
            var eventprice2 = viewHolder.eventprice
            var eventtime2= viewHolder.eventtime
            var eventdate2= viewHolder.eventdate
            var eventstate2 =viewHolder.eventstate


            var eventinfointent2 = Intent(context,eventinfotwo::class.java)
            eventinfointent2.putExtra("eventkey2",eventid)
            eventinfointent2.putExtra("eventtile2",eventtile2)
            eventinfointent2.putExtra("eventimage2",eventiamge2)
            eventinfointent2.putExtra("eventprice2",eventprice2)
            eventinfointent2.putExtra("eventtime2",eventtime2)
            eventinfointent2.putExtra("eventdate2",eventdate2)
            eventinfointent2.putExtra("eventstate2",eventstate2)
            context.startActivity(eventinfointent2)

        }

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var eventtitle:String?=null
        var eventimage:String?=null
        var eventstate:String?=null
        var eventtime:String?=null
        var eventdate:String?=null
        var eventprice:String?=null

        fun bindView(event:Events,context: Context){


            var myeventtitle = itemView.findViewById<TextView>(R.id.event_title)
            var myeventimage = itemView.findViewById<ImageView>(R.id.event_image)
            var myeventdate = itemView.findViewById<TextView>(R.id.event_date)
            var myeventtime = itemView.findViewById<TextView>(R.id.event_time)
            var myeventprice = itemView.findViewById<TextView>(R.id.event_price)
            var myeventstate= itemView.findViewById<TextView>(R.id.event_state)


            eventtitle = event.event_title
            eventimage = event.event_image
            eventstate = event.event_state
            eventtime =  event.event_time
            eventdate =  event.event_date
            eventprice = event.event_price


            myeventtitle.text = event.event_title
            myeventdate.text = event.event_date
            myeventtime.text = event.event_time
            myeventprice.text = "\u20b9"+ event.event_price
            myeventstate.text = event.event_state

            Glide.with(context)
                .load(eventimage)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(myeventimage)




        }
}}