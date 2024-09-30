package com.example.findoraapi

import android.os.Parcel
import android.os.Parcelable

data class Event(
    val _id: String?,
    val eventName: String,
    val organisers: String,
    val category: String,
    val location: String,
    val date: String,
    val details: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(eventName)
        parcel.writeString(organisers)
        parcel.writeString(category)
        parcel.writeString(location)
        parcel.writeString(date)
        parcel.writeString(details)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
