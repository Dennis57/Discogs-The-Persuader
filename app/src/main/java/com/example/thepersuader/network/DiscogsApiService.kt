package com.example.thepersuader.network

import com.example.thepersuader.model.artist.ArtistResponse
import com.example.thepersuader.model.release.ReleaseResponse
import com.example.thepersuader.model.releaseDetail.ReleaseDetailResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsApiService {
    @GET("artists/1")
    fun getArtist(): Observable<Response<ArtistResponse>>

    @GET("artists/1/releases?sort=year&sort_order=desc")
    fun getReleases(@Query("page") page: Int?, @Query("per_page") per_page: Int?): Observable<Response<ReleaseResponse>>

    @GET("releases/{id}")
    fun getReleaseDetail(@Path("id") id: Int?): Observable<Response<ReleaseDetailResponse>>
}

