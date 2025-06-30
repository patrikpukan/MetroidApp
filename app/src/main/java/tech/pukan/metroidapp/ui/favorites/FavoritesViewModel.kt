package tech.pukan.metroidapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.pukan.metroidapp.data.model.Station
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.repository.TransportRepository
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val transportRepository: TransportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()
    
    // In-memory storage for favorites (in a real app, this would use Room/DataStore)
    private val favoriteStationIds = mutableSetOf<String>()
    private val favoriteLineIds = mutableSetOf<String>()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            transportRepository.getAllLines().collect { allLines ->
                val favoriteStations = allLines
                    .flatMap { it.stations }
                    .filter { it.id in favoriteStationIds }
                    
                val favoriteLines = allLines.filter { it.id in favoriteLineIds }
                
                _uiState.value = _uiState.value.copy(
                    favoriteStations = favoriteStations,
                    favoriteLines = favoriteLines,
                    isLoading = false
                )
            }
        }
    }

    fun addStationToFavorites(station: Station) {
        favoriteStationIds.add(station.id)
        loadFavorites()
    }

    fun removeStationFromFavorites(station: Station) {
        favoriteStationIds.remove(station.id)
        loadFavorites()
    }

    fun addLineToFavorites(line: TransportLine) {
        favoriteLineIds.add(line.id)
        loadFavorites()
    }

    fun removeLineFromFavorites(line: TransportLine) {
        favoriteLineIds.remove(line.id)
        loadFavorites()
    }

    fun isStationFavorite(stationId: String): Boolean {
        return stationId in favoriteStationIds
    }

    fun isLineFavorite(lineId: String): Boolean {
        return lineId in favoriteLineIds
    }
}

data class FavoritesUiState(
    val favoriteStations: List<Station> = emptyList(),
    val favoriteLines: List<TransportLine> = emptyList(),
    val isLoading: Boolean = true
) 