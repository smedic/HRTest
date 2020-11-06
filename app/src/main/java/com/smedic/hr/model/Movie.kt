package com.smedic.hr.model

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */
data class Movie(
    val id: Int,
    val name: String,
    val lastUpdated: Int,
    val score: Float,
    val description: String,
    val actors: List<Actor>,
    val posterUrl: String,
    val trailerYouTubeId: String,
)