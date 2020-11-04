package com.smedic.hr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.smedic.hr.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.movie_details_fragment.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MovieDetailsFragment : Fragment() {
    private var TAG = "SMEDIC"
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "--->: ${arguments?.get(MOVIE_NAME)}")
        val movieName = arguments?.get(MOVIE_NAME) as String;
        name.text = movieName

        val movieDetail = viewModel.getMovieInfo(movieName)
        Log.d(TAG, "---------------")
        Log.d(TAG, "----> : ${movieDetail.name}")
        Log.d(TAG, "----> : ${movieDetail.score}")
        Log.d(TAG, "----> : ${movieDetail.description}")
        Log.d(TAG, "----> : ${movieDetail.actors.size}")

        for(actor in movieDetail.actors) {
            Log.d(TAG, "----> actor : ${actor.name}")
            Log.d(TAG, "----> actor : ${actor.age}")
            Log.d(TAG, "----> actor : ${actor.imageUrl}")
        }

    }

    companion object {
        const val MOVIE_NAME = "movie_name"
    }
}