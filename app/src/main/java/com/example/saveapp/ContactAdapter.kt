package com.example.saveapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saveapp.databinding.ItemContactBinding

class ContactAdapter (
    private val onDeleteClick: (ContactModel) -> Unit,
    private val onEditClick: (ContactModel) -> Unit
):RecyclerView.Adapter<ContactAdapter.Holder>()

{

var list:ArrayList<ContactModel>?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding=ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list?.get(position)!!)


    }
    inner class Holder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(contactModel: ContactModel) {
        binding.contactData.text = contactModel.data
        binding.contactName.text = contactModel.name
        binding.contactPhone.text = contactModel.phone

        binding.btnEdit.setOnClickListener {
            val intent = Intent(binding.root.context, EditActivity::class.java)
            intent.putExtra("contact", contactModel)

            binding.root.context.startActivity(intent)
        }

        // عند الضغط على زر Delete
        binding.btnDelete.setOnClickListener {
            MyDatabase.myDatabase.getDao().deleteContact(contactModel)
            // إزالة العنصر من القائمة
            onDeleteClick(contactModel)
            list?.remove(contactModel)
            notifyDataSetChanged()
        }

    }
    }
}