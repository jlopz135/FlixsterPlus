package com.example.flixsterplus;

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GetNewResult (
    @SerialName("results")
    val result: List<Actor>?
)

@Keep
@Serializable
data class Actor(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String,
    @SerialName("known_for")
    var knownFor: List<KnownFor>?
): java.io.Serializable{
    val profileImageURL = "https://image.tmdb.org/t/p/w200/$profilePath"
}

@Keep
@Serializable
data class KnownFor(
    @SerialName("original_title")
    val title: String?=null,
    @SerialName("original_name")
    val tvTitle: String?=null,
    @SerialName("overview")
    val overview: String?=null,
    @SerialName("poster_path")
    val posterPath: String
): java.io.Serializable{
    val posterImageURL = "https://image.tmdb.org/t/p/w200/$posterPath"
}
