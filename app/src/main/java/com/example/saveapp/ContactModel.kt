package com.example.saveapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactModel(
    @PrimaryKey
    val data: String,
    val name: String,
    val phone: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeString(name)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactModel> {
        override fun createFromParcel(parcel: Parcel): ContactModel {
            return ContactModel(parcel)
        }

        override fun newArray(size: Int): Array<ContactModel?> {
            return arrayOfNulls(size)
        }
    }
}
