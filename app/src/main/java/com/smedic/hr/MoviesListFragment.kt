package com.smedic.hr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedic.hr.adapter.MoviesRecyclerViewAdapter
import com.smedic.hr.model.Movie
import com.smedic.hr.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.item_list.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MoviesListFragment : Fragment() {

    private var TAG = "SMEDIC"
    private val viewModel by viewModels<MoviesViewModel>()

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

        item_list.addItemDecoration(verticalDecorator);
        item_list.addItemDecoration(horizontalDecorator);
        //item_list.layoutManager = GridLayoutManager(this, 2)
        item_list.layoutManager = LinearLayoutManager(requireActivity())
        item_list.adapter = MoviesRecyclerViewAdapter(movies) {
            Log.d(TAG, "clicked: ${it.name}")
            val bundle = bundleOf(MovieDetailsFragment.MOVIE_NAME to it.name)
            Navigation.findNavController(view).navigate(R.id.action_list_to_detail, bundle)
        }
    }
}