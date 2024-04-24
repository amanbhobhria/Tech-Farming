package com.example.techfarming.adapter

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class SMPostListAdapter(val context: Context, val postListData : List<DocumentSnapshot>): RecyclerView.Adapter<SMPostListAdapter.SMPostListViewModel>() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    class SMPostListViewModel(itemView: View): RecyclerView.ViewHolder(itemView){

        val userNamePostSM: TextView = itemView.findViewById(R.id.userNamePostSM)
        val userPostTitleValue: TextView = itemView.findViewById(R.id.userPostTitleValue)
        val userPostDescValue: TextView = itemView.findViewById(R.id.userPostDescValue)
        val userPostUploadTime: TextView = itemView.findViewById(R.id.userPostUploadTime)
        val postVideoSM: WebView = itemView.findViewById(R.id.postVideoSM)
        val postImageSM: ImageView = itemView.findViewById(R.id.postImageSM)
        val userProfileImageCard: CardView = itemView.findViewById(R.id.userProfileImageCard)
        val postContainer: ConstraintLayout = itemView.findViewById(R.id.post_container)
        val userProfileImagePost: ImageView = itemView.findViewById(R.id.userProfileImagePost)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMPostListViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.post_with_image_sm, parent, false)
        return SMPostListAdapter.SMPostListViewModel(view)
    }

    override fun getItemCount(): Int {
        return postListData.size
    }

    override fun onBindViewHolder(holder: SMPostListViewModel, position: Int) {
        val currentPost = postListData[position]



        holder.userNamePostSM.text = currentPost.get("name").toString()
        holder.userPostTitleValue.text = currentPost.get("title").toString()
        holder.userPostDescValue.text = currentPost.get("description").toString()
        holder.userPostUploadTime.text = DateUtils.getRelativeTimeSpanString(currentPost.get("timeStamp") as Long)

        val imageUrl = currentPost.get("imageUrl")
        Log.d("Post without Image1", imageUrl.toString())


        val uploadType = currentPost.get("uploadType").toString()
        if (uploadType == "video"){



            // Web View

            val webSet: WebSettings = holder.postVideoSM.settings
            webSet.javaScriptEnabled = true
            webSet.loadWithOverviewMode = true
            webSet.useWideViewPort = true


            holder.postVideoSM.setWebViewClient(object : WebViewClient() {
                // autoplay when finished loading via javascript injection
                override fun onPageFinished(view: WebView, url: String) {
//                    holder.postVideoSM.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
                }
            })


            holder.postVideoSM.loadUrl(currentPost.get("imageUrl").toString())
//            holder.postVideoSM.stopLoading()
            holder.postImageSM.visibility = View.GONE
            holder.postVideoSM.visibility = View.VISIBLE


        } else if (uploadType == "image"){
            Glide.with(context).load(currentPost.get("imageUrl")).into(holder.postImageSM)
            holder.postVideoSM.visibility = View.GONE

            holder.postImageSM.visibility = View.VISIBLE
            Log.d("Upload Type 2 ", uploadType)
        }else if (uploadType.isEmpty() ){
            Log.d("Post without Image2", imageUrl.toString())
            holder.postImageSM.visibility = View.GONE
            holder.postVideoSM.visibility = View.GONE
            Log.d("Upload Type 3 ", uploadType)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        holder.userProfileImageCard.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
        holder.postContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)


        holder.postContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
//        Glide.with(context).load(firebaseAuth.currentUser!!.photoUrl.toString()).into(holder.userProfileImagePost)
        holder.userPostDescValue.setOnClickListener {
            holder.userPostDescValue.maxLines = Int.MAX_VALUE
        }



        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document("${currentPost.get("userID")}").get()
            .addOnSuccessListener {
                val profileImage = it.get("profileImage").toString()
                if (!profileImage.isNullOrEmpty()){
                    Glide.with(context).load(it.get("profileImage").toString()).into(holder.userProfileImagePost)
                }
            }
    }
}