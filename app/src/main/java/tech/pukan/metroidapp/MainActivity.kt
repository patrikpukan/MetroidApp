package tech.pukan.metroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import dagger.hilt.android.AndroidEntryPoint
import tech.pukan.metroidapp.core.navigation.Screen
import tech.pukan.metroidapp.core.navigation.bottomNavItems
import tech.pukan.metroidapp.core.theme.PragueMetroTheme
import tech.pukan.metroidapp.ui.favorites.FavoritesScreen
import tech.pukan.metroidapp.ui.map.MapScreen
import tech.pukan.metroidapp.ui.station.StationDetailsScreen
import tech.pukan.metroidapp.ui.timetables.TimetablesScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PragueMetroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Timetables.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Timetables.route) {
                TimetablesScreen(
                    onLineClick = { line ->
                        // Navigate to the first station of the line with proper error handling
                        if (line.stations.isNotEmpty()) {
                            navController.navigate("station/${line.stations.first().id}")
                        }
                    }
                )
            }
            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onLineClick = { line ->
                        // Navigate to the first station of the line with proper error handling
                        if (line.stations.isNotEmpty()) {
                            navController.navigate("station/${line.stations.first().id}")
                        }
                    },
                    onStationClick = { station ->
                        navController.navigate("station/${station.id}")
                    }
                )
            }
            composable(
                route = "station/{stationId}",
                arguments = listOf(navArgument("stationId") { type = NavType.StringType })
            ) { backStackEntry ->
                val stationId = backStackEntry.arguments?.getString("stationId")
                if (stationId != null) {
                    StationDetailsScreen(
                        stationId = stationId,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                } else {
                    // Handle the case where stationId is null - navigate back
                    navController.popBackStack()
                }
            }
        }
    }
}