package com.ksoc.harmonika

import MusicViewModel
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ksoc.harmonika.ui.theme.SearchScreen

class SearchActivity : AppCompatActivity() {

    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchScreen()
        }
    }
}
