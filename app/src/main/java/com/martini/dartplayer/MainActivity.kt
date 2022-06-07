package com.martini.dartplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.FirebaseApp
import com.martini.dartplayer.domain.usecases.ExitAppAction
import com.martini.dartplayer.domain.usecases.ExitAppUseCase
import com.martini.dartplayer.domain.usecases.PlayRequestedSongUseCase
import com.martini.dartplayer.domain.usecases.files.InitializePreferencesUseCase
import com.martini.dartplayer.services.ScanMusicService
import com.martini.dartplayer.services.playerService.PlayerService
import com.martini.dartplayer.ui.theme.DartPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var initializePreferencesUseCase: InitializePreferencesUseCase

    @Inject
    lateinit var exitAppUseCase: ExitAppUseCase

    @Inject
    lateinit var playRequestedSongUseCase: PlayRequestedSongUseCase


    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startFirebase()
        startSettings()
        listenToExit()

        setContent {
            DartPlayerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SetupNavigation()
                }
            }
        }

        handleIntent()
    }

    override fun onStart() {
        Intent(this, PlayerService::class.java).also { intent ->
            startService(intent)
        }
        Intent(this, ScanMusicService::class.java).also {
            startService(it)
        }
        super.onStart()
    }

    private fun handleIntent() {
        playRequestedSongUseCase(intent)
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun startFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun startSettings() {
        initializePreferencesUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(lifecycleScope)
    }

    private fun listenToExit() {
        exitAppUseCase.listen
            .onEach {
                if (it is ExitAppAction.Exit) {
                    this.finishAffinity()
                }
            }
            .launchIn(lifecycleScope)
    }

}