package com.example.thepersuader.Model.Artist

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("name")
    val name: String?,

    @SerializedName("realname")
    val real_name: String?,

    @SerializedName("aliases")
    val aliases: List<AliasResponse>?
)

data class AliasResponse(
    @SerializedName("name")
    val name: String
)