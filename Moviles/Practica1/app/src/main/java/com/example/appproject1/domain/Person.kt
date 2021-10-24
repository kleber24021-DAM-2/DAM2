package com.example.appproject1.domain

import android.os.Parcel
import android.os.Parcelable
data class Person(
    var name: String,
    var dni: String,
    var telephone: String,
    var isMale: Boolean,
    var isAdmin: Boolean
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(dni)
        parcel.writeString(telephone)
        parcel.writeByte(if (isMale) 1 else 0)
        parcel.writeByte(if (isAdmin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

}