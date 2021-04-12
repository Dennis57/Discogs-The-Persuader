package com.example.thepersuader.model.releaseDetail

data class ReleaseDetailUiModel (
    val id: Int,
    val title: String,
    val year: Int,
    val artists: String,
    val trackList: List<TrackListUiModel>,
    val videos: List<VideosUiModel>
)

data class TrackListUiModel(
    val title: String,
    val duration: String
)

data class VideosUiModel(
    val title: String,
    val uri: String
)