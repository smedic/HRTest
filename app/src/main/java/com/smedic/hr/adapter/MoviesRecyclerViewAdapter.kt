package com.smedic.hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smedic.hr.R
import com.smedic.hr.model.Movie
import com.squareup.picasso.Picasso

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */
class MoviesRecyclerViewAdapter(
    private val values: List<Movie>,
    val clickListener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.lastUpdated.toString()
        holder.contentView.text = item.name
        Picasso.get()
            .load("https://image.cnbcfm.com/api/v1/image/105814861-1553608877209ben-affleck-batman-1.jpg?v=1553609938&w=1600&h=900")
            .into(holder.photo)

        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.name)
        val contentView: TextView = view.findViewById(R.id.content)
        val photo: ImageView = view.findViewById(R.id.photo)
    }
}