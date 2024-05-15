package com.example.yourpoints.presentation.ui.home

import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
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
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){

        when(uiState){
            HomeViewState.LOADING -> Loading(Modifier.align(Alignment.Center))
            HomeViewState.SUCCESS -> {


            }
        }
        StartAnnotator(Modifier.align(Alignment.BottomEnd)){showDialog = true}


        if (showDialog) {
            DialogSelectAnnotator(
                onDismissRequest = { showDialog = false },
                onClickAnnotatorGenerico = {time ->  homeViewModel.navigateTo(time, navigateToGenerico)},
                onClickAnnotatorTruco = {time ->  homeViewModel.navigateTo(time, navigateToTruco) },
                onClickAnnotatorGenerala = {time ->  homeViewModel.navigateTo(time, navigateToGenerala) }
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

    val currentDateTime = LocalDateTime.now()
    val formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Annotator(text = "GENERICO")  {onClickAnnotatorGenerico(formattedDateTime)}
            Annotator(text = "TRUCO") {onClickAnnotatorTruco(formattedDateTime)}
            Annotator(text = "GENERALA") {onClickAnnotatorGenerala(formattedDateTime)}
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
