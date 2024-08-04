package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.R
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.ui.theme.string_generico
import com.example.yourpoints.presentation.ui.theme.string_home_delete_games
import com.example.yourpoints.presentation.ui.theme.string_home_game_generic
import com.example.yourpoints.presentation.ui.theme.string_home_game_truco
import com.example.yourpoints.presentation.ui.theme.string_home_select_all_game
import com.example.yourpoints.presentation.ui.theme.string_home_without_games
import com.example.yourpoints.presentation.ui.theme.string_truco

private const val TAG = "HomeScreen Intern Test"

@Composable
fun HomeScreen (
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToTruco: (Int) -> Unit,
    navigateToGenerico: (Int) -> Unit,
    navigateToGenerala: (Int) -> Unit
) {
    var showDialog by remember{ mutableStateOf(false) }
    val uiState by homeViewModel.uiState.collectAsState()
    val games by homeViewModel.games.collectAsState()
    val gameSelected by homeViewModel.gameSelected.collectAsState()


    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){

        when(uiState){
            HomeViewState.LOADING -> Loading(Modifier.align(Alignment.Center))
            HomeViewState.SUCCESS -> {
                Body(
                    modifier = Modifier.fillMaxSize(),
                    games = games,
                    gameSelected = gameSelected,
                    onTap = {index ->
                        if (gameSelected){
                            Log.i(TAG, "HomeScreen: Selected Game $index")
                            homeViewModel.selectGame(index)
                        } else {
                            Log.i(TAG, "HomeScreen: onJoinGame ${games[index]}")
                            when(games[index]){
                                is TrucoUi -> { homeViewModel.navigateTo(navigateToTruco, (games[index] as TrucoUi).id)}
                                is GenericoUi -> {homeViewModel.navigateTo(navigateToGenerico, (games[index] as GenericoUi).id)}
                                else -> {}
                            }
                        }
                     },
                    onLongPress = {index ->
                        Log.i(TAG, "HomeScreen: Selected Game $index")
                        homeViewModel.selectGame(index)
                    },
                    onSelectAllGames = {
                        Log.i(TAG, "HomeScreen: Select All game")
                        homeViewModel.selectAll()
                    },
                    onDeleteGames = {
                        Log.i(TAG, "HomeScreen: Select All game")
                        homeViewModel.deleteGames()
                    },
                )
                StartAnnotator(Modifier.align(Alignment.BottomEnd)){showDialog = true}
            }
            HomeViewState.ERROR -> {
                SinGames(Modifier.align(Alignment.Center))
                StartAnnotator(Modifier.align(Alignment.BottomEnd)){showDialog = true}
            }
        }

        if (showDialog) {
            DialogSelectAnnotator(
                onDismissRequest = { showDialog = false },
                onClickAnnotatorGenerico = {homeViewModel.navigateTo(navigateToGenerico)},
                onClickAnnotatorTruco = {homeViewModel.navigateTo(navigateToTruco)},
                onClickAnnotatorGenerala = {homeViewModel.navigateTo(navigateToGenerala) }
            )
        }
    }
}

@Composable
fun Loading(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(38.dp),
            color = MaterialTheme.colorScheme.onBackground,
            strokeWidth = 2.dp
        )
    }
}


@Composable
fun Body(
    modifier: Modifier = Modifier,
    games: List<Any>,
    gameSelected:Boolean,
    onTap: (Int) -> Unit,
    onLongPress: (Int) -> Unit,
    onSelectAllGames: () -> Unit,
    onDeleteGames:() -> Unit
){
    Column(modifier = modifier) {
        if (gameSelected){
            OptionBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                onSelectAllGames = onSelectAllGames,
                onDeleteGames = onDeleteGames
            )
        }
        Games(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            games = games,
            onTap = onTap,
            onLongPress = onLongPress
        )
    }
}

@Composable
fun OptionBar(
    modifier: Modifier = Modifier,
    onSelectAllGames: () -> Unit,
    onDeleteGames:() -> Unit
){
    Row (
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            modifier = Modifier.clickable {
                onSelectAllGames()
            },
            text = string_home_select_all_game,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.clickable {
                onDeleteGames()
            },
            text = string_home_delete_games,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
fun Games(modifier:Modifier = Modifier, games: List<Any>, onTap: (Int) -> Unit, onLongPress:(Int) -> Unit) {
    Column(modifier.background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
        if (games.isNotEmpty()) {
            LazyColumn{
                Log.i(TAG, "Game.size: ${games.size}")
                items(games.size) {index ->
                    Log.i(TAG, "Game[$index]: ${games[index]}")
                    if (games[index] is TrucoUi){
                        ItemTruco(
                            index = index,
                            game = games[index] as TrucoUi,
                            onTap = onTap,
                            onLongPress = onLongPress
                        )
                    } else if (games[index] is GenericoUi){
                        ItemGenerico(
                            index = index,
                            game = games[index] as GenericoUi,
                            onTap = onTap,
                            onLongPress = onLongPress
                        )
                    } else {
                        Log.i(TAG, "Game ignorado")
                    }
                }
            }
        }
    }
}
@Composable
fun ItemTruco(index:Int, game: TrucoUi, onTap: (Int) -> Unit, onLongPress: (Int) -> Unit){
    val borderColor = if(game.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceDim

    Card(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress(index) },
                    onTap = { onTap(index) },
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp),
                text = string_truco,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )

            Row( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = game.player1.playerName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        Text(
                            text = game.player1.playerPoint.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                Text(
                    text = game.pointLimit.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )

                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = game.player2.playerName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        Text(
                            text = game.player2.playerPoint.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }



            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = game.dataCreated,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
@Composable
fun ItemGenerico(index:Int, game: GenericoUi, onTap: (Int) -> Unit, onLongPress: (Int) -> Unit){
    val border = if(game.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceDim

    Card(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress(index) },
                    onTap = { onTap(index) },
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
            .border(2.dp, border, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp),
                text = string_generico,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )

            Row( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Face, contentDescription = "")
                        Text(
                            text = game.player.size.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    VerticalDivider(
                        Modifier
                            .height(24.dp)
                            .padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_scoreboard), contentDescription = "")
                        Text(
                            text = if(game.withPoints) "${game.pointToInit}-${game.pointToFinish}" else "-",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    VerticalDivider(
                        Modifier
                            .height(24.dp)
                            .padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
                        Icon(painter = painterResource(id = R.drawable.ic_rounds), contentDescription = "")
                        Text(
                            text = if (game.withRounds) "${game.roundPlayed}/${game.roundMax}" else "-",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }



                }

            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = game.dataCreated,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
fun SinGames(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = string_home_without_games, color = MaterialTheme.colorScheme.tertiary)
    }
}




@Composable
fun StartAnnotator(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(modifier = modifier.padding(24.dp), onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }

}

@Composable
fun DialogSelectAnnotator(
    onDismissRequest: () -> Unit,
    onClickAnnotatorGenerico: () -> Unit,
    onClickAnnotatorTruco: () -> Unit,
    onClickAnnotatorGenerala: () -> Unit
){


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Annotator(text = string_home_game_generic)  {onClickAnnotatorGenerico()}
            Annotator(text = string_home_game_truco) {onClickAnnotatorTruco()}
            //Annotator(text = "GENERALA") {onClickAnnotatorGenerala()}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Annotator(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick:()-> Unit = {}
){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        onClick = {onClick()}
    ) {
        Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = text)
        }
    }
}
