package com.smedic.hr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedic.hr.WebPageFragment.Companion.ACTOR_BIOGRAPHY_URL
import com.smedic.hr.adapter.ActorsRecyclerViewAdapter
import com.smedic.hr.model.Actor
import com.smedic.hr.model.Movie
import com.smedic.hr.tools.formattedRating
import com.smedic.hr.viewmodel.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_fragment.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MovieDetailsFragment : Fragment() {

    private val viewModel by activityViewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.get(MOVIE_ID) as Int
        viewModel.getMovie(movieId)?.let {
            setMovieInfoLayout(it)
            setupRecyclerView(view, it.actors)
        }
    }

    private fun setMovieInfoLayout(movie: Movie) {
        title.text = movie.name
        description.text = movie.description
        close_button.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        rating.text = movie.score.formattedRating()
        Picasso.get().load(movie.posterUrl).into(poster)
    }

    private fun setupRecyclerView(view: View, actors: List<Actor>) {
        val horizontalDecorator =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL)

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        horizontalDecorator.setDrawable(drawable!!)
        actors_list.addItemDecoration(horizontalDecorator)
        actors_list.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL,
            false
        )
        actors_list.adapter = ActorsRecyclerViewAdapter(actors) {
            val bundle = bundleOf(ACTOR_BIOGRAPHY_URL to it.biographyUrl)
            Navigation.findNavController(view).navigate(R.id.action_detail_to_webpage, bundle)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}