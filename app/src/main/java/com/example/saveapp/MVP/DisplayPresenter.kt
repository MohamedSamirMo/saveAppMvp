package com.example.saveapp.MVP

import com.example.saveapp.ContactModel
import com.example.saveapp.MyDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DisplayPresenter (private val view: DisplayContract.View , private val contactDao: MyDao) : DisplayContract.Presenter {
    private val CompositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun loadContacts() {
        // استخدم RxJava لتحميل البيانات من قاعدة البيانات

        contactDao.getAllContacts()
            .subscribeOn(Schedulers.io()) // تنفيذ العملية في الخلفية
            .observeOn(AndroidSchedulers.mainThread()) // إظهار النتيجة في الـ Main Thread
            .subscribe(object : SingleObserver<List<ContactModel>> {
                override fun onSubscribe(d: Disposable) {
                    CompositeDisposable.add(d)

                }

                override fun onSuccess(contacts: List<ContactModel>) {
                    view.displayContacts(contacts)
                }

                override fun onError(e: Throwable) {
                    view.showErrorMessage(e.message.toString())
                }
            })
    }

    override fun deleteContact(contact: ContactModel) {
        contactDao.deleteContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadContacts() // إعادة تحميل جهات الاتصال بعد الحذف
            }, { error ->
                view.showErrorMessage(error.message ?: "خطأ عند الحذف") // عرض الخطأ
            })
    }

    override fun editContact(contact: ContactModel) {
        contactDao.updateContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadContacts() // إعادة تحميل جهات الاتصال بعد التعديل
            }, { error ->
                view.showErrorMessage(error.message ?: "خطأ عند التعديل") // عرض الخطأ
            })
    }

    override fun onDestroy() {

        CompositeDisposable.clear()
    }

}
