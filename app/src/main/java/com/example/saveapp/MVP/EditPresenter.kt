package com.example.saveapp.MVP

import android.widget.Toast
import com.example.saveapp.ContactModel
import com.example.saveapp.MyDao
import com.example.saveapp.MyDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EditPresenter (private val view: EditContract.View , private val contactDao: MyDao) : EditContract.Presenter  {
    private val CompositeDisposable: CompositeDisposable = CompositeDisposable()



    override fun onUpdateContact(contact: ContactModel) {
        contactDao.updateContact(contact)
            .subscribeOn(Schedulers.io()) // تنفيذ العملية في الخلفية
            .observeOn(AndroidSchedulers.mainThread()) // تحديث الـ UI بعد العملية
            .subscribe({
                // في حالة النجاح
                view.showSuccessMessage()
            }, { error ->
                // في حالة الأخطاء
                view.showErrorMessage(error.message.toString())
            })
    }

    override fun onEditContact(contact: ContactModel) {
        MyDatabase.myDatabase.getDao().updateContact(contact)
    }

    override fun onDestroy() {
      CompositeDisposable.clear()
    }

}