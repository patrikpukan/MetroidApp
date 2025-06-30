package tech.pukan.metroidapp.ui.timetables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.model.TransportType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetablesScreen(
    onLineClick: (TransportLine) -> Unit,
    viewModel: TimetablesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::searchLines,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search lines or stations...") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = viewModel::clearSearch) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transport type filters
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    onClick = { viewModel.selectTransportType(null) },
                    label = { Text("All") },
                    selected = uiState.selectedTransportType == null
                )
            }
            item {
                FilterChip(
                    onClick = { viewModel.selectTransportType(TransportType.METRO) },
                    label = { Text("Metro") },
                    selected = uiState.selectedTransportType == TransportType.METRO
                )
            }
            item {
                FilterChip(
                    onClick = { viewModel.selectTransportType(TransportType.TRAM) },
                    label = { Text("Tram") },
                    selected = uiState.selectedTransportType == TransportType.TRAM
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            
            uiState.searchQuery.isNotEmpty() -> {
                // Show search results
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.searchResults) { line ->
                        TransportLineCard(
                            line = line,
                            onClick = { onLineClick(line) }
                        )
                    }
                }
            }
            
            else -> {
                // Show filtered content
                val linesToShow = when (uiState.selectedTransportType) {
                    TransportType.METRO -> uiState.metroLines
                    TransportType.TRAM -> uiState.tramLines
                    null -> uiState.allLines
                }
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (uiState.selectedTransportType == null || uiState.selectedTransportType == TransportType.METRO) {
                        if (uiState.metroLines.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Metro Lines",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            items(uiState.metroLines) { line ->
                                TransportLineCard(
                                    line = line,
                                    onClick = { onLineClick(line) }
                                )
                            }
                        }
                    }
                    
                    if (uiState.selectedTransportType == null || uiState.selectedTransportType == TransportType.TRAM) {
                        if (uiState.tramLines.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Tram Lines",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            items(uiState.tramLines) { line ->
                                TransportLineCard(
                                    line = line,
                                    onClick = { onLineClick(line) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransportLineCard(
    line: TransportLine,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Line indicator circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(line.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = line.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${line.type.name.lowercase().replaceFirstChar { it.uppercase() }} Line ${line.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${line.stations.size} stations",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 