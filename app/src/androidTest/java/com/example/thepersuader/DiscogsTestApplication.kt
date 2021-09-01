package com.example.thepersuader

import android.app.Application
import android.content.Context
import com.example.thepersuader.component.DaggerAppTestComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DiscogsTestApplication : Application(), HasAndroidInjector {
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()

    DaggerAppTestComponent.builder()
      .application(this)
      .build()
      .inject(this)

    context = applicationContext
    INSTANCE = this
  }

  companion object {
    lateinit var INSTANCE : DiscogsTestApplication
    lateinit var context: Context
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }
}