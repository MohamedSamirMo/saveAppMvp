package com.example.saveapp.MVP
interface MainContract {
    interface View {
        fun showSuccessMessage()
        fun showErrorMessage(error: String)
    }

    interface Presenter {
        fun onSaveContact(data: String, name: String, phone: String)
        fun onDestroy()
    }
}
