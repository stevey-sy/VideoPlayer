package com.example.youtubevideoplayer

import MainViewModel
import android.os.Bundle
import android.provider.MediaStore.Video
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideoplayer.Network.Service.RetrofitService
import com.example.youtubevideoplayer.Network.Service.YoutubeItem
import com.example.youtubevideoplayer.ui.theme.ItemLayout
import com.example.youtubevideoplayer.ui.theme.YouTubeVideoPlayerTheme
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

class MainViewModelFactory(private val retrofitService: RetrofitService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(retrofitService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
    }
}

fun createRetrofit(): RetrofitService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://mellowcode.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(RetrofitService::class.java)
}

@Composable
fun ItemList(viewModel: MainViewModel) {
    val items by viewModel.youtubeItems.observeAsState(emptyList())

    LazyColumn {
        items(items) { item ->
            // 각 아이템을 커스텀 레이아웃으로 표시
            ItemLayout(item)
        }
    }
}
