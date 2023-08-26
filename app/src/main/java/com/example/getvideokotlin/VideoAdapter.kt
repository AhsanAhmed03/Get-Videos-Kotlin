package com.example.getvideokotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(videos: ArrayList<VideoModelClass>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    var video: ArrayList<VideoModelClass> = ArrayList<VideoModelClass>()

    init {
        video = videos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.video_layout,parent,false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return video.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        val videoModel: VideoModelClass = video[position]
        holder.title.setText(videoModel.title)
        holder.path.setText(videoModel.path)

        Glide.with(holder.itemView.context)
            .load(videoModel.thumbnilUri)
            .placeholder(R.drawable.video_icon)
            .centerCrop()
            .into(holder.thumbnil)

    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title : TextView
        var path : TextView
        var thumbnil : ImageView

        init {
            title = itemView.findViewById(R.id.file_name)
            path = itemView.findViewById(R.id.file_path)
            thumbnil = itemView.findViewById(R.id.thumbnil)
        }
    }

}