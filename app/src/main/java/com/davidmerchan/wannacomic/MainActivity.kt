package com.davidmerchan.wannacomic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.davidmerchan.home.presentation.HomeScreen
import com.davidmerchan.presentation.ComicDetailScreen
import com.davidmerchan.presentation.ShoppingCartScreen
import com.davidmerchan.wannacomic.ui.theme.WannaComicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            WannaComicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home) {
        composable<Screen.Home> {
            HomeScreen { id ->
                navController.navigate(Screen.ComicDetail(id))
            }
        }
        composable<Screen.ComicDetail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Screen.ComicDetail>()
            ComicDetailScreen(comicId = detail.id) {
                navController.popBackStack()
            }
        }
        composable<Screen.ShoppingCart> {
            ShoppingCartScreen()
        }
    }
}
