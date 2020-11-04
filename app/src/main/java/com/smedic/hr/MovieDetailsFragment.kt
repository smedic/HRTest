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
        name.text = arguments?.get(MOVIE_NAME) as String
    }

    companion object {
        const val MOVIE_NAME = "movie_name"
    }
}