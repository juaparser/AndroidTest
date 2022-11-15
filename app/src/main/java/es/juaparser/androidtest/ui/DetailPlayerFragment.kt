package es.juaparser.androidtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import es.juaparser.androidtest.model.Player
import es.juaparser.androidtest.model.SeasonAverage
import es.juaparser.androidtest.ui.theme.MainTheme
import es.juaparser.androidtest.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPlayerFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()
    private var playerInfo: Player? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerId = arguments?.getInt("playerId")!!
        viewModel.getPlayerSeason(playerId)

        playerInfo = viewModel.getPlayer(playerId)

        (view as ComposeView).setContent {

            val playerSeason = viewModel.playerSeason.observeAsState()

            MainTheme {

                Column {


                    TopAppBar(modifier = Modifier.height(60.dp)) {

                        Row(Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        activity?.onBackPressed()
                                    }
                            )

                            Text(
                                text = "Player detail",
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                                color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    MainView(playerSeason.value)
                }
            }
        }
    }

    @Composable
    fun MainView(seasonAverage: SeasonAverage?) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)) {

            if(playerInfo == null) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
            } else {

                Text(
                    text = "First section",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp),
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                
                Column(Modifier.padding(horizontal = 16.dp)) {

                    Text(
                        text = playerInfo?.firstName + " " + playerInfo?.lastName,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "Position: " + playerInfo?.position,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    playerInfo?.heightFeet?.let {
                        Text(
                            text = "Height feet: " + playerInfo?.heightFeet,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    playerInfo?.weightPounds?.let {
                        Text(
                            text = "Weight pounds: " + playerInfo?.weightPounds,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                }



                Text(
                    text = "Second section",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp),
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Row(Modifier.fillMaxWidth()) {
                    val image = if("West" == playerInfo?.team?.conference)
                        "https://content.sportslogos.net/logos/6/1001/full/2996.gif"
                    else
                        "https://content.sportslogos.net/logos/6/999/full/2995.gif"

                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = playerInfo?.team?.conference,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                    Column(Modifier.padding(horizontal = 16.dp).weight(1f)) {

                        Text(
                            text = playerInfo?.team?.fullName ?: "Missing team name",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            text = playerInfo?.team?.abbreviation ?: "Mssing team abrev",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            text = "City: " + playerInfo?.team?.city,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                    }
                }
                Text(
                    text = "Third section",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp),
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )

                if(seasonAverage == null) {
                    Text(
                        text = "No season average info.",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    Column(Modifier.padding(horizontal = 16.dp)) {

                        Text(
                            text = "Games played: " + seasonAverage.gamesPlayed,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            text = "Minutes: " + seasonAverage.min,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            text = "Average points: " + seasonAverage.pts,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                    }

                }
            }
        }
    }
    
    

}