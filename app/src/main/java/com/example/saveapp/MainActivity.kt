package com.example.saveapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveapp.MVP.MainContract
import com.example.saveapp.MVP.MainPresenter
import com.example.saveapp.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(), MainContract.View {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var presenter: MainContract.Presenter
    private val CompositeDisposable:CompositeDisposable=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter= MainPresenter(this)

        binding.btnSave.setOnClickListener {
            val data = binding.inputData.text.toString()
            val name = binding.inputName.text.toString()
            val phone = binding.inputPhone.text.toString()
            presenter.onSaveContact(data, name, phone)

        }
        binding.btnDisplay.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDisposable.clear()
    }

    override fun showSuccessMessage() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()

    }
}