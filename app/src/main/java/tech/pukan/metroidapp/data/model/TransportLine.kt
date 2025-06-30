package tech.pukan.metroidapp.data.model

import androidx.compose.ui.graphics.Color
import tech.pukan.metroidapp.core.theme.MetroGreen
import tech.pukan.metroidapp.core.theme.MetroRed
import tech.pukan.metroidapp.core.theme.MetroYellow
import tech.pukan.metroidapp.core.theme.TramBlue
import java.time.LocalTime

data class TransportLine(
    val id: String,
    val name: String,
    val type: TransportType,
    val color: Color,
    val stations: List<Station>
)

data class Station(
    val id: String,
    val name: String,
    val departures: List<Departure>
)

data class Departure(
    val time: LocalTime,
    val destination: String,
    val platform: String? = null
)

enum class TransportType {
    METRO, TRAM
}

// Extension function to get color by metro line
fun getMetroLineColor(lineName: String): Color {
    return when (lineName) {
        "A" -> MetroGreen
        "B" -> MetroYellow
        "C" -> MetroRed
        else -> TramBlue
    }
} 