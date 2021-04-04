package com.example.myeventtickets

import android.content.Context
import android.content.Intent
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myeventtickets.R.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso


class EventAdapter (databaseQuery: DatabaseReference, var context: Context):FirebaseRecyclerAdapter<Events,
        EventAdapter.ViewHolder>(Events::class.java,layout.event_row,EventAdapter.ViewHolder::class.java,databaseQuery) {


    override fun populateViewHolder(viewHolder: EventAdapter.ViewHolder?, event: Events?, position: Int) {

        var eventid = getRef(position).key
        viewHolder!!.bindView(event!!,context)


        viewHolder.itemView.setOnClickListener{

            var eventtile2 = viewHolder.eventtitle //for sending data to eventinfo activity
            var eventiamge2 = viewHolder.eventimage
            var eventprice2 = viewHolder.eventprice
            var eventtime2= viewHolder.eventtime
            var eventdate2= viewHolder.eventdate
            var eventstate2 =viewHolder.eventstate


               var eventinfointent = Intent(context,eventinfo::class.java)
                  eventinfointent.putExtra("eventkey",eventid)
                  eventinfointent.putExtra("eventtile",eventtile2)
                  eventinfointent.putExtra("eventimage",eventiamge2)
                  eventinfointent.putExtra("eventprice",eventprice2)
                  eventinfointent.putExtra("eventtime",eventtime2)
                  eventinfointent.putExtra("eventdate",eventdate2)
                  eventinfointent.putExtra("eventstate",eventstate2)
               context.startActivity(eventinfointent)

        }

    }

//    override fun getItem(position: Int): Events {
//        return super.getItem(itemCount-1-position)
//    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var eventtitle:String?=null
        var eventimage:String?=null
        var eventstate:String?=null
        var eventtime:String?=null
        var eventdate:String?=null
        var eventprice:String?=null

        fun bindView(event:Events,context: Context){


            var myeventtitle = itemView.findViewById<TextView>(id.event_title)
            var myeventimage = itemView.findViewById<ImageView>(id.event_image)
            var myeventdate = itemView.findViewById<TextView>(id.event_date)
            var myeventtime = itemView.findViewById<TextView>(id.event_time)
            var myeventprice = itemView.findViewById<TextView>(id.event_price)
            var myeventstate= itemView.findViewById<TextView>(id.event_state)


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
//                   .load(eventimage)
  //                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
    //              .into(myeventimage)




        }

    }


}