package com.example.activityandfragments

import android.os.Parcel
import android.os.Parcelable

class NameColor(val name: String?, val color: Int) : Parcelable {
    constructor(parcel: Parcel) : this(name = parcel.readString(), color = parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NameColor> {
        override fun createFromParcel(parcel: Parcel): NameColor = NameColor(parcel)
        override fun newArray(size: Int): Array<NameColor?> = arrayOfNulls(size)
    }
}