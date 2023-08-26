package com.example.getvideokotlin

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
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
        val thumbnail = getThumbnailFromVideo(videoModel.path.toString(), holder.itemView.context)

        holder.thumbnil.setImageBitmap(thumbnail)

//        Glide.with(holder.itemView.context)
//            .load(videoModel.thumbnilUri)
//            .placeholder(R.drawable.video_icon)
//            .centerCrop()
//            .into(holder.thumbnil)

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

    private fun getThumbnailFromVideo(videoPath: String, context: Context): Bitmap {
        val mediaStoreId = getMediaStoreIdFromPath(videoPath, context.contentResolver)
        val thumbnail = MediaStore.Video.Thumbnails.getThumbnail(
            context.contentResolver,
            mediaStoreId,
            MediaStore.Video.Thumbnails.MICRO_KIND,
            null
        )
        return thumbnail ?: BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
    }

    private fun getMediaStoreIdFromPath(videoPath: String, contentResolver: ContentResolver): Long {
        val projection = arrayOf(MediaStore.Video.Media._ID)
        val selection = "${MediaStore.Video.Media.DATA} = ?"
        val selectionArgs = arrayOf(videoPath)
        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                return cursor.getLong(idColumn)
            }
        }
        return -1 // Return a default value if not found
    }

}