package com.codecamp.blogapp.binding.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.codecamp.blogapp.R
import com.codesgood.views.JustifiedTextView
import de.hdodenhof.circleimageview.CircleImageView

class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var postUserImage : CircleImageView
    var postUserName : TextView
    var postLike : ImageView
    var postComment : ImageView
    var postShare : ImageView
    var menuIcon : ImageView
    var postText : JustifiedTextView
    var postDate : TextView
    var mConstraintLayout : ConstraintLayout
    init {
        postUserImage = itemView.findViewById(R.id.postUserImage);
        postUserName = itemView.findViewById(R.id.postUserName);
        postLike = itemView.findViewById(R.id.likeImage);
        postComment = itemView.findViewById(R.id.addCommentImage);
        postShare = itemView.findViewById(R.id.sharePostImage);
        menuIcon = itemView.findViewById(R.id.moreVerticalIcon);
        postText = itemView.findViewById(R.id.postText);
        postDate = itemView.findViewById(R.id.postDate);
        mConstraintLayout = itemView.findViewById(R.id.constraintLayout);
    }
}