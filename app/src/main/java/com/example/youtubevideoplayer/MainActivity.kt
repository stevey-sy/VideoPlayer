package com.example.youtubevideoplayer

import android.os.Bundle
import android.provider.MediaStore.Video
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideoplayer.data.VideoItem
import com.example.youtubevideoplayer.ui.theme.ItemLayout
import com.example.youtubevideoplayer.ui.theme.YouTubeVideoPlayerTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YouTubeVideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ItemList(
                        listOf(
                            VideoItem(1, "Item 1"),
                            VideoItem(2, "Item 2"),
                            VideoItem(3, "Item 3"),
                            VideoItem(4, "Item 4"),
                            VideoItem(5, "Item 5"),
                        )
                    )
                }
            }
        }
    }

    val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()



}

@Composable
fun ItemList(items: List<VideoItem>) {
    LazyColumn {
        items(items) { item ->
            // 각 아이템을 커스텀 레이아웃으로 표시
            ItemLayout(item)
        }
    }
}
