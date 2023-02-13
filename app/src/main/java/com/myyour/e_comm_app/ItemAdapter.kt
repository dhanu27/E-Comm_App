package com.myyour.e_comm_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private val itemsList: ArrayList<String>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ItemViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.titleTextView.text = itemsList[position];
    }

    override fun getItemCount(): Int {
        return itemsList.size;
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView : TextView = itemView.findViewById(R.id.title)
}