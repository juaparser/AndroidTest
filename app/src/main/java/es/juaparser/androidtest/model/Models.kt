package es.juaparser.androidtest.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

/**
 * Model for GET products response, with list of clusters
 */
data class PlayerListResponse(
    @SerializedName("data")
    val data: List<Player>?,
    @SerializedName("meta")
    val meta: Meta?
)

data class Player(
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("height_feet")
    val heightFeet: Int?,
    @SerializedName("height_inches")
    val heightInches: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("team")
    val team: Team?,
    @SerializedName("weight_pounds")
    val weightPounds: Int?
)

data class Meta(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)

data class Team(
    @SerializedName("abbreviation")
    val abbreviation: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("conference")
    val conference: String?,
    @SerializedName("division")
    val division: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("id")
    val teamId: Int?,
    @SerializedName("name")
    val name: String?
)

data class SeasonResponse(
    @SerializedName("data")
    val seasonAverage: List<SeasonAverage>
)

data class SeasonAverage(
    @SerializedName("ast")
    val ast: Double?,
    @SerializedName("blk")
    val blk: Double?,
    @SerializedName("dreb")
    val dreb: Double?,
    @SerializedName("fg3_pct")
    val fg3Pct: Double?,
    @SerializedName("fg3a")
    val fg3a: Double?,
    @SerializedName("fg3m")
    val fg3m: Double?,
    @SerializedName("fg_pct")
    val fgPct: Double?,
    @SerializedName("fga")
    val fga: Double?,
    @SerializedName("fgm")
    val fgm: Double?,
    @SerializedName("ft_pct")
    val ftPct: Double?,
    @SerializedName("fta")
    val fta: Double?,
    @SerializedName("ftm")
    val ftm: Double?,
    @SerializedName("games_played")
    val gamesPlayed: Int?,
    @SerializedName("min")
    val min: String?,
    @SerializedName("oreb")
    val oreb: Double?,
    @SerializedName("pf")
    val pf: Double?,
    @SerializedName("player_id")
    val playerId: Int?,
    @SerializedName("pts")
    val pts: Double?,
    @SerializedName("reb")
    val reb: Double?,
    @SerializedName("season")
    val season: Int?,
    @SerializedName("stl")
    val stl: Double?,
    @SerializedName("turnover")
    val turnover: Double?
)