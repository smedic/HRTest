package com.smedic.hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smedic.hr.R
import com.smedic.hr.model.Movie
import com.smedic.hr.tools.formattedRating
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */
class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private var moviesFiltered: List<Movie>,
    private val clickListener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = moviesFiltered[position]
        holder.name.text = item.name
        holder.rating.text = item.score.formattedRating()
        Picasso.get().load(item.posterUrl).into(holder.photo)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount() = moviesFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    moviesFiltered = movies
                } else {
                    val resultList = ArrayList<Movie>()
                    for (row in movies) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    moviesFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFiltered = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val rating: TextView = view.findViewById(R.id.rating)
        val photo: ImageView = view.findViewById(R.id.photo)
    }
}