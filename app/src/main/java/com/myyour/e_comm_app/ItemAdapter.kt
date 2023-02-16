package com.myyour.e_comm_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myyour.e_comm_app.model.Item

class ItemAdapter (private val itemsList: ArrayList<Item>,private val viewType : Int) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ItemViewHolder(itemView,viewType);
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.titleTextView.text = itemsList[position].name
//           holder.imageView. = itemsList[position].name
        holder.priceView.text = itemsList[position].price
       if(viewType == 0){
           holder.extraView.text = itemsList[position].extra?:""
       }
    }

    override fun getItemCount(): Int {
        return itemsList.size;
    }
}

class ItemViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
    val titleTextView : TextView = itemView.findViewById(R.id.title)
    val imageView:ImageView = itemView.findViewById(R.id.imageView)
    val extraView:TextView = itemView.findViewById(R.id.extra)
    val priceView :TextView = itemView.findViewById(R.id.price)
}