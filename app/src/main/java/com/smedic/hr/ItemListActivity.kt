package com.smedic.hr

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedic.hr.adapter.MoviesRecyclerViewAdapter
import com.smedic.hr.model.Movie
import kotlinx.android.synthetic.main.item_list.*

class ItemListActivity : AppCompatActivity() {

    private var TAG = "SMEDIC"
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        viewModel.getItems().observe(this) { movies ->
            setupRecyclerView(movies)
        }
    }

    fun setupRecyclerView(movies: List<Movie>) {
        val verticalDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val horizontalDecorator = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        verticalDecorator.setDrawable(drawable!!)
        horizontalDecorator.setDrawable(drawable)

        item_list.addItemDecoration(verticalDecorator);
        item_list.addItemDecoration(horizontalDecorator);
        //item_list.layoutManager = GridLayoutManager(this, 2)
        item_list.layoutManager = LinearLayoutManager(this)
        item_list.adapter = MoviesRecyclerViewAdapter(movies)
    }
}