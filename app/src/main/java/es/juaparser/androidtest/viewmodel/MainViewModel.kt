package es.juaparser.androidtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.juaparser.androidtest.api.PlayerService
import es.juaparser.androidtest.database.DBPlayer
import es.juaparser.androidtest.database.DBTeam
import es.juaparser.androidtest.database.PlayerRepository
import es.juaparser.androidtest.model.*
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * Main view model for main fragment
 *
 * Service is injected via Koin and calls GET products
 */
class MainViewModel(private val repository: PlayerRepository) : ViewModel() {

    private val service: PlayerService by inject(PlayerService::class.java)

    var allPlayers: List<DBPlayer> = mutableListOf()
    var allTeams: List<DBTeam> = mutableListOf()
    val playerSeason: MutableLiveData<SeasonAverage> = MutableLiveData()

    val loadedData = MutableLiveData(false)

    init {
        if(allPlayers.isEmpty()) getDBPlayers()
        if(allTeams.isEmpty()) getDBTeams()
    }

    fun getDBPlayers() {
        viewModelScope.launch {
            repository.getPlayers().collect {
                allPlayers = it
            }
        }
    }

    fun getDBTeams() {
        viewModelScope.launch {
            repository.getTeams().collect {
                allTeams = it
                loadedData.postValue(true)
            }
        }
    }

    fun insertPlayers(players: List<Player>) = viewModelScope.launch {
        repository.insertPlayers(players)
    }

    fun getPlayersList(pageNumber: Int? = null, players: (List<Player>) -> Unit) {
        service.getPlayersList(pageNumber) {
            players(it?: mutableListOf())
        }
    }

    fun getPlayerSeason(playerId: Int) {
        service.getPlayerSeason(playerId) {
            playerSeason.postValue(it)
        }
    }

    fun getPlayer(playerId: Int): Player {
        val player = allPlayers.first { x -> x.id == playerId }
        val team = allTeams.first { x -> x.teamId == player.team }
        return DBMapper.databaseToService(
            listOf(player),
            listOf(team)
        ).first()
    }
}