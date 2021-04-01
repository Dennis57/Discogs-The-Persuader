package com.example.thepersuader.Network

import com.example.thepersuader.Model.Artist.ArtistResponse
import com.example.thepersuader.Model.Release.ReleaseResponse
import com.example.thepersuader.Model.ReleaseDetail.ReleaseDetailResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.discogs.com/"

private val gson = GsonBuilder().create()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface DiscogsApiService {
    @GET("artists/1")
    fun getArtist(): Observable<Response<ArtistResponse>>

    @GET("artists/1/releases")
    fun getReleases(@Query("page") page: Int?, @Query("per_page") per_page: Int?): Observable<Response<ReleaseResponse>>

    @GET("releases/{id}")
    fun getReleaseDetail(@Path("id") id: Int?): Observable<Response<ReleaseDetailResponse>>
}

object DiscogsApi {
    val retrofitService : DiscogsApiService by lazy { retrofit.create(DiscogsApiService::class.java) }
}

