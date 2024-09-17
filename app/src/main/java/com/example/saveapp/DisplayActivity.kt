package com.example.saveapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveapp.MVP.DisplayContract
import com.example.saveapp.MVP.DisplayPresenter
import com.example.saveapp.databinding.ActivityDisplayBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DisplayActivity : AppCompatActivity() ,DisplayContract.View{
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            onDeleteClick = { presenter.deleteContact(it) },
            onEditClick = { presenter.editContact(it) }
        )
    }

    private lateinit var presenter: DisplayContract.Presenter
    private val binding: ActivityDisplayBinding by lazy {
        ActivityDisplayBinding.inflate(layoutInflater)
    }

    private lateinit var contactDao: MyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       // Initialize the database
        contactDao = MyDatabase.myDatabase.getDao()
        presenter = DisplayPresenter(this, contactDao)
        // Load contacts using RxJava
        presenter.loadContacts()
    }

    override fun displayContacts(contacts: List<ContactModel>) {
        contactAdapter.list=contacts as ArrayList<ContactModel>
        binding.recDisplay.adapter=contactAdapter
        contactAdapter.notifyDataSetChanged()

    }

    override fun showErrorMessage(error: String) {
        // Handle error message
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
