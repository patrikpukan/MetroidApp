package tech.pukan.metroidapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.pukan.metroidapp.data.model.Station
import tech.pukan.metroidapp.data.model.TransportLine
import tech.pukan.metroidapp.data.model.TransportType
import tech.pukan.metroidapp.data.mock.MockData
import javax.inject.Inject
import javax.inject.Singleton

interface TransportRepository {
    fun getAllLines(): Flow<List<TransportLine>>
    fun getLinesByType(type: TransportType): Flow<List<TransportLine>>
    fun getLineById(id: String): Flow<TransportLine?>
    fun getStationById(stationId: String): Flow<Station?>
    fun searchStations(query: String): Flow<List<Station>>
    fun searchLines(query: String): Flow<List<TransportLine>>
}

@Singleton
class TransportRepositoryImpl @Inject constructor() : TransportRepository {
    
    override fun getAllLines(): Flow<List<TransportLine>> = flow {
        emit(MockData.allLines)
    }
    
    override fun getLinesByType(type: TransportType): Flow<List<TransportLine>> = flow {
        emit(MockData.allLines.filter { it.type == type })
    }
    
    override fun getLineById(id: String): Flow<TransportLine?> = flow {
        emit(MockData.allLines.find { it.id == id })
    }
    
    override fun getStationById(stationId: String): Flow<Station?> = flow {
        val station = MockData.allLines
            .flatMap { it.stations }
            .find { it.id == stationId }
        emit(station)
    }
    
    override fun searchStations(query: String): Flow<List<Station>> = flow {
        val stations = MockData.allLines
            .flatMap { it.stations }
            .filter { it.name.contains(query, ignoreCase = true) }
        emit(stations)
    }
    
    override fun searchLines(query: String): Flow<List<TransportLine>> = flow {
        val lines = MockData.allLines.filter { line ->
            line.name.contains(query, ignoreCase = true) ||
            line.stations.any { it.name.contains(query, ignoreCase = true) }
        }
        emit(lines)
    }
} 