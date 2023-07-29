package com.example.youtubevideoplayer

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.youtubevideoplayer.network.Service.RetrofitService
import com.example.youtubevideoplayer.network.Service.createRetrofit
import com.example.youtubevideoplayer.ui.theme.ItemList
import com.example.youtubevideoplayer.ui.theme.YouTubeVideoPlayerTheme
import com.example.youtubevideoplayer.viewModel.MainViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = createRetrofit()
        viewModel = ViewModelProvider(this, MainViewModelFactory(retrofitService)).get(MainViewModel::class.java)

        setContent {
            YouTubeVideoPlayerTheme {
                // 테마를 적용하여 화면 구성
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // ViewModel에서 가져온 데이터를 사용하여 화면 구성
                    ItemList(viewModel)
                }
            }
        }
    }
}

