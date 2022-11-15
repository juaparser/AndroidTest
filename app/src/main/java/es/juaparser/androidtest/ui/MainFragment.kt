package es.juaparser.androidtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import es.juaparser.androidtest.R
import es.juaparser.androidtest.model.DBMapper
import es.juaparser.androidtest.model.Player
import es.juaparser.androidtest.ui.theme.MainTheme
import es.juaparser.androidtest.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allDbPlayers = mutableListOf<Player>()

        (view as ComposeView).setContent {
            MainTheme {
                // A surface container using the 'background' color from the theme
                val players = remember { mutableStateListOf<Player>() }
                val pageNumber = remember { mutableStateOf(1) }
                val newLimit = 25 * pageNumber.value
                var showLoader by remember { mutableStateOf(false) }

                viewModel.loadedData.observe(viewLifecycleOwner) {
                    if(it && allDbPlayers.isEmpty()) {
                        if(viewModel.allPlayers.isEmpty()) {
                            viewModel.getPlayersList { list ->
                                allDbPlayers.addAll(list)
                                players.addAll(list)
                                viewModel.insertPlayers(list)
                            }
                        } else {
                            allDbPlayers.addAll(DBMapper.databaseToService(
                                viewModel.allPlayers,
                                viewModel.allTeams)
                            )
                            players.addAll(allDbPlayers.subList(0,25))
                        }
                    }
                }

                pageNumber.value.let { page ->
                    if(page > 1) {
                        if(allDbPlayers.size > newLimit) {
                            players.clear()
                            players.addAll(allDbPlayers.subList(newLimit-25, newLimit))
                        } else {
                            showLoader = true
                            viewModel.getPlayersList(page) {
                                showLoader = false
                                allDbPlayers.addAll(it)
                                players.clear()
                                players.addAll(it)
                                viewModel.insertPlayers(it)
                            }
                        }
                    }
                }


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(players,pageNumber)

                    if(showLoader) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray.copy(alpha = 0.5f))) {
                            CircularProgressIndicator(
                                Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainView(playersList: MutableList<Player>, pageNumber: MutableState<Int>) {

        Column(Modifier.fillMaxSize()) {

            Text(
                text = "Player list",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            if (playersList.isEmpty()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    for (player in playersList) {
                        item {
                            ProductView(player)
                        }
                    }
                    item {
                        Text(text = "Page ${pageNumber.value}",
                        modifier = Modifier.padding(16.dp))
                    }
                    item {
                        Button(onClick = { pageNumber.value++ }, modifier = Modifier.padding(16.dp)) {
                            Text(text = "Next page >")
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun ProductView(item: Player) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.activity_main_frame_layout,
                        DetailPlayerFragment::class.java,
                        bundleOf("playerId" to item.id))
                    .addToBackStack(null)
                    .commit()
            }) {

            val image = if("West" == item.team?.conference)
                "https://content.sportslogos.net/logos/6/1001/full/2996.gif"
            else
                "https://content.sportslogos.net/logos/6/999/full/2995.gif"

            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = item.team?.conference,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            Column(
                Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {

                Text(
                    text = "${item.firstName} ${item.lastName}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Position: ${item.position}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = item.team?.fullName?:"Unknown",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.padding(end = 24.dp)
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.DarkGray)
        )
    }

}