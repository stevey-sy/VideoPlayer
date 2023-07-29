package com.example.youtubevideoplayer

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideoplayer.Network.Service.RetrofitService
import com.example.youtubevideoplayer.Network.Service.YoutubeItem
import com.example.youtubevideoplayer.data.VideoItem
import com.example.youtubevideoplayer.ui.theme.ItemLayout
import com.example.youtubevideoplayer.ui.theme.YouTubeVideoPlayerTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = createRetrofit()
        retrofitService.getYoutubeItemList().enqueue(object : Callback<ArrayList<YoutubeItem>> {
            override fun onResponse(call: Call<ArrayList<YoutubeItem>>, response: Response<ArrayList<YoutubeItem>>) {
                if (response.isSuccessful) {
                    val youtubeItems = response.body()
                    setContent {
                        YouTubeVideoPlayerTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                                ItemList(youtubeItems ?: emptyList()) // Retrofit으로 받은 비디오 아이템 목록을 넘겨줍니다.
                            }
                        }
                    }
                    // 가져온 비디오 아이템 목록(youtubeItems)을 사용하면 됩니다.
                } else {
                    // 요청은 성공했으나 서버에서 실패한 경우 처리
                }
            }

            override fun onFailure(call: Call<ArrayList<YoutubeItem>>, t: Throwable) {
                Log.d("TEST", "onFailure: ")
                // 요청 실패 처리
            }
        })

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
fun ItemList(items: List<YoutubeItem>) {
    LazyColumn {
        items(items) { item ->
            // 각 아이템을 커스텀 레이아웃으로 표시
            ItemLayout(item)
        }
    }
}
