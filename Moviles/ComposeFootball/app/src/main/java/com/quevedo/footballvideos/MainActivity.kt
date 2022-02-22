package com.quevedo.footballvideos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.quevedo.footballvideos.ui.navigation.Navigation
import com.quevedo.footballvideos.ui.screens.main.MainContract
import com.quevedo.footballvideos.ui.screens.main.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewmodel by viewModels()
        viewModel.handleEvent(MainContract.Event.GetData)
        setContent {
            FootballApp {
                Navigation(viewModel)
            }
        }
    }
}