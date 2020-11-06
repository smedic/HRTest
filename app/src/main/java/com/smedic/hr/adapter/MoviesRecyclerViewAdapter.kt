package com.smedic.hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smedic.hr.R
import com.smedic.hr.model.Movie
import com.smedic.hr.tools.formattedRating
import com.squareup.picasso.Picasso

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */
class MoviesRecyclerViewAdapter(
    private val values: List<Movie>,
    private val clickListener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.rating.text = item.score.formattedRating()
        Picasso.get().load(item.posterUrl).into(holder.photo)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val rating: TextView = view.findViewById(R.id.rating)
        val photo: ImageView = view.findViewById(R.id.photo)
    }
}