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
import com.smedic.hr.adapter.MoviesRecyclerViewAdapter
import com.smedic.hr.model.Movie
import com.smedic.hr.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MoviesListFragment : Fragment() {

    private val viewModel by activityViewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItems().observe(requireActivity()) { movies ->
            setupRecyclerView(view, movies)
        }
    }

    private fun setupRecyclerView(view: View, movies: List<Movie>) {
        val verticalDecorator =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        val horizontalDecorator =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL)

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        verticalDecorator.setDrawable(drawable!!)
        horizontalDecorator.setDrawable(drawable)

        movies_list.addItemDecoration(verticalDecorator)
        movies_list.addItemDecoration(horizontalDecorator)
        //item_list.layoutManager = GridLayoutManager(this, 2)
        movies_list.layoutManager = LinearLayoutManager(requireActivity())
        movies_list.adapter = MoviesRecyclerViewAdapter(movies) {
            val bundle = bundleOf(MovieDetailsFragment.MOVIE_ID to it.id)
            Navigation.findNavController(view).navigate(R.id.action_list_to_detail, bundle)
        }
    }
}