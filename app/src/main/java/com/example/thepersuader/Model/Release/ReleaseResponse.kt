package com.example.thepersuader.Model.Release

import com.google.gson.annotations.SerializedName

data class ReleaseResponse(
    @SerializedName("releases")
    val releases: List<Releases>
)

data class Releases(
    val id: Int?,

    @SerializedName("title")
    val name: String?,

    @SerializedName("year")
    val year: Int?
)