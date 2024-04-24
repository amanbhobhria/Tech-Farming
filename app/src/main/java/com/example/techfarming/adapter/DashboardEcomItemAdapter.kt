package com.example.techfarming.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.utilities.CellClickListener
import com.google.firebase.firestore.DocumentSnapshot

class DashboardEcomItemAdapter(var context: Context, val allData: List<DocumentSnapshot>, val itemsToShow: List<Int>, val cellClickListener: CellClickListener): RecyclerView.Adapter<DashboardEcomItemAdapter.DashboardEcomItemViewHolder>() {
    class DashboardEcomItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle=itemView.findViewById<TextView>(R.id.itemTitle)
        var itemPrice=itemView.findViewById<TextView>(R.id.itemPrice)
        var itemImage=itemView.findViewById<ImageView>(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardEcomItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_dashboard_ecomm_item, parent, false)
        return DashboardEcomItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsToShow.size
    }

    override fun onBindViewHolder(holder: DashboardEcomItemViewHolder, position: Int) {
        val currentData = allData[itemsToShow[position]]

        holder.itemTitle.text = currentData.get("title").toString()
        holder.itemPrice.text = "\u20B9"  + currentData.get("price").toString()
        val allImages = currentData.get("imageUrl") as ArrayList<String>
        Glide.with(context).load(allImages[0]).into(holder.itemImage)
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentData.id)
        }

    }

}