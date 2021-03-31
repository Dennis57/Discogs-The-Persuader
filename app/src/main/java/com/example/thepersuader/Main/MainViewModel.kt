package com.example.thepersuader.Main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.Model.*
import com.example.thepersuader.Network.DiscogsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private var page = 1

    private val _releases = MutableLiveData<List<ReleaseUiModel>>()

    val releases: LiveData<List<ReleaseUiModel>>
        get() = _releases

    private val _artist = MutableLiveData<ArtistUiModel>()

    val artist: LiveData<ArtistUiModel>
        get() = _artist

    init {
        getArtist()
        getReleases()
    }

    @SuppressLint("CheckResult")
    private fun getArtist() {
        try {
            Log.e("<Error>", "Test fetch artist.")
            DiscogsApi.retrofitService.getArtist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful) {
                        it.body()?.let { artistResponse ->
                            val aliasesList: String = artistResponse.aliases?.map { alias -> alias.name }?.joinToString(", ") ?:"Unspecified"

                            val artistUiModel = ArtistUiModel(artistResponse.name.orEmpty(), artistResponse.real_name.orEmpty(), aliasesList)
                            _artist.value = artistUiModel
                            Log.e("<Error>", "Test fetch artist success." + _artist.value)
                        }

                    }
                },{
                    Log.e("<Error>", "Error:" + it.message)
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
        }
    }

    @SuppressLint("CheckResult")
    fun getReleases() {
        try {
            Log.e("<Error>", "Test fetch release.")
            DiscogsApi.retrofitService.getReleases(page, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful) {
                        it.body()?.let { releaseResponse ->
                            val releaseUiModels: MutableList<ReleaseUiModel> = mutableListOf()
                            for(response in releaseResponse.releases) {
                                val releaseUiModel = ReleaseUiModel(response.id?:0, response.name.orEmpty(), response.year?:0)
                                releaseUiModels.add(releaseUiModel)
                            }
                            _releases.value = releaseUiModels
                            page += 1
                        }
                    }

                    Log.e("<Error>", "Test fetch release success. " + _releases.value)
                },{

                    Log.e("<Error>", "Error fetch release:" + it.message)
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
        }
    }
}