package com.example.yourpoints.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi

private val trucoGame = TrucoUi(
    id = 0,
    dataCreated = "2024-08-13 00:04:25",
    pointLimit = 30,
    player1 = TrucoPlayerUi(playerName = "player 1"),
    player2 = TrucoPlayerUi(playerName = "player 2"),
    winner = TypePlayer.VACIO,
)
private val genericGame = GenericoUi(
    id = 1,
    dataCreated = "2024-08-13 00:04:21",
    name = "Example",
    withPoints = true,
    pointToInit = 0,
    pointToFinish = 100,
    finishToWin = true,
    finished = false,
    withRounds = true,
    roundMax = 10,
    roundPlayed = 1,
)


@Preview
@Composable
fun PreviewCardTruco() {
    ItemTruco(
        index = 0,
        game = trucoGame,
        onTap = { },
        onLongPress = { }
    )
}
@Preview
@Composable
fun PreviewCardGeneric() {
    ItemGenerico(
        index = 1,
        game = genericGame,
        onTap = { },
        onLongPress = { }
    )
}

@Preview
@Composable
fun PreviewBody(){
    val games = listOf(
        trucoGame,
        genericGame
    )
    Body(
        modifier = Modifier.fillMaxSize(),
        games = games,
        gameSelected = false,
        onTap = {},
        onLongPress = {},
        onSelectAllGames = {},
        onDeleteGames = {},
    )
}