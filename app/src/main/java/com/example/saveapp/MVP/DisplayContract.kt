package com.example.saveapp.MVP

import com.example.saveapp.ContactModel

interface DisplayContract {
    interface View {
        fun displayContacts(contacts: List<ContactModel>)
        fun showErrorMessage(error: String)
    }
    interface Presenter {
        fun loadContacts()
        fun deleteContact(contact: ContactModel)
        fun editContact(contact: ContactModel)
        fun onDestroy()
    }

}