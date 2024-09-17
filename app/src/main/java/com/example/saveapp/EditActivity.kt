package com.example.saveapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveapp.MVP.EditContract
import com.example.saveapp.MVP.EditPresenter
import com.example.saveapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity(), EditContract.View {
    private val binding by lazy {
        ActivityEditBinding.inflate(layoutInflater)
    }
    private var contactModel: ContactModel? = null

    // ربط الـ Presenter بالـ View
    private lateinit var presenter: EditContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // استلام بيانات جهة الاتصال من الـIntent
        contactModel = intent.getParcelableExtra("contact")

        // عرض البيانات في الـEditText
        contactModel?.let {
            binding.etContactName.setText(it.name)
            binding.etContactPhone.setText(it.phone)
            binding.etContactData.setText(it.data)

        }

        // تهيئة الـ Presenter
        presenter = EditPresenter(this, MyDatabase.myDatabase.getDao())



        // عند الضغط على زر الحفظ
        binding.btnSave.setOnClickListener {
            val name = binding.etContactName.text.toString()
            val phone = binding.etContactPhone.text.toString()
            val data = binding.etContactData.text.toString()

            if (name.isEmpty() || phone.isEmpty() || data.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // تحديث جهة الاتصال باستخدام الـ Presenter
            contactModel?.let {
                val updatedContact = it.copy(name = name, phone = phone, data = data)
                presenter.onUpdateContact(updatedContact)
            }
        }
    }

    override fun showSuccessMessage() {
        Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
