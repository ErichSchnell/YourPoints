package com.example.yourpoints.presentation.ui.generico

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.R
import com.example.yourpoints.presentation.model.GenericoPlayerUi
import com.example.yourpoints.presentation.model.GenericoUi
import com.example.yourpoints.presentation.ui.theme.string_generic_add_new_palyer
import com.example.yourpoints.presentation.ui.theme.string_generic_cant_players
import com.example.yourpoints.presentation.ui.theme.string_generic_change_name
import com.example.yourpoints.presentation.ui.theme.string_generic_date_created
import com.example.yourpoints.presentation.ui.theme.string_generic_delete_player
import com.example.yourpoints.presentation.ui.theme.string_generic_is_with_points
import com.example.yourpoints.presentation.ui.theme.string_generic_name_game
import com.example.yourpoints.presentation.ui.theme.string_generic_number_of_rounds
import com.example.yourpoints.presentation.ui.theme.string_generic_number_of_rounds_played
import com.example.yourpoints.presentation.ui.theme.string_util_new_name
import com.example.yourpoints.presentation.ui.theme.string_generic_point_finish
import com.example.yourpoints.presentation.ui.theme.string_generic_point_init
import com.example.yourpoints.presentation.ui.theme.string_generic_point_to_lose
import com.example.yourpoints.presentation.ui.theme.string_generic_point_to_win
import com.example.yourpoints.presentation.ui.theme.string_generic_prefix_rounds
import com.example.yourpoints.presentation.ui.theme.string_generic_select_name
import com.example.yourpoints.presentation.ui.theme.string_generic_setting
import com.example.yourpoints.presentation.ui.theme.string_generic_title_rounds
import com.example.yourpoints.presentation.ui.theme.string_util_update_name

private const val TAG = "GenericoScreen Intern Test"

@Composable
fun GenericoScreen(
    genericoViewModel: GenericoViewModel = hiltViewModel(),
    gameId:Int
){
    val uiState by genericoViewModel.uiState.collectAsState()
    val game by genericoViewModel.game.collectAsState()
    val loading by genericoViewModel.loading.collectAsState()

    val showToast by genericoViewModel.showToast.collectAsState()
    val showDialogChangeName by genericoViewModel.showDialogChangeName.collectAsState()
    val playerSelected by genericoViewModel.playerSelected.collectAsState()
    
    var showDialogMenu by remember { mutableStateOf(false) }


    LaunchedEffect(true){
        genericoViewModel.initGenerico(gameId)
    }

    Box(modifier = Modifier.fillMaxSize()){
        when(uiState){
            GenericoUiState.LOADING -> Loading(Modifier.fillMaxSize())

            GenericoUiState.CREATE -> {
                CreateGame(
                    modifier = Modifier.fillMaxSize(),
                    onClickCreateGame = { name, cantPlayers, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds ->
                        genericoViewModel.createGame(name, pointFlag, pointInit, pointFinish, finishToWin, roundFlag, rounds, cantPlayers)
                    }
                )
            }

            GenericoUiState.SELECT_NAME -> {
                SelectName(
                    modifier = Modifier.fillMaxSize(),
                    cantPlayers = game.player.size,
                    onValueChange = { newNames ->
                        genericoViewModel.updateNames(newNames)
                    },
                )
            }

            GenericoUiState.GAME -> {
                Game(
                    game = game,
                    onAddPlayer = { genericoViewModel.addPLayer() },
                    onValueChange = { genericoViewModel.updatePoints(it) },
                    onClickChangeViewSetPoints = { genericoViewModel.changeViewSetPoints() },
                    onClickResetGame = { genericoViewModel.resetGame() },
                    onSelectPlayer = { player -> genericoViewModel.setPlayerSelected(player) },
                    onClickInfo = { showDialogMenu = true}
                )
            }
        }
        if (loading){
            Loading(Modifier.fillMaxSize())
        }
        if(playerSelected != null && !showDialogChangeName){
            DialogSettingPlayer(
                onDismissRequest = { genericoViewModel.setPlayerSelected(null) },
                onSetDialogChangeName = { genericoViewModel.setDialogChangeName(true) },
                onDeletePLayer = { genericoViewModel.deletePLayer() }
            )
        }
        if (showDialogChangeName){
            DialogChangeName(onDismissRequest = { genericoViewModel.setDialogChangeName(false) }){
                genericoViewModel.changeName(it)
            }
        }
        DialogInfo(
            showDialog = showDialogMenu,
            game = game,
            onDismissRequest = { showDialogMenu = false}
        )
    }

    ShowToast(text = showToast){
        genericoViewModel.clearToast()
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
            color = MaterialTheme.colorScheme.tertiary,
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

    val scrollState = rememberScrollState()

    val textFieldColors = TextFieldDefaults.colors().copy(
        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
    )

    Column(
        modifier = modifier.verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SetName(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            name = name,
            textFieldColors = textFieldColors.copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            onValueChange = { name = it }
        )
        CantPlayers(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            cantPlayers = cantPlayers,
            textFieldColors = textFieldColors.copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            onValueChange = { cantPlayers = it }
        )

        Points(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textFieldColors = textFieldColors.copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ),

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textFieldColors = textFieldColors.copy(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ),

            roundFlag = roundFlag,
            onClickRoundFlag = { roundFlag = it },
            rounds = rounds,
            onClickRounds = { rounds = it },
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
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
fun SetName(modifier: Modifier = Modifier, name:String, textFieldColors: TextFieldColors = TextFieldDefaults.colors(), onValueChange:(String) -> Unit){
    Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = string_generic_name_game,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        TextField (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = name,
            onValueChange = {onValueChange(it)},
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            colors = textFieldColors
        )
    }
}
@Composable
fun CantPlayers(modifier: Modifier = Modifier, cantPlayers:Int, textFieldColors: TextFieldColors = TextFieldDefaults.colors(), onValueChange:(Int) -> Unit){
    ChangeNumber(
        modifier,
        title = string_generic_cant_players,
        titleColor = MaterialTheme.colorScheme.primary,
        value = cantPlayers,
        textFieldColors = textFieldColors,
        onValueChange = onValueChange
    )
}

@Composable
fun Points(
    modifier: Modifier = Modifier,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    pointFlag:Boolean,
    onClickPointFlag:(Boolean) -> Unit,
    pointInit:Int,
    onClickPointInit:(Int) -> Unit,
    finishToWin:Boolean,
    onClickFinishToWin:(Boolean) -> Unit,
    pointFinish:Int,
    onClickPointFinish:(Int) -> Unit,
){
    val pointContainerColor = if (pointFlag) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val pointContentColor = if (pointFlag) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    val checkBoxColor = CheckboxDefaults.colors().copy(
        checkedBorderColor = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        checkedCheckmarkColor = MaterialTheme.colorScheme.onPrimary,
        uncheckedCheckmarkColor = MaterialTheme.colorScheme.surfaceVariant,
        checkedBoxColor = MaterialTheme.colorScheme.primary,
        uncheckedBoxColor = MaterialTheme.colorScheme.surfaceVariant,
    )




    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation( defaultElevation = 12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = pointContainerColor,
            contentColor = pointContentColor,
        )
    ) {

        Column(modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = string_generic_is_with_points,
                    style = MaterialTheme.typography.headlineMedium,
                    color = pointContentColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = pointFlag,
                    onCheckedChange = { onClickPointFlag(it) },
                    colors = checkBoxColor
                )
            }

            AnimatedVisibility(visible = pointFlag) {
                Column{
                    ChangeNumber(
                        modifier = Modifier.fillMaxWidth(),
                        title = string_generic_point_init,
                        titleStyle = MaterialTheme.typography.titleLarge,
                        titleColor = MaterialTheme.colorScheme.secondary,
                        value = pointInit,
                        onValueChange = onClickPointInit,
                        textFieldColors = textFieldColors
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    ChangeNumber(
                        modifier = Modifier.fillMaxWidth(),
                        title = string_generic_point_finish,
                        titleStyle = MaterialTheme.typography.titleLarge,
                        titleColor = MaterialTheme.colorScheme.secondary,
                        value = pointFinish,
                        onValueChange = onClickPointFinish,
                        textFieldColors = textFieldColors
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
            RadioButton(
                selected = finishToWin, onClick = { onClickFinishToWin(true)},
                colors = RadioButtonDefaults.colors().copy(
                    selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
            Text(
                text = string_generic_point_to_win,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !finishToWin, onClick = { onClickFinishToWin(false)},
                colors = RadioButtonDefaults.colors().copy(
                    selectedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ))
            Text(
                text = string_generic_point_to_lose,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    }
}

@Composable
fun Rounds(
    modifier: Modifier = Modifier,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    roundFlag:Boolean = false,
    onClickRoundFlag:(Boolean) -> Unit,
    rounds:Int = 0,
    onClickRounds:(Int) -> Unit,
){
    val pointContainerColor = if (roundFlag) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val pointContentColor = if (roundFlag) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    val checkBoxColor = CheckboxDefaults.colors().copy(
        checkedBorderColor = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        checkedCheckmarkColor = MaterialTheme.colorScheme.onPrimary,
        uncheckedCheckmarkColor = MaterialTheme.colorScheme.surfaceVariant,
        checkedBoxColor = MaterialTheme.colorScheme.primary,
        uncheckedBoxColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation( defaultElevation = 12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = pointContainerColor,
            contentColor = pointContentColor,
        )
    ) {
        Column(modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = string_generic_title_rounds,
                    style = MaterialTheme.typography.headlineMedium,
                    color = pointContentColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = roundFlag,
                    onCheckedChange = { onClickRoundFlag(it) },
                    colors = checkBoxColor
                )
            }

            AnimatedVisibility(visible = roundFlag) {
                Column {
                    Spacer(modifier = Modifier.width(8.dp))
                    ChangeNumber(
                        modifier = Modifier.fillMaxWidth(),
                        textFieldColors = textFieldColors,
                        value = rounds,
                        onValueChange = onClickRounds
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
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
    val scrollState = rememberScrollState()

    for (i in 0 until cantPlayers){
        playerNames.add("Player ${i+1}")
    }

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = string_generic_select_name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            .align(Alignment.End), onClick = { onValueChange(playerNames) },
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
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
fun PrototypePlayer(
    modifier:Modifier = Modifier,
    playerName:String = "",
    onValueChange:(String) -> Unit,
){
    var name by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedTextColor = MaterialTheme.colorScheme.tertiary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        unfocusedLabelColor = MaterialTheme.colorScheme.primary
    )



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
            label = { Text(text = playerName) },
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
            colors = textFieldColors
        )

    }
}


@Composable
fun Game(
    game:GenericoUi,
    onAddPlayer: ()-> Unit,
    onValueChange:(List<Int>) -> Unit,
    onClickChangeViewSetPoints:() -> Unit,
    onClickResetGame:() -> Unit,
    onSelectPlayer:(GenericoPlayerUi) -> Unit,
    onClickInfo:() -> Unit
){
    val isRoundsExceeded = (game.roundPlayed > game.roundMax && game.withRounds)


    val scrollState = rememberScrollState()
    val newPoints by remember { mutableStateOf( mutableListOf<Int>() ) }
    val backgroundColor = if (!isRoundsExceeded) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
    val contentColor = if (!isRoundsExceeded) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onError

    if (newPoints.size != game.player.size){
        newPoints.clear()
        game.player.forEach {
            newPoints.add(it.playerPoint)
        }
    }

    Log.i(TAG, "Game: newPoints: $newPoints")

    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
            ){
            RoundsPlayed(game.withRounds, game.roundPlayed, game.roundMax, contentColor)
            IconInfo(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp), color = contentColor){
                onClickInfo()
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)){

            ListPlayer(
                isSetPoint = game.isSetPoint,
                players = game.player,
                finishToWin = game.finishToWin,
                withPoints = game.withPoints,
                pointToInit = game.pointToInit,
                pointToFinish = game.pointToFinish,

                onPointChange = { player, newPoint ->
                    newPoints[game.player.indexOf(player)] = newPoint
                    Log.i(TAG, "Game: newPoints: $newPoints")
                },
                onSelectPlayer = onSelectPlayer
            )

            AddPlayer(onAddPlayer)

            Spacer(modifier = Modifier.weight(1f))


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                if (game.finished || isRoundsExceeded) {
                    FloatingActionButton(
                        onClick = {
                            newPoints.clear()
                            onClickResetGame()
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
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
                    onClick = {
                        if (game.isSetPoint) onValueChange(newPoints)
                        else onClickChangeViewSetPoints()
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun IconInfo(modifier: Modifier, color: Color = LocalContentColor.current, onClick:() -> Unit){
    Icon(
        modifier = modifier.clickable {onClick() },
        imageVector = Icons.Default.Info,
        contentDescription = null,
        tint = color
    )
}
@Composable
fun RoundsPlayed(withRounds: Boolean, roundPlayed: Int, roundMax: Int, contentColor: Color){
    val text = if (withRounds) {
        "$string_generic_prefix_rounds $roundPlayed de $roundMax"
    } else {
        "$string_generic_prefix_rounds $roundPlayed"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = contentColor
        )
    }

}
@Composable
fun ListPlayer(
    isSetPoint: Boolean,
    players: List<GenericoPlayerUi>,
    finishToWin: Boolean,
    withPoints: Boolean,
    pointToInit: Int,
    pointToFinish: Int,

    onPointChange:(GenericoPlayerUi, Int) -> Unit,
    onSelectPlayer:(GenericoPlayerUi) -> Unit
){
    players.forEach { player ->
        CardPlayer(
            player,
            isSetPoint,
            finishToWin,
            withPoints,
            pointToInit,
            pointToFinish,
            onPointChahnge = { newPoint ->
                onPointChange(player, newPoint)
            },
            onSelectPlayer = onSelectPlayer
        )
        HorizontalDivider()
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardPlayer(
    player: GenericoPlayerUi,
    isSetPoint: Boolean,
    finishToWin: Boolean,
    withPoints: Boolean,
    pointToInit: Int,
    pointToFinish: Int,
    onPointChahnge:(Int) -> Unit,
    onSelectPlayer:(GenericoPlayerUi) -> Unit,
){
    var point by remember { mutableIntStateOf(0) }
    if (!isSetPoint) point = 0



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ScoreCircleBox(
            modifier = Modifier.size(50.dp),
            finishToWin = finishToWin,
            victoriesVisibility = withPoints,
            victories = player.victories,
            score = player.playerPoint + point
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier
                .width(100.dp)
                .combinedClickable(onClick = {}, onLongClick = { onSelectPlayer(player) }),
            text = player.playerName,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (isSetPoint){
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
        } else {
            if (withPoints){
                LinearProgressIndicator(
                    progress = { ((player.playerPoint * 1f - pointToInit) / (pointToFinish - pointToInit)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 24.dp, end = 12.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onPrimary,
                )

                EmoticonPlayer(
                    modifier = Modifier.size(50.dp),
                    finishToWin = finishToWin,
                    pointToFinish = pointToFinish,
                    player = player,
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
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
fun BadgedBoxScore(victories: Int, finishToWin: Boolean) { //Badge (probar)
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
fun AddPlayer(onAddPlayer:() -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.clickable { onAddPlayer() },
            text = string_generic_add_new_palyer,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
@Composable
fun ChangeNumber(
    modifier: Modifier = Modifier,
    title:String? = null,
    titleStyle:TextStyle = MaterialTheme.typography.headlineSmall,
    titleColor:Color = MaterialTheme.colorScheme.onSurface,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.background, focusedContainerColor = MaterialTheme.colorScheme.background,),
    value:Int = 0,
    onValueChange: (Int) -> Unit
){
    var numberAux by remember { mutableStateOf(TextFieldValue(value.toString())) }
    var hasFocus by remember { mutableStateOf(false) }

    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        numberAux = numberAux.copy(
            selection = if (isFocused) {
                Log.i(TAG, "LaunchedEffect: ")
                TextRange(0, numberAux.text.length)
            } else {
                TextRange.Zero
            }
        )
    }



    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        title?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                textAlign = TextAlign.Center,
                color = titleColor, style = titleStyle
            )
        }

        TextField(
            modifier = Modifier.wrapContentWidth(),
            value = numberAux,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions.Default.also {
                hasFocus = false
            },
            onValueChange = {
                numberAux = it
                try {
                    onValueChange(it.text.toInt())
                } catch (e:Exception){
                    Log.i(TAG, "ChangeNumber error: ${e.message}")
                }
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            numberAux = TextFieldValue(value.dec().toString())
                            onValueChange(value.dec())
                        },
                    imageVector = Icons.Default.KeyboardArrowDown, contentDescription = ""
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            numberAux = TextFieldValue(value.inc().toString())
                            onValueChange(value.inc())
                        },
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = ""
                )
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            singleLine =  true,
            colors =  textFieldColors ,
        )
    }
}

@Composable
fun DialogSettingPlayer(
    onDismissRequest: () -> Unit,
    onSetDialogChangeName: () -> Unit,
    onDeletePLayer: () -> Unit
) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = string_generic_setting,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        onSetDialogChangeName()
                    },
                ) {
                    Text(text = string_generic_change_name)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        onDeletePLayer()
                    },

                    ) {
                    Text(text = string_generic_delete_player)
                }
                Spacer(modifier = Modifier.height(8.dp))

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
                    label = { Text(text = string_util_new_name, color = MaterialTheme.colorScheme.tertiary) },
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
                    Text(text = string_util_update_name)
                }
            }
        }

    }
}

@Composable
fun DialogInfo(
    showDialog: Boolean,
    game: GenericoUi,
    onDismissRequest:() -> Unit
){
    if (!showDialog) return

    Dialog(onDismissRequest = { onDismissRequest() }){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    Text(text = "${string_generic_name_game}: ")
                    Text(text = game.name)
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    Text(text = "${string_generic_date_created}: ")
                    Text(text = game.dataCreated)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer)

                if (game.withPoints){
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_point_init}: ")
                        Text(text = game.pointToInit.toString())
                    }
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_point_finish}: ")
                        Text(text = game.pointToFinish.toString())
                    }
                    if (game.finishToWin) Text(text = string_generic_point_to_win)
                    else Text(text = string_generic_point_to_lose)
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_is_with_points}: ")
                        Checkbox(
                            checked = false,
                            enabled = false,
                            onCheckedChange = {  }
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer)

                if (game.withRounds){
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_number_of_rounds_played}: ")
                        Text(text = game.roundPlayed.toString())
                    }
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_number_of_rounds}: ")
                        Text(text = game.roundMax.toString())
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "${string_generic_title_rounds}: ")
                        Checkbox(
                            checked = false,
                            enabled = false,
                            onCheckedChange = {  }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ShowToast(text: String, clearToast:() -> Unit) {
    val context = LocalContext.current
    if (text.isNotEmpty()){
        Toast.makeText(context, "Error: Set Name", Toast.LENGTH_SHORT).show()
        clearToast()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAux(){
    val game = GenericoUi(
        name = "Erich",
        dataCreated = "2024-08-08 11:07:25",
        withPoints = true,
        pointToInit = 0,
        pointToFinish = 100,
        finishToWin = true,
        withRounds = true,
        roundMax = 10,
        roundPlayed = 1
    )
    Box (Modifier.fillMaxSize()){
        DialogInfo(
            showDialog = true,
            game = game,
            onDismissRequest = { } ,
        )
    }
}


