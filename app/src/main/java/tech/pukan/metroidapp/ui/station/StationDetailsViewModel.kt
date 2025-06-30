package tech.pukan.metroidapp.ui.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import tech.pukan.metroidapp.data.model.Departure
import tech.pukan.metroidapp.data.model.Station
import tech.pukan.metroidapp.data.repository.TransportRepository
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class StationDetailsViewModel @Inject constructor(
    private val transportRepository: TransportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StationDetailsUiState())
    val uiState: StateFlow<StationDetailsUiState> = _uiState.asStateFlow()

    fun loadStation(stationId: String) {
        viewModelScope.launch {
            transportRepository.getStationById(stationId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                .collect { station ->
                    if (station != null) {
                        val currentTime = LocalTime.now()
                        val upcomingDepartures = station.departures
                            .filter { it.time.isAfter(currentTime) }
                            .sortedBy { it.time }
                            .take(10) // Show next 10 departures
                        
                        _uiState.value = _uiState.value.copy(
                            station = station,
                            upcomingDepartures = upcomingDepartures,
                            isLoading = false,
                            error = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Station not found"
                        )
                    }
                }
        }
    }
}

data class StationDetailsUiState(
    val station: Station? = null,
    val upcomingDepartures: List<Departure> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
) 