package com.example.yourpoints.presentation.ui.generico

import android.graphics.Color
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.presentation.model.GenericoPlayerUi

private val TAG = "GenericoScreen Intern Test"

@Composable
fun GenericoScreen(
    genericoViewModel: GenericoViewModel = hiltViewModel(),
    gameId:Int
){
    val uiState by genericoViewModel.uiState.collectAsState()
    val game by genericoViewModel.game.collectAsState()


    LaunchedEffect(true){
        genericoViewModel.initGenerico(gameId)
    }

    when(uiState){
        GenericoUiState.LOADING -> Loading(Modifier.fillMaxSize())

        GenericoUiState.CREATE -> CreateGame(
            modifier = Modifier.fillMaxSize(),
            onClickCreateGame = { cantPlayers, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds ->
                genericoViewModel.createGame(pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds, cantPlayers)
            }
        )

        GenericoUiState.SELECT_NAME -> SelectName(
            modifier = Modifier.fillMaxSize(),
            cantPlayers = game.playerMax,
            onValueChange = {
                            genericoViewModel.updateNames(it)
            },
        )

        GenericoUiState.SET_POINTS -> SetPoints(
            modifier = Modifier.fillMaxSize(),
            cantPlayers = game.player,
            onValueChange = {
                genericoViewModel.updatePoints(it)
            }
        )

        GenericoUiState.VIEW_POINTS -> ViewPoints(

        )
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
fun CreateGame(
    modifier: Modifier = Modifier,
    onClickCreateGame:(Int, Boolean, Int, Int, Boolean, Boolean, Int) -> Unit
){
    var cantPlayers by remember { mutableIntStateOf(2) }
    var pointFlag by remember { mutableStateOf(false) }
    var pointInit by remember { mutableIntStateOf(0) }
    var pointFinish by remember { mutableIntStateOf(100) }
    var finishToWin by remember { mutableStateOf(true) }
    var roundFlag by remember { mutableStateOf(false) }
    var rounds by remember { mutableIntStateOf(10) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CantPlayers(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            cantPlayers = cantPlayers,
            onValueChange = { cantPlayers = it }
        )

        Points(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),

            pointFlag = pointFlag,
            onClickPointFlag = { pointFlag = it },

            pointInit = pointInit,
            onClickPointInit = { pointInit = it },

            finishToWin = finishToWin,
            onClickFinishToWin = { finishToWin = it },

            pointFinish = pointFinish,
            onClickPointFinish = { pointFinish = it }
        )

        Rounds(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            roundFlag = roundFlag,
            onClickRoundFlag = { roundFlag = it },
            rounds = rounds,
            onClickRounds = { rounds = it },
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(24.dp),
            onClick = {
                onClickCreateGame(
                    cantPlayers,
                    pointFlag,
                    pointInit,
                    pointFinish,
                    finishToWin,
                    roundFlag,
                    rounds,
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
@Composable
fun CantPlayers(modifier: Modifier = Modifier, cantPlayers:Int, onValueChange:(Int) -> Unit){
    ChangeNumber(
        modifier,
        title = "Players Cant",
        value = cantPlayers,
        onValueChange = onValueChange
    )
}

@Composable
fun Points(
    modifier: Modifier = Modifier,
    pointFlag:Boolean,
    onClickPointFlag:(Boolean) -> Unit,
    pointInit:Int,
    onClickPointInit:(Int) -> Unit,
    finishToWin:Boolean,
    onClickFinishToWin:(Boolean) -> Unit,
    pointFinish:Int,
    onClickPointFinish:(Int) -> Unit,
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation( defaultElevation = 12.dp)
    ) {
        Column {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Puntajes")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(checked = pointFlag, onCheckedChange = { onClickPointFlag(it) })
            }

            AnimatedVisibility(visible = pointFlag) {
                Column{
                    ChangeNumber(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Point Init",
                        value = pointInit,
                        onValueChange = onClickPointInit
                    )
                    ChangeNumber(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Point Finish",
                        value = pointFinish,
                        onValueChange = onClickPointFinish
                    )
                    FinishToWin(
                        modifier = Modifier.fillMaxWidth(),
                        finishToWin = finishToWin,
                        onClickFinishToWin = onClickFinishToWin,
                    )
                }

            }
        }
    }
}

@Composable
fun FinishToWin(modifier: Modifier, finishToWin: Boolean, onClickFinishToWin: (Boolean) -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = finishToWin, onClick = { onClickFinishToWin(true)})
            Text(text = "Quien alcanza el puntaje, GANA")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !finishToWin, onClick = { onClickFinishToWin(false)})
            Text(text = "Quien alcanza el puntaje, PIERDE")
        }

    }
}

@Composable
fun Rounds(
    modifier: Modifier = Modifier,
    roundFlag:Boolean = false,
    onClickRoundFlag:(Boolean) -> Unit,
    rounds:Int = 0,
    onClickRounds:(Int) -> Unit,
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation( defaultElevation = 12.dp)
    ) {
        Column {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Rounds")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(checked = roundFlag, onCheckedChange = { onClickRoundFlag(it) })
            }

            AnimatedVisibility(visible = roundFlag) {
                ChangeNumber(
                    modifier = Modifier.fillMaxWidth(),
                    value = rounds,
                    onValueChange = onClickRounds
                )
            }
        }
    }
}




@Composable
fun SelectName(
    modifier: Modifier = Modifier,
    cantPlayers: Int,
    onValueChange: (List<String>) -> Unit
) {
    val playerNames by remember { mutableStateOf(mutableListOf<String>()) }

    for (i in 0 until cantPlayers){
        playerNames.add("Player ${i+1}")
    }

    Column(modifier = modifier) {
        for (i in 0 until cantPlayers){
            PrototypePlayer(
                modifier = Modifier.fillMaxWidth(),
                playerName = playerNames[i],
                onValueChange = { 
                    playerNames[i] = it
                }

            )
        }
        Spacer(modifier = Modifier.weight(1f))
        FloatingActionButton(modifier = Modifier
            .padding(24.dp)
            .align(Alignment.End), onClick = { onValueChange(playerNames) }) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun PrototypePlayer(
    modifier:Modifier = Modifier,
    playerName:String = "",
    onValueChange:(String) -> Unit,
){
    var name by remember { mutableStateOf("") }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Box(
            Modifier
                .clickable { }
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (name.isNotEmpty()) "${name.first()}" else "P",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

        OutlinedTextField(
            label = { Text(text = playerName, color = MaterialTheme.colorScheme.tertiary) },
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                if (it.length <= 24){
                    name = it
                    onValueChange(name)
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

    }
}

@Composable
fun SetPoints(
    modifier: Modifier = Modifier,
    cantPlayers: List<GenericoPlayerUi>,
    onValueChange: (List<Int>) -> Unit
){
    var index by remember { mutableStateOf(0) }
    val newPoints by remember { mutableStateOf(mutableListOf<Int>()) }
    cantPlayers.forEach { newPoints.add(it.playerPoint) }

    Column(modifier = modifier) {
        cantPlayers.forEach{ player ->
            ItemPlayer(
                modifier = Modifier.fillMaxWidth(),
                player = player,
                onPointChahnge = {
                    newPoints[cantPlayers.indexOf(player)] = it
                }

            )
        }
        Spacer(modifier = Modifier.weight(1f))
        FloatingActionButton(modifier = Modifier
            .padding(24.dp)
            .align(Alignment.End), onClick = {
                onValueChange(newPoints)
                newPoints.clear()
            }) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
@Composable
fun ItemPlayer(
    modifier:Modifier = Modifier,
    player:GenericoPlayerUi,
    onPointChahnge:(Int) -> Unit
){
    var point by remember { mutableIntStateOf(0) }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Box(
            Modifier
                .clickable { }
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (player.playerPoint + point).toString(),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

        Text(
            text = player.playerName,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.weight(1f))
        ChangeNumber(
            value = point,
            onValueChange = {
                point = it
                onPointChahnge(player.playerPoint + point)
            }
        )

    }
}


@Composable
fun ViewPoints(){
//    Column(modifier = modifier) {
//        Spacer(modifier = Modifier.weight(1f))
//        FloatingActionButton(modifier = Modifier
//            .padding(24.dp)
//            .align(Alignment.End), onClick = { onValueChange(playerNames) }) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//        }
//    }
}

@Composable
fun ChangeNumber(
    modifier: Modifier = Modifier,
    title:String = "",
    value:Int,
    onValueChange: (Int) -> Unit
){
    var numberAux by remember { mutableStateOf(value.toString()) }
    Column(modifier = modifier) {

        Text(modifier = Modifier.fillMaxWidth(), text = title, textAlign = TextAlign.Center)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        numberAux = value.toString()
                        onValueChange(value.dec())
                    },
                tint = MaterialTheme.colorScheme.tertiary, imageVector = Icons.Default.KeyboardArrowDown, contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = numberAux, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    numberAux = it
                    try {
                        onValueChange(it.toInt())
                    } catch (e:Exception){
                        Log.i(TAG, "ChangeNumber error: ${e.message}")
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        numberAux = value.toString()
                        onValueChange(value.inc())
                    },
                tint = MaterialTheme.colorScheme.tertiary, imageVector = Icons.Default.KeyboardArrowUp, contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


