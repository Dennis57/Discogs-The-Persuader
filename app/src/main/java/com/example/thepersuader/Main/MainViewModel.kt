package com.example.thepersuader.Main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.Model.Artist.ArtistUiModel
import com.example.thepersuader.Model.Release.ReleaseUiModel
import com.example.thepersuader.Network.DiscogsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private var page = 1

    private var _releases = MutableLiveData<List<ReleaseUiModel>>()

    val releases: LiveData<List<ReleaseUiModel>>
        get() = _releases

    private var _artist = MutableLiveData<ArtistUiModel>()

    val artist: LiveData<ArtistUiModel>
        get() = _artist

    private var _showLoading = MutableLiveData<Boolean>()

    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private var _artistError = MutableLiveData<Boolean>()

    val artistError: LiveData<Boolean>
        get() = _artistError

    init {
        getArtist()
        getReleases()
    }

    @SuppressLint("CheckResult")
    private fun getArtist() {
        try {
            DiscogsApi.retrofitService.getArtist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _showLoading.value = true }
                .doOnTerminate { _showLoading.value = false }
                .subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { artistResponse ->
                            val aliasesList: String =
                                artistResponse.aliases?.map { alias -> alias.name }
                                    ?.joinToString(", ") ?: "Unspecified"

                            val artistUiModel =
                                ArtistUiModel(
                                    artistResponse.name.orEmpty(),
                                    artistResponse.real_name.orEmpty(),
                                    aliasesList
                                )
                            _artist.value = artistUiModel
                        }
                    }
                }, {
                    Log.e("<Error>", "Error:" + it.message)
                    _artistError.value = true
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
            _artistError.value = true
        }

    }

    private var releaseUiModels: MutableList<ReleaseUiModel> = mutableListOf()

    @SuppressLint("CheckResult")
    fun getReleases() {
        try {
            DiscogsApi.retrofitService.getReleases(page, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { releaseResponse ->
                            for (response in releaseResponse.releases) {
                                val releaseUiModel = ReleaseUiModel(
                                    response.id ?: 0,
                                    response.name.orEmpty(),
                                    response.year ?: 0
                                )
                                releaseUiModels.add(releaseUiModel)
                            }
                            _releases.value = releaseUiModels

                            page += 1
                        }
                    }
                }, {
                    Log.e("<Error>", "Error fetch release:" + it.message)
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
        }
    }
}