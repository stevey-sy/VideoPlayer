package com.example.youtubevideoplayer

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.youtubevideoplayer.network.Service.createRetrofit
import com.example.youtubevideoplayer.ui.theme.ItemList
import com.example.youtubevideoplayer.ui.theme.YouTubeVideoPlayerTheme
import com.example.youtubevideoplayer.viewModel.HomeViewModel
import com.example.youtubevideoplayer.viewModel.HomeViewModelFactory
import com.example.youtubevideoplayer.viewModel.MainViewModelFactory


class MainActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = createRetrofit()
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(retrofitService)).get(HomeViewModel::class.java)

        setContent {
            YouTubeVideoPlayerTheme {
                // 테마를 적용하여 화면 구성
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreenView(homeViewModel)

                }
            }
        }
    }
}

@Composable
fun MainScreenView(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController, homeViewModel = homeViewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    androidx.compose.material.BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { /* 각 아이템의 아이콘을 추가하세요 */ },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // 필요하면 추가적인 navigation options를 설정할 수 있습니다.
                    }
                },
                label = { Text(text = item.title) }
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val title: String) {
    object Home : BottomNavItem("home", "Home")
    object Search : BottomNavItem("search", "Search")
    object Profile : BottomNavItem("profile", "Profile")
}

@Composable
fun NavigationGraph(navController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(homeViewModel = homeViewModel)
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
    }
}

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val youtubeItems by homeViewModel.youtubeItems.observeAsState(emptyList())
    ItemList(viewModel = homeViewModel)
}

@Composable
fun SearchScreen() {

}

@Composable
fun ProfileScreen() {

}
