package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.presentation.model.TrucoUi

@Composable
fun HomeScreen (
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToTruco: () -> Unit,
    navigateToGenerico: () -> Unit,
    navigateToGenerala: () -> Unit
) {
    var showDialog by remember{ mutableStateOf(false) }
    val uiState by homeViewModel.uiState.collectAsState()
    val games by homeViewModel.games.collectAsState()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){

        when(uiState){
            HomeViewState.LOADING -> Loading(Modifier.align(Alignment.Center))
            HomeViewState.SUCCESS -> {
                Games(games){
                    Log.i(TAG, "HomeScreen: Click Game $it")
                }
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
                onClickAnnotatorTruco = {homeViewModel.navigateTo(navigateToTruco) },
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
fun Games(games: List<TrucoUi>, onClickGame: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
        if (games.isNotEmpty() == true) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                content = {
                    items(games.size) { game ->
                        ItemGame(games[game], onClickGame = onClickGame)
                    }
                },
                contentPadding = PaddingValues(16.dp)
            )

        } else {

        }
    }
}
@Composable
fun ItemGame(game: TrucoUi, onClickGame: (String) -> Unit){
    Card(
        modifier = Modifier
            .clickable { onClickGame(game.id.toString()) }
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = game.id.toString(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))

//                if (hall.isPublic) Icon(modifier = Modifier, painter = painterResource(id = R.drawable.ic_lock_open), tint = Orange2, contentDescription = "")
//                else Icon(painter = painterResource(id = R.drawable.ic_lock), tint = Orange2, contentDescription = "")
//                Spacer(modifier = Modifier.width(20.dp))
            }
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
        Text(text = "Sin Partidas", color = MaterialTheme.colorScheme.tertiary)
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
            Annotator(text = "GENERICO")  {onClickAnnotatorGenerico()}
            Annotator(text = "TRUCO") {onClickAnnotatorTruco()}
            Annotator(text = "GENERALA") {onClickAnnotatorGenerala()}
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
