package com.example.yourpoints.presentation.ui.truco

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.yourpoints.R
import com.example.yourpoints.domain.model.TypePlayer
import com.example.yourpoints.presentation.model.TrucoUi
import com.example.yourpoints.presentation.ui.theme.string_cancel
import com.example.yourpoints.presentation.ui.theme.string_create
import com.example.yourpoints.presentation.ui.theme.string_create_game
import com.example.yourpoints.presentation.ui.theme.string_reset
import com.example.yourpoints.presentation.ui.theme.string_restar_point
import com.example.yourpoints.presentation.ui.theme.string_setting_points
import com.example.yourpoints.presentation.ui.theme.string_winner_game

@Composable
fun TrucoScreen(
    trucoViewModel: TrucoViewModel = hiltViewModel(),
    gameId: Int,
    navController: NavHostController
){
    LaunchedEffect(true){
        trucoViewModel.initAnnotator(gameId)
    }

    val uiState by trucoViewModel.uiState.collectAsState()
    val game by trucoViewModel.game.collectAsState()

    var showSetting by remember{ mutableStateOf(false) }
    var showChangeNamePlayer1 by remember{ mutableStateOf(false) }
    var showChangeNamePlayer2 by remember{ mutableStateOf(false) }

    when(uiState){
        TrucoViewState.LOADING -> { Loading(modifier = Modifier.fillMaxSize())}
        TrucoViewState.CREATE -> {
            SettingNewGame(
                onDismissRequest = { navController.popBackStack() },
                onClickCancel = { navController.popBackStack() },
                onClickCreate = { trucoViewModel.addNewGame(it) }
            )
        }
        TrucoViewState.SUCCESS -> {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

                TrucoAnnotator(
                    game = game,

                    onClickSetting = { showSetting = true },

                    onChangeNamePlayer1 = { showChangeNamePlayer1 = true },
                    increasePlayer1 = { trucoViewModel.increasePlayer1() },
                    decreasePlayer1 = { trucoViewModel.decreasePlayer1() },

                    onChangeNamePlayer2 = { showChangeNamePlayer2 = true },
                    increasePlayer2 = { trucoViewModel.increasePlayer2() },
                    decreasePlayer2 = { trucoViewModel.decreasePlayer2() },
                )

                if(showSetting){
                    DialogSetting(
                        pointCurrent = game.pointLimit,
                        onDismissRequest = { showSetting = false },
                        onClickResetAnnotator = {
                            trucoViewModel.setAnnotator(it)
                            showSetting = false
                        }
                    )
                }
                if (showChangeNamePlayer1){
                    DialogChangeName(
                        onDismissRequest = { showChangeNamePlayer1 = false },
                        onChangeName = {
                            trucoViewModel.changeNamePlayer1(it)
                            showChangeNamePlayer1 = false
                        }
                    )
                }
                if (showChangeNamePlayer2){
                    DialogChangeName(
                        onDismissRequest = { showChangeNamePlayer2 = false },
                        onChangeName = {
                            trucoViewModel.changeNamePlayer2(it)
                            showChangeNamePlayer2 = false
                        }
                    )
                }
                if (game.winner != TypePlayer.VACIO) {
                    DialogFinishGame(
                        winner = if (game.winner == TypePlayer.PLAYER1) game.player1.playerName else game.player2.playerName,
                        onClickCancel = { trucoViewModel.clearWinner() },
                        onClickResetAnnotator = {
                            trucoViewModel.setAnnotator(game.pointLimit)
                        }
                    )
                }
            }
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
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 2.dp
        )
    }
}


@Composable
fun TrucoAnnotator(
    game: TrucoUi,
    onClickSetting: () -> Unit,
    onChangeNamePlayer1: () -> Unit,
    onChangeNamePlayer2: () -> Unit,
    increasePlayer1: () -> Unit,
    decreasePlayer1:() -> Unit,
    increasePlayer2: () -> Unit,
    decreasePlayer2:() -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
        Cabecera(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            player1Name = game.player1.playerName,
            player2Name = game.player2.playerName,
            onClickSetting = { onClickSetting() },
            onClickPlayer1 = { onChangeNamePlayer1() },
            onClickPlayer2 = { onChangeNamePlayer2() }
        )

        Body(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            game = game,
            increasePlayer1 = { increasePlayer1() },
            increasePlayer2 = { increasePlayer2() },
        )

        RestarPuntos(
            modifier = Modifier.height(100.dp),
            decreasePlayer1 = { decreasePlayer1() },
            decreasePlayer2 = { decreasePlayer2() },
        )
    }
}
@Composable
fun Cabecera(
    modifier: Modifier = Modifier,
    player1Name: String = "Nosotros",
    player2Name: String = "Ellos",
    onClickSetting: () -> Unit,
    onClickPlayer1: () -> Unit,
    onClickPlayer2: () -> Unit,
) {
    Row (modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

        Text(
            modifier = Modifier
                .weight(1f)
                .clickable { onClickPlayer1() },
            text = player1Name,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(44.dp)
                .clickable { onClickSetting() },
            imageVector = Icons.Default.Settings,
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = ""
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .clickable { onClickPlayer2() },
            text = player2Name,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun Body(
    modifier: Modifier = Modifier,
    game: TrucoUi,
    increasePlayer1: () -> Unit,
    increasePlayer2: () -> Unit,
) {

    Row(modifier = modifier.background(MaterialTheme.colorScheme.background), horizontalArrangement = Arrangement.Center){

        PointsPlayer(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .clickable { increasePlayer1() },
            playerPoint = game.player1.playerPoint
        )
        VerticalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.inversePrimary)
        PointsPlayer(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .clickable { increasePlayer2() },
            playerPoint = game.player2.playerPoint
        )
    }
}
@Composable
fun PointsPlayer(modifier: Modifier = Modifier, playerPoint: Int){

    var pointCount = playerPoint

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))

        for (i in 1 .. 6){

            BoxPuntos(
                Modifier.size(100.dp),
                points = pointCount
            )
            Spacer(modifier = Modifier.weight(1f))

            if (i == 3){
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            if (pointCount >= 5) pointCount -= 5 else pointCount = 0
        }


    }
}

@Composable
fun BoxPuntos(modifier: Modifier = Modifier, points: Int){
    val limitAux = if (points > 5) 5 else points
    Box(
        modifier = modifier
    ) {

        val alignmentList = listOf(
            Alignment.CenterStart,
            Alignment.TopCenter,
            Alignment.CenterEnd,
            Alignment.BottomCenter,
            Alignment.Center
        )
        val drawableListList = listOf(
            R.drawable.fosforo_vert,
            R.drawable.fosforo_hor,
            R.drawable.fosforo_vert,
            R.drawable.fosforo_hor,
            R.drawable.fosforo_diag
        )

        for (i in 0 until limitAux){
            Image(modifier = Modifier.align(alignmentList[i]), painter = painterResource(id = drawableListList[i]), contentDescription = "")
        }

    }
}

@Composable
fun RestarPuntos(
    modifier: Modifier = Modifier,
    decreasePlayer1:() -> Unit,
    decreasePlayer2:() -> Unit
) {
    Row (modifier = modifier.border(border = BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary))){
        Box (modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { decreasePlayer1() },
            contentAlignment = Alignment.Center){
            Text(text = string_restar_point, fontSize = 24.sp, color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.ExtraLight)
        }
        VerticalDivider(color = MaterialTheme.colorScheme.inversePrimary)
        Box (modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { decreasePlayer2() },
            contentAlignment = Alignment.Center){
            Text(text = string_restar_point, fontSize = 24.sp, color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.ExtraLight)
        }
    }
}

@Composable
fun SettingNewGame(onDismissRequest: () -> Unit = {}, onClickCancel:() -> Unit, onClickCreate:(Int) -> Unit){
    SelectPoints(
        title = string_create_game,
        onDismissRequest = onDismissRequest,
        pointCurrent = 0,
    ){selectedPoint ->
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),onClick = { onClickCancel() }) {
            Text(text = string_cancel)
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = selectedPoint != 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),onClick = { onClickCreate(selectedPoint) }) {
            Text(text = string_create)
        }

    }

}

@Composable
fun DialogSetting(pointCurrent:Int ,onDismissRequest:() -> Unit = {}, onClickResetAnnotator:(Int) -> Unit){

    SelectPoints(
        title = string_setting_points,
        pointCurrent = pointCurrent,
        onDismissRequest = onDismissRequest
    ){selectedPoint ->

        Button(
            modifier = Modifier.padding(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),onClick = { onClickResetAnnotator(selectedPoint) }) {
            Text(text = string_reset.uppercase())
        }

    }
}

@Composable
fun SelectPoints(
    title:String,
    pointCurrent:Int ,
    onDismissRequest:() -> Unit = {},
    content: @Composable RowScope.(Int) -> Unit
){
    val pointList = listOf(12, 15, 24, 30)
    var selectedPoint by remember { mutableIntStateOf(pointCurrent) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = title, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))

                pointList.forEach { points ->
                    Row(Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedPoint == points,
                            onClick = { selectedPoint = points }
                        )
                        Text(text = "$points", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { content(selectedPoint) }
                )
            }
        }
    }
}


@Composable
fun DialogChangeName(onDismissRequest:() -> Unit, onChangeName:(String) -> Unit){
    var name by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    label = { Text(text = "New Name", color = MaterialTheme.colorScheme.tertiary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = name,
                    onValueChange = {
                        if (it.length <= 12){
                            name = it
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary
                    )
                )

                Button(
                    modifier = Modifier.padding(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        onChangeName(name)
                    },
                    enabled = name.isNotEmpty()
                ) {
                    Text(text = "Update Name")
                }
            }
        }

    }
}


@Composable
fun DialogFinishGame(winner:String, onClickCancel:() -> Unit, onClickResetAnnotator:() -> Unit){
    Dialog(onDismissRequest = {}) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),

            ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(text = string_winner_game, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text(text = " $winner", fontSize = 24.sp, color = MaterialTheme.colorScheme.inversePrimary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),onClick = {onClickCancel()}) {
                        Text(text = string_cancel)
                    }
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),onClick = {onClickResetAnnotator()}) {
                        Text(text = string_reset)
                    }
                }
            }
        }

    }
}