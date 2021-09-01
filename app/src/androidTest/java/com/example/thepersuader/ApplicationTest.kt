package com.example.thepersuader

import androidx.test.core.app.ActivityScenario
import com.example.thepersuader.main.MainActivity
import org.junit.Test

class ApplicationTest {

    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}