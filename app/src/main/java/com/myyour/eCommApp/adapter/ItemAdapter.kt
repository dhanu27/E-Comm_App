package com.myyour.eCommApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myyour.eCommApp.R
import com.myyour.eCommApp.Utils.ViewTypes
import com.myyour.eCommApp.Utils.ViewTypes.Companion.GRIDVIEW
import com.myyour.eCommApp.Utils.ViewTypes.Companion.LINEARVIEW
import com.myyour.eCommApp.model.Item
import com.squareup.picasso.Picasso

class ItemAdapter(private val itemsList: List<Item>, private val viewType: ViewTypes) :
    RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView: View = when (viewType) {
            LINEARVIEW -> LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_view, parent, false)
            GRIDVIEW -> LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item_view, parent, false)
            else -> {
                LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_view, parent, false)}
        }
        return ItemViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
       return  viewType.getViewType()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.titleTextView.text = itemsList[position].name
         val viewTypeInt:Int = viewType.getViewType()
        setImageIntoView(position, holder.imageView)
        when (viewTypeInt) {
           LINEARVIEW -> {
                holder.extraView?.text = itemsList[position].extra ?: ""
                holder.priceView.text = itemsList[position].price
            }
            GRIDVIEW -> {
                holder.extraView?.text = ""
                holder.priceView.text = itemsList[position].price
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    private fun setImageIntoView(position: Int, view: ImageView) {
        val imgUrl: String = itemsList[position].image ?: ""
        if (imgUrl.isEmpty().not()) {
            Picasso.get().load(imgUrl).into(view)
        } else {
            // Set image to null if no image is there
            view.setImageDrawable(null)
        }
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.title)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val extraView: TextView? = itemView.findViewById(R.id.extra)
    val priceView: TextView = itemView.findViewById(R.id.price)
}