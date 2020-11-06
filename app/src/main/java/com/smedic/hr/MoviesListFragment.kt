package com.smedic.hr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedic.hr.adapter.MoviesRecyclerViewAdapter
import com.smedic.hr.model.Movie
import com.smedic.hr.tools.gone
import com.smedic.hr.tools.setTint
import com.smedic.hr.tools.visible
import com.smedic.hr.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.movies_list_fragment.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MoviesListFragment : Fragment() {

    private val viewModel by activityViewModels<MoviesViewModel>()
    private lateinit var moviesListAdapter : MoviesRecyclerViewAdapter

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

        show_search_button.setOnClickListener {
            if (search_box.isVisible) {
                search_box.gone(true)
                show_search_button.colorFilter = null
            } else {
                search_box.visible(true)
                show_search_button.setTint(R.color.orange)
            }
        }

        search_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                moviesListAdapter.filter.filter(text)
            }
        })
    }

    private fun setupRecyclerView(view: View, movies: List<Movie>) {
        val divider = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        divider.setDrawable(drawable!!)

        movies_list.addItemDecoration(divider)
        movies_list.layoutManager = LinearLayoutManager(requireActivity())
        moviesListAdapter = MoviesRecyclerViewAdapter(movies, movies) {
            val bundle = bundleOf(MovieDetailsFragment.MOVIE_ID to it.id)
            Navigation.findNavController(view).navigate(R.id.action_list_to_detail, bundle)
        }
        movies_list.adapter = moviesListAdapter
    }
}