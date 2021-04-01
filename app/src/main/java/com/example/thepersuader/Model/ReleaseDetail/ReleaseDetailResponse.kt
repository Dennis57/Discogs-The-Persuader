package com.example.thepersuader.Model.ReleaseDetail

import com.google.gson.annotations.SerializedName

data class ReleaseDetailResponse(

    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("year")
    val year: Int?,

    @SerializedName("artists")
    val artists: List<ArtistResponse>?,

    @SerializedName("tracklist")
    val trackList: List<TrackResponse>?,

    @SerializedName("videos")
    val videos: List<VideoResponse>?
)

data class ArtistResponse(

    @SerializedName("name")
    val name: String?
)

data class TrackResponse(
    @SerializedName("title")
    val title: String?,

    @SerializedName("duration")
    val duration: String?
)

data class VideoResponse(
    @SerializedName("title")
    val title: String?,

    @SerializedName("uri")
    val uri: String?
)