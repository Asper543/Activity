package com.example.activityandfragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.activityandfrugments.R

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var data = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = data[position]
        with(holder) {
            name_text.text = item.name
            phone_text.text = "Phone:${item.phone}"
        }
    }

    class ContactViewHolder(itemView: View) : ViewHolder(itemView) {
        val name_text: TextView = itemView.findViewById(R.id.name_contact)
        val phone_text: TextView = itemView.findViewById(R.id.name_phone)
    }

    fun setData(dataItem: Contact) {
        data.add(dataItem)
    }
    fun cleared(){
        data.clear()
    }

}

data class Contact(
    val name: String,
    val phone: String?
)