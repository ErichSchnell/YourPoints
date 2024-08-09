package com.example.yourpoints.presentation.ui.generico

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi


private val player = listOf(
    GenericoPlayerUi(playerName = "Player 1", playerPoint = 50, victories = 0),
    GenericoPlayerUi(playerName = "Player 2", playerPoint = 150, victories = 1),
    GenericoPlayerUi(playerName = "Player 3", playerPoint = 250, victories = 0),
    GenericoPlayerUi(playerName = "Player 4", playerPoint = 50, victories = 4),
)
private val game = GenericoUi(
    isSetPoint = false,
    withPoints = true,
    pointToInit = 0,
    pointToFinish = 100,
    finishToWin = true,
    finished = false,
    withRounds = true,
    roundPlayed = 7,
    roundMax = 10,
    player = player,
)






@Preview
@Composable
fun PreviewCreateGame(){
    CreateGame(
        modifier = Modifier.fillMaxSize(),
        onClickCreateGame = {name, cantPlayers, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds ->

        }
    )
}
@Preview
@Composable
fun PreviewSelectName(){
    val cantidadDePlayes = 10
    SelectName(
        modifier = Modifier.fillMaxSize(),
        cantPlayers = cantidadDePlayes,
        onValueChange = { newNames ->
        },
    )
}
@Preview
@Composable
fun PreviewGamee(){
    Game(
        game = game,
        onAddPlayer = { },
        onValueChange = { },
        onClickChangeViewSetPoints = { },
        onClickResetGame = { },
        onSelectPlayer = { player -> },
        onClickInfo = { }
    )
}

@Preview
@Composable
fun PreviewDialogSettingPlayer(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DialogSettingPlayer(
            onDismissRequest = {  },
            onSetDialogChangeName = {  },
            onDeletePLayer = {  }
        )
    }
}
@Preview
@Composable
fun PreviewDialogChangeName(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DialogChangeName(onDismissRequest = {  }){

        }
    }
}
@Preview
@Composable
fun PreviewDialogInfo(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DialogInfo(
            showDialog = true,
            game = game,
            onDismissRequest = { }
        )
    }
}