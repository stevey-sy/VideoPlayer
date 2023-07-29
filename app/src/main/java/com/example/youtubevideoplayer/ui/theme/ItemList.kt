package com.example.youtubevideoplayer.ui.theme

import MainViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

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