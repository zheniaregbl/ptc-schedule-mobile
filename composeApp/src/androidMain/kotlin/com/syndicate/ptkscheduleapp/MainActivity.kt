package com.syndicate.ptkscheduleapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.work.WorkManager
import com.syndicate.ptkscheduleapp.widget.worker.setUpWorker

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle
                .light(
                    scrim = Color.TRANSPARENT,
                    darkScrim = Color.TRANSPARENT
                ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        setContent {

            LaunchedEffect(Unit) {
                val workManager = WorkManager.getInstance(applicationContext)
                setUpWorker(workManager)
            }

            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) { App() }
        }
    }
}