package es.juaparser.androidtest.database

import androidx.annotation.WorkerThread
import es.juaparser.androidtest.model.DBMapper
import es.juaparser.androidtest.model.Player

class PlayerRepository(
    private val playerDao: PlayerDao,
    private val teamDao: TeamDao,
    ) {

    fun getPlayers() = playerDao.getAll()
    fun getTeams() = teamDao.getAllTeams()


    @WorkerThread
    suspend fun insertPlayers(players: List<Player>) {
        playerDao.insertPlayers(*DBMapper.serviceToDatabase(players).toTypedArray())
        val teams = players.map { x -> x.team!! }.distinct()
        val dbTeams = DBMapper.teamServiceToDB(teams)
        teamDao.insertTeams(*dbTeams.toTypedArray())
    }

}