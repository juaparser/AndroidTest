package es.juaparser.androidtest.api

import es.juaparser.androidtest.model.PlayerListResponse
import es.juaparser.androidtest.model.SeasonResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface with endpoints
 */
interface PlayerApi {

    /**
     * GET PLAYERS
     *
     * Retrieves a list of players
     * @param pageNumber: Page to load new players
     */
    @GET("v1/players")
    fun getPlayersList(@Query("page") pageNumber: Int): Call<PlayerListResponse>

    /**
     * GET PLAYER DETAIL
     *
     * @param playerId Id of the player
     *
     * Retrieves player's season average
     */
    @GET("v1/season_averages")
    fun getPlayerSeason(@Query("player_ids[]", encoded = true) playerId: Int): Call<SeasonResponse>

}