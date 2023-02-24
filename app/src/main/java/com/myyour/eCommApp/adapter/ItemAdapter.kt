package com.myyour.eCommApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myyour.eCommApp.R
import com.myyour.eCommApp.Utils.enums.VIEWTYPE
import com.myyour.eCommApp.model.Item
import com.squareup.picasso.Picasso

class ItemAdapter(private val itemsList: List<Item>, private val viewType: VIEWTYPE) :
    RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): ItemViewHolder {
        val itemView: View = when (viewType) {
            VIEWTYPE.LINEARVIEW -> LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_view, parent, false)
            VIEWTYPE.GRIDVIEW -> LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item_view, parent, false)
        }

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.titleTextView.text = itemsList[position].name

        setImageIntoView(position, holder.imageView)
        when (viewType) {
            VIEWTYPE.LINEARVIEW -> {
                holder.extraView?.text = itemsList[position].extra ?: ""
                holder.priceView.text = itemsList[position].price
            }
            VIEWTYPE.GRIDVIEW -> {
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