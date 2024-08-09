package com.example.yourpoints.presentation.ui.truco

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.presentation.model.TrucoPlayerUi
import com.example.yourpoints.presentation.model.TrucoUi


@Preview(
    name = "create_game",
    showBackground = false,
)
@Composable
fun PreviewCreateGame(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        SettingNewGame(
            {}, {},{}
        )
    }
}

@Preview(
    name = "game",
    showBackground = false,
)
@Composable
fun PreviewGame(){
    val player1 = TrucoPlayerUi(
        playerName = "player 1",
        playerPoint = 23,
        victories = 0
    )
    val player2 = TrucoPlayerUi(
        playerName = "player 2",
        playerPoint = 15,
        victories = 0
    )
    val game = TrucoUi(
        dataCreated = "2024-08-08 12:13:25",
        pointLimit = 30,
        player1 = player1,
        player2 = player2,
        winner = TypePlayer.VACIO,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        TrucoAnnotator(
            game = game, {},{},{},{},{},{},{}
        )
    }
}

@Preview(
    name = "setting",
    showBackground = false,
)
@Composable
fun PreviewSetting(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        DialogSetting(
            pointCurrent = 0,
            {},{}
        )
    }
}

@Preview(
    name = "change_name",
)
@Composable
fun PreviewChangeName(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        DialogChangeName(
            onDismissRequest = { },
            onChangeName = {}
        )
    }
}
@Preview(
    name = "game_finish",
)
@Composable
fun PreviewGameFinish(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        DialogFinishGame(
            winner = "Team 1",
            onClickCancel = {  },
            onClickResetAnnotator = {}
        )
    }
}