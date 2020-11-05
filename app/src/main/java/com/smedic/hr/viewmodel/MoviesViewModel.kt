package com.smedic.hr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smedic.hr.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MoviesViewModel : ViewModel() {

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also { loadItems() }
    }

    private fun loadItems() {
        viewModelScope.launch { getMoviesFromNative() }
    }

    fun getItems(): LiveData<List<Movie>> {
        return movies
    }

    //TODO CHECK DISPATCHER
    private suspend fun getMoviesFromNative() = withContext(Dispatchers.IO) {
        val data = async { getMovies() }
        try {
            movies.postValue(data.await())
        } catch (e: Exception) {
            movies.postValue(listOf())
        }
    }

    fun getMovie(id: Int): Movie? {
        return movies.value?.first { it.id == id }
    }

    private external fun getMovies(): ArrayList<Movie>
}