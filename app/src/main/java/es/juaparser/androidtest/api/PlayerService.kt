package es.juaparser.androidtest.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import es.juaparser.androidtest.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_API_URL = "https://www.balldontlie.io/api/"


/**
 * Service to manage the endpoints response and send it to viewmodels
 */
class PlayerService: KoinComponent {

    private var gson: Gson = GsonBuilder().create()
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Retrofit builder with Logging interceptor to see response in logcat.
     */
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService: PlayerApi = retrofit.create(PlayerApi::class.java)

    /**
     * Service call to endpoint to get players list, callback with results
     */
    fun getPlayersList(pageNumber: Int?= null, res: (players: List<Player>?) -> Unit) {
        val call: Call<PlayerListResponse> = apiService.getPlayersList(pageNumber?:1)
        call.enqueue(object : Callback<PlayerListResponse> {
            override fun onResponse(call: Call<PlayerListResponse>, response: Response<PlayerListResponse>) {
                val data = response.body()
                if (data != null) {
                    res(data.data)
                }
            }

            override fun onFailure(call: Call<PlayerListResponse>, t: Throwable) {
                // Log error here since request failed
                t.printStackTrace()
            }
        })
    }

    /**
     * Service call to endpoint to get players season average, callback with results
     */
    fun getPlayerSeason(playerId: Int, res: (season: SeasonAverage?) -> Unit) {
        val call: Call<SeasonResponse> = apiService.getPlayerSeason(playerId)
        call.enqueue(object : Callback<SeasonResponse> {
            override fun onResponse(call: Call<SeasonResponse>, response: Response<SeasonResponse>) {
                val data = response.body()
                if (data != null) {
                    res(data.seasonAverage.firstOrNull())
                }
            }

            override fun onFailure(call: Call<SeasonResponse>, t: Throwable) {
                // Log error here since request failed
                t.printStackTrace()
            }
        })
    }


}