package com.example.saveapp.MVP

import com.example.saveapp.ContactModel

interface EditContract {
    interface View {
        fun showSuccessMessage()
        fun showErrorMessage(error: String)
    }
    interface Presenter {

        fun onUpdateContact(contact: ContactModel)
        fun onEditContact(contact: ContactModel)
        fun onDestroy()
    }

}