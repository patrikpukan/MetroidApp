package tech.pukan.metroidapp.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.repository.TransportRepository
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val transportRepository: TransportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadLines()
    }

    private fun loadLines() {
        viewModelScope.launch {
            transportRepository.getAllLines().collect { lines ->
                _uiState.value = _uiState.value.copy(
                    lines = lines,
                    isLoading = false
                )
            }
        }
    }
}

data class MapUiState(
    val lines: List<TransportLine> = emptyList(),
    val isLoading: Boolean = true
) 