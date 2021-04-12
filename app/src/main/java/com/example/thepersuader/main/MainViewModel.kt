package com.example.thepersuader.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.DiscogsApplication
import com.example.thepersuader.model.artist.ArtistUiModel
import com.example.thepersuader.model.release.ReleaseUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.ArtistRepository
import com.example.thepersuader.repository.ReleaseRepository
import com.example.thepersuader.room.DiscogsDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val artistRepository: ArtistRepository, private val releaseRepository: ReleaseRepository) : ViewModel() {

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

//    private val discogsDatabase = DiscogsDatabase.getInstance(DiscogsApplication.context)
//    private val discogsDatabaseDao = discogsDatabase.discogsDatabaseDao

    init {
        getArtist()
        getReleases()
    }

    @SuppressLint("CheckResult")
    private fun getArtist() {
//        try {
            artistRepository.getArtist(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Log.e("<Error>", "Artist onsubscribe")
                    _showLoading.value = true }
                .doOnError {
                    Log.e("<Error>", "Artist onError")
                    _artistError.value = true
                }
                .subscribe({
                    Log.e("<Error>", "Artist name: " + it.name)
                    if (it != null) {
                        _artist.value = it
                    } else {
                        _artistError.value = true
                    }
                    _showLoading.value = false
                }, {
                    Log.e("<Error>", "Error: " + it.message)
                    _showLoading.value = false
                })
//        } catch (e: Exception) {
//            Log.e("<Error>", "Error: " + e.message)
//        }

    }

    private var releaseUiModels: MutableList<ReleaseUiModel> = mutableListOf()

    @SuppressLint("CheckResult")
    fun getReleases() {
        Log.e("<Error>", "GetReleases() is called")
        try {
//            DiscogsApi.retrofitService.getReleases(page, 20)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    if (it.isSuccessful) {
//                        it.body()?.let { releaseResponse ->
//                            for (response in releaseResponse.releases) {
//                                val releaseUiModel = ReleaseUiModel(
//                                    response.id ?: 0,
//                                    response.name.orEmpty(),
//                                    response.year ?: 0
//                                )
//                                releaseUiModels.add(releaseUiModel)
//                            }
//                            _releases.value = releaseUiModels
//
//                            page += 1
//                        }
//                    }
//                }, {
//                    Log.e("<Error>", "Error fetch release:" + it.message)
//                })
            releaseRepository.getReleases(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _releases.value = it
                    Log.e("<Error>", "Releases:${it.size}")
                }, {
                    Log.e("<Error>", "Error fetch release:" + it.message)
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
        }
    }
}