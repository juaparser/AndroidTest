package es.juaparser.androidtest.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM db_player")
    fun getAll(): Flow<List<DBPlayer>>

    @Query("SELECT * FROM db_player WHERE player_id IS :playerId")
    fun findById(playerId: Int): Flow<DBPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(vararg players: DBPlayer): List<Long>
}