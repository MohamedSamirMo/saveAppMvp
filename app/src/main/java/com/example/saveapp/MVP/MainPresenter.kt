package com.example.saveapp.MVP

import android.widget.Toast
import com.example.saveapp.ContactModel
import com.example.saveapp.MyDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter (private val view: MainContract.View) : MainContract.Presenter {
    private val CompositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onSaveContact(data: String, name: String, phone: String) {

        MyDatabase
            .myDatabase.getDao()
            .insertContact(ContactModel(data, name, phone))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    CompositeDisposable.add(d)
                }

                override fun onComplete() {
                    view.showSuccessMessage()
                }

                override fun onError(e: Throwable) {
                    view.showErrorMessage(e.message.toString())
                }

            })

    }

    override fun onDestroy() {
        CompositeDisposable.clear()
    }

}