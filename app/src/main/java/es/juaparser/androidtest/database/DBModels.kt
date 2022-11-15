package es.juaparser.androidtest.database

import androidx.room.*


@Entity(tableName = "db_player")
data class DBPlayer(
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "height_feet")
    val heightFeet: Int?,
    @ColumnInfo(name = "height_inches")
    val heightInches: Int?,
    @PrimaryKey
    @ColumnInfo(name = "player_id")
    val id: Int?,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @ColumnInfo(name = "position")
    val position: String?,
    @ColumnInfo(name = "team_id")
    val team: Int?,
    @ColumnInfo(name = "weight_pounds")
    val weightPounds: Int?
)


@Entity
data class DBTeam(
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "conference")
    val conference: String?,
    @ColumnInfo(name = "division")
    val division: String?,
    @ColumnInfo(name = "full_name")
    val fullName: String?,
    @PrimaryKey
    @ColumnInfo(name = "teamId")
    val teamId: Int?,
    @ColumnInfo(name = "name")
    val name: String?
)

data class TeamWithPlayers(
    @Embedded val team: DBTeam,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "team_id"
    )
    val players: List<DBPlayer>
)