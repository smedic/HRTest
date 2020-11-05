package com.smedic.hr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.smedic.hr.model.Movie
import com.smedic.hr.viewmodel.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_fragment.*
import java.text.DecimalFormat

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MovieDetailsFragment : Fragment() {

    private var TAG = "SMEDIC"
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
        viewModel.getMovie(movieId)?.let { setMovieLayout(it) }
    }

    private fun setMovieLayout(movie: Movie) {
        Log.d(TAG, "---------------")
        Log.d(TAG, "----> : ${movie.name}")
        Log.d(TAG, "----> : ${movie.score}")
        Log.d(TAG, "----> : ${movie.description}")
        Log.d(TAG, "----> : ${movie.actors.size}")

        title.text = movie.name
        description.text = movie.description
        closeButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        val df = DecimalFormat("0.0")
        val score = "${df.format(movie.score)}/10"
        rating.text = score

        for (actor in movie.actors) {
            Log.d(TAG, "----> actor : ${actor.name}")
            Log.d(TAG, "----> actor : ${actor.age}")
            Log.d(TAG, "----> actor : ${actor.imageUrl}")
        }
        Picasso.get().load(movie.posterUrl).into(poster)
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}