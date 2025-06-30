package tech.pukan.metroidapp.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Timetables : Screen("timetables", "Timetables", Icons.Filled.DirectionsTransit)
    object Map : Screen("map", "Map", Icons.Filled.LocationOn)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.Favorite)
}

val bottomNavItems = listOf(
    Screen.Timetables,
    Screen.Map,
    Screen.Favorites
) 