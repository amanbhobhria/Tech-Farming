package com.example.techfarming.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.utilities.CellClickListener
import com.google.firebase.firestore.DocumentSnapshot

class PostListUserProfileAdapter(val context: Context, var listData: ArrayList<DocumentSnapshot>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<PostListUserProfileAdapter.PostListUserProfileViewHolder>() {
    class PostListUserProfileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userPostTitleUserProfileFrag: TextView = itemView.findViewById(R.id.userPostTitleUserProfileFrag)
        val userPostUploadTimeUserProfileFrag: TextView = itemView.findViewById(R.id.userPostUploadTimeUserProfileFrag)
        val userPostProfileCard: CardView = itemView.findViewById(R.id.userPostProfileCard)
        val userPostImageUserProfileFrag: ImageView = itemView.findViewById(R.id.userPostImageUserProfileFrag)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostListUserProfileViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_profile_posts_single, parent, false)
        return PostListUserProfileAdapter.PostListUserProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: PostListUserProfileViewHolder, position: Int) {
        val currentData = listData[position]

        holder.userPostTitleUserProfileFrag.text = currentData.get("title").toString()
        holder.userPostUploadTimeUserProfileFrag.text = DateUtils.getRelativeTimeSpanString(currentData.get("timeStamp") as Long)
        holder.userPostProfileCard.setOnClickListener {
            cellClickListener.onCellClickListener(currentData.id)
        }
        if (!currentData.get("imageUrl").toString().isNullOrEmpty()){
            Glide.with(context).load(currentData.getString("imageUrl")).into(holder.userPostImageUserProfileFrag)
        }
    }
}