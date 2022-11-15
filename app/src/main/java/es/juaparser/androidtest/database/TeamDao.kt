package es.juaparser.androidtest.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Query("SELECT * FROM DBTeam")
    fun getAllTeams(): Flow<List<DBTeam>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(vararg teams: DBTeam): List<Long>
}