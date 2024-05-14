package com.example.yourpoints.presentation.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourpoints.ui.theme.Background
import com.example.yourpoints.ui.theme.Primary
import com.example.yourpoints.ui.theme.Secondary
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToTruco: (String) -> Unit,
    navigateToGenerico: (String) -> Unit,
    navigateToGenerala: (String) -> Unit
){
    var showDialog by remember{ mutableStateOf(false) }
    val uiState by homeViewModel.uiState.collectAsState()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ){
        when(uiState){
            HomeViewState.LOADING -> Loading(Modifier.align(Alignment.Center))
            HomeViewState.SUCCESS -> {

                StartAnnotator(Modifier.align(Alignment.BottomEnd)){showDialog = true}
            }
        }



        if (showDialog) {
            DialogSelectAnnotator(
                onDismissRequest = { showDialog = false },
                onClickAnnotatorGenerico = {time ->  homeViewModel.navigateTo(userEmail, time, navigateToGenerico)},
                onClickAnnotatorTruco = {time ->  homeViewModel.navigateTo(userEmail, time, navigateToTruco) },
                onClickAnnotatorGenerala = {time ->  homeViewModel.navigateTo(userEmail, time, navigateToGenerala) }
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
            color = Secondary,
            backgroundColor = Primary,
            strokeWidth = 2.dp
        )
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogSelectAnnotator(
    onDismissRequest: () -> Unit,
    onClickAnnotatorGenerico: (time:String) -> Unit,
    onClickAnnotatorTruco: (time:String) -> Unit,
    onClickAnnotatorGenerala: (time:String) -> Unit
){
    val currentTime = LocalTime.now()
    val formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Annotator(text = "GENERICO")  {onClickAnnotatorGenerico(formattedTime)}
            Annotator(text = "TRUCO") {onClickAnnotatorTruco(formattedTime)}
            Annotator(text = "GENERALA") {onClickAnnotatorGenerala(formattedTime)}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
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
        backgroundColor = Orange1,
        contentColor = Accent,
        elevation = 12.dp,
        onClick = {onClick()}
    ) {
        Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = text)
        }
    }
}