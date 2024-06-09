package com.example.yourpoints.presentation.ui.generico

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.R
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi

private const val TAG = "GenericoScreen Intern Test"

@Composable
fun GenericoScreen(
    genericoViewModel: GenericoViewModel = hiltViewModel(),
    gameId:Int
){
    val context = LocalContext.current
    val uiState by genericoViewModel.uiState.collectAsState()
    val game by genericoViewModel.game.collectAsState()


    LaunchedEffect(true){
        genericoViewModel.initGenerico(gameId)
    }

    when(uiState){
        GenericoUiState.LOADING -> Loading(Modifier.fillMaxSize())

        GenericoUiState.CREATE -> CreateGame(
            modifier = Modifier.fillMaxSize(),
            onClickCreateGame = { name, cantPlayers, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds ->
                if (name.isNotEmpty())
                    genericoViewModel.createGame(name, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds, cantPlayers)
                else
                    Toast.makeText(context, "Error: Set Name",Toast.LENGTH_SHORT).show()
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
            game = game,
            onValueChange = {
                genericoViewModel.updatePoints(it)
            }
        )

        GenericoUiState.VIEW_POINTS -> ViewPoints(
            modifier = Modifier.fillMaxSize(),
            game = game,
            onClickViewChange = {
                genericoViewModel.changeView()
            },
            onClickResetGame = {
                genericoViewModel.resetGame()
            }
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
    onClickCreateGame:(String, Int, Boolean, Int, Int, Boolean, Boolean, Int) -> Unit
){
    var name by remember { mutableStateOf("") }
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
        SetName(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            name = name,
            onValueChange = { name = it }
        )
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
                    name,
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
fun SetName(modifier: Modifier = Modifier, name:String, onValueChange:(String) -> Unit){
    Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Name Game:",
            style = MaterialTheme.typography.headlineMedium
        )
        TextField(value = name, onValueChange = {onValueChange(it)})
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
    game: GenericoUi,
    onValueChange: (List<Int>) -> Unit
){

    val newPoints by remember { mutableStateOf( mutableListOf<Int>() ) }

    game.player.forEach {
        newPoints.add(it.playerPoint)
    }

    Column(modifier = modifier) {

        game.player.forEach{ player ->
            ItemSetPoint(
                modifier = Modifier.fillMaxWidth(),
                game = game,
                player = player,
                onPointChahnge = {
                    newPoints[game.player.indexOf(player)] = it
                }
            )
            HorizontalDivider()
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
fun ItemSetPoint(
    modifier:Modifier = Modifier,
    game:GenericoUi,
    player:GenericoPlayerUi,
    onPointChahnge:(Int) -> Unit
){
    var point by remember { mutableIntStateOf(0) }

    Row(modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

        ScoreCircleBox(
            modifier = Modifier.size(50.dp),
            victories = player.victories,
            score =  player.playerPoint + point,
            victoriesVisibility = game.withPoints,
            finishToWin = game.finishToWin,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.width(100.dp),
            text = player.playerName,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        ChangeNumber(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            value = point,
            onValueChange = {
                point = it
                onPointChahnge(player.playerPoint + point)
            }
        )

    }
}


@Composable
fun ViewPoints(
    modifier: Modifier = Modifier,
    game: GenericoUi,
    onClickViewChange:() -> Unit,
    onClickResetGame:() -> Unit,
){
    Column(modifier = modifier) {

        game.player.forEach {
            ItemViewPoint(
                modifier = Modifier.fillMaxWidth(),
                game = game,
                player = it
            )
            HorizontalDivider()
        }

        Spacer(modifier = Modifier.weight(1f))


        Row(
            Modifier
                .fillMaxWidth()
                .padding(24.dp)) {
            if (game.finished){
                FloatingActionButton(
                    onClick = { onClickResetGame() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            FloatingActionButton(
                onClick = { onClickViewChange() }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
@Composable
fun ItemViewPoint(
    modifier:Modifier = Modifier,
    game: GenericoUi,
    player:GenericoPlayerUi
){
    Row(modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

        ScoreCircleBox(
            modifier = Modifier.size(50.dp),
            finishToWin = game.finishToWin,
            victoriesVisibility = game.withPoints,
            victories = player.victories,
            score =  player.playerPoint
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.width(100.dp),
            text = player.playerName,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (game.withPoints){
            LinearProgressIndicator(
                progress = { ((player.playerPoint * 1f - game.pointToInit) / (game.pointToFinish - game.pointToInit)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 12.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimary,
            )

            EmoticonPlayer(
                modifier = Modifier.size(50.dp),
                finishToWin = game.finishToWin,
                pointToFinish = game.pointToFinish,
                player = player,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ScoreCircleBox(
    modifier: Modifier,
    finishToWin: Boolean = true,
    victoriesVisibility: Boolean = true,
    victories: Int,
    score: Int,
) {
    BadgedBox(
        badge = {
            if (victoriesVisibility) {// && victories > 0
                BadgedBoxScore(victories, finishToWin)
            }
        },
        content = {
            Box(
                modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = score.toString(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

@Composable
fun BadgedBoxScore(victories: Int, finishToWin: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$victories", fontSize = 16.sp)
        Image(
            modifier = Modifier.size(16.dp),
            painter = if (finishToWin) painterResource(id = R.drawable.cup_winner) else painterResource(id = R.drawable.sad_loser),
            contentDescription = "",
        )
    }
}

@Composable
fun EmoticonPlayer(
    modifier: Modifier = Modifier,
    finishToWin: Boolean,
    pointToFinish: Int,
    player: GenericoPlayerUi,
) {

    if(player.playerPoint >= pointToFinish){
        if (finishToWin){
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.cup_winner),
                contentDescription = "",
            )
        } else {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.sad_loser),
                contentDescription = "",
            )

        }
    } else {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.flag_finish),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }


}

@Composable
fun ChangeNumber(
    modifier: Modifier = Modifier,
    title:String? = null,
    value:Int = 0,
    onValueChange: (Int) -> Unit
){
    var numberAux by remember { mutableStateOf(value.toString()) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        title?.let {
            Text(modifier = Modifier.fillMaxWidth(), text = it, textAlign = TextAlign.Center)
        }

        TextField(
            modifier = Modifier.wrapContentWidth(),
            value = numberAux,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                numberAux = it
                try {
                    onValueChange(it.toInt())
                } catch (e:Exception){
                    Log.i(TAG, "ChangeNumber error: ${e.message}")
                }
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            numberAux = value.dec().toString()
                            onValueChange(value.dec())
                        },
                    tint = MaterialTheme.colorScheme.tertiary, imageVector = Icons.Default.KeyboardArrowDown, contentDescription = ""
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            numberAux = value.inc().toString()
                            onValueChange(value.inc())
                        },
                    tint = MaterialTheme.colorScheme.tertiary, imageVector = Icons.Default.KeyboardArrowUp, contentDescription = ""
                )
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            singleLine =  true,
            colors =  TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
            ),
        )
    }
}


