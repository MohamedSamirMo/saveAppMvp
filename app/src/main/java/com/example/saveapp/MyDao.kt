package com.example.saveapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertContact(contactModel: ContactModel):Completable

     @Update
     fun updateContact(contactModel: ContactModel):Completable
    @Delete
     fun deleteContact(contactModel: ContactModel):Completable

    @Query("select * from ContactModel")
     fun getAllContacts(): Single<List<ContactModel>>

}