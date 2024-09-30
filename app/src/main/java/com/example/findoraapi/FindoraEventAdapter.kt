package com.example.findoraapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class FindoraEventAdapter(private val events: List<Event?>) :
    RecyclerView.Adapter<FindoraEventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImageView: ImageView = view.findViewById(R.id.eventImageView) // Uncommented
        val eventTitleTextView: TextView = view.findViewById(R.id.eventTitleTextView)
        val eventDescriptionTextView: TextView = view.findViewById(R.id.eventDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    //To display Event information on Recycler View
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventImageView.setImageResource(R.drawable.baseline_add_photo_alternate_24_2)
        holder.eventTitleTextView.text = event?.eventName ?: "Unknown Event"
        // Set the concatenated event details in the eventDescriptionTextView
        """
               Location: ${event?.category ?: "No category"}
               Category:  ${event?.location ?: "No location"}
                Organisers: ${event?.organisers ?: "No organisers"}
                Date: ${event?.date ?: "No date"}
                Details: ${event?.details ?: "No details available"}
            """.trimIndent().also { holder.eventDescriptionTextView.text = it }
    }

// Create dynamic lists with RecyclerView  :  Views  :  Android Developers (no date) Android Developers. Available at: https://developer.android.com/develop/ui/views/layout/recyclerview (Accessed: 23 September 2024).


    override fun getItemCount(): Int {
        return events.size
    }
}
