package tech.pukan.metroidapp.ui.timetables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.model.TransportType
import tech.pukan.metroidapp.data.repository.TransportRepository
import javax.inject.Inject

@HiltViewModel
class TimetablesViewModel @Inject constructor(
    private val transportRepository: TransportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimetablesUiState())
    val uiState: StateFlow<TimetablesUiState> = _uiState.asStateFlow()

    init {
        loadAllLines()
    }

    private fun loadAllLines() {
        viewModelScope.launch {
            transportRepository.getAllLines()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                .collect { lines ->
                    _uiState.value = _uiState.value.copy(
                        allLines = lines,
                        metroLines = lines.filter { it.type == TransportType.METRO },
                        tramLines = lines.filter { it.type == TransportType.TRAM },
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun searchLines(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(searchResults = emptyList())
            return
        }

        viewModelScope.launch {
            transportRepository.searchLines(query)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
                .collect { results ->
                    _uiState.value = _uiState.value.copy(searchResults = results)
                }
        }
    }

    fun clearSearch() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            searchResults = emptyList()
        )
    }

    fun selectTransportType(type: TransportType?) {
        _uiState.value = _uiState.value.copy(selectedTransportType = type)
    }
}

data class TimetablesUiState(
    val allLines: List<TransportLine> = emptyList(),
    val metroLines: List<TransportLine> = emptyList(),
    val tramLines: List<TransportLine> = emptyList(),
    val searchQuery: String = "",
    val searchResults: List<TransportLine> = emptyList(),
    val selectedTransportType: TransportType? = null,
    val isLoading: Boolean = true,
    val error: String? = null
) 