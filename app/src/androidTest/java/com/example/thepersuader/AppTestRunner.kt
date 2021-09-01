package com.example.thepersuader

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class AppTestRunner: AndroidJUnitRunner() {
  override fun newApplication(
    cl: ClassLoader?,
    className: String?,
    context: Context?
  ): Application {
    return super.newApplication(cl, DiscogsTestApplication::class.java.name, context)
  }
}