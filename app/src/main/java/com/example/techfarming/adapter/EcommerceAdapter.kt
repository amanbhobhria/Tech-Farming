package com.example.techfarming.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.utilities.CellClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class EcommerceAdapter(val context: Context, val ecommtListData : List<DocumentSnapshot>, private val cellClickListener: CellClickListener):
    RecyclerView.Adapter<EcommerceAdapter.EcommercceViewModel>() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    class EcommercceViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ecommTitle: TextView = itemView.findViewById(R.id.ecommtitle)
        val ecommPrice: TextView = itemView.findViewById(R.id.ecommPrice)
        val ecommretailer: TextView = itemView.findViewById(R.id.ecommretailer)
        val ecommItemAvailability: TextView = itemView.findViewById(R.id.ecommItemAvailability)
        val ecommImage: ImageView = itemView.findViewById(R.id.ecommImage)
        val ecommRating: RatingBar = itemView.findViewById(R.id.ecommRating)





    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EcommerceAdapter.EcommercceViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.single_ecomm_item, parent, false)
        return EcommerceAdapter.EcommercceViewModel(view)
    }

    override fun getItemCount(): Int {
        return ecommtListData.size
    }

    override fun onBindViewHolder(holder: EcommerceAdapter.EcommercceViewModel, position: Int) {
        val currentList = ecommtListData[position]
        holder.ecommTitle.text = currentList.get("title").toString()
        holder.ecommPrice.text = "\u20B9 "+currentList.get("price").toString()
        holder.ecommretailer.text = currentList.get("retailer").toString()
        holder.ecommItemAvailability.text = currentList.get("availability").toString()
        val allImages = currentList.get("imageUrl") as List<String>
        Glide.with(context).load(allImages[0].toString()).into(holder.ecommImage)
        holder.ecommRating.rating = currentList.get("rating").toString().toFloat()

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentList.id.toString())
        }

    }
}