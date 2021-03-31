package com.example.thepersuader.Model

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("name")
    val name: String?,

    @SerializedName("realname")
    val real_name: String?,

    @SerializedName("aliases")
    val aliases: List<Aliases>?
)

data class Aliases(
    @SerializedName("name")
    val name: String
)