package com.example.homework1516.presentation.gifslist

import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework1516.R
import com.example.homework1516.data.remote.Data

class GifAdapter(private val activity: Activity) : RecyclerView.Adapter<GifViewHolder>() {

    var results: List<Data> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view: View = activity.layoutInflater.inflate(R.layout.gif_list_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val result: Data = results[position]
        val uri = result.images.original.url
        Glide
            .with(holder.itemView.context)
            .load(uri)
            .thumbnail(Glide.with(activity).load(R.drawable.gif_placeholder))
            .into(holder.gifView);
    }

    override fun getItemId(i: Int): Long = i.toLong()
    override fun getItemCount(): Int = results.size

}

class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gifView: ImageView = itemView.findViewById(R.id.gif_view)
}