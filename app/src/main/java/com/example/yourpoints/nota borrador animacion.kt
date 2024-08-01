package com.example.yourpoints

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourpoints.presentation.ui.generico.ChangeNumber
import com.example.yourpoints.presentation.ui.generico.ScoreCircleBox

/*

            .combinedClickable(
                onLongClick = {
                    Log.i(TAG, "onLongPress: ${player.playerName}")
                    onSelectPlayer()
                }
            )
 */


/*
Row(
modifier = modifier.padding(8.dp),
verticalAlignment = Alignment.CenterVertically
) {

    ScoreCircleBox(
        modifier = Modifier.size(50.dp),
        finishToWin = game.finishToWin,
        victoriesVisibility = game.withPoints,
        victories = player.victories,
        score =  player.playerPoint + point,
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
        modifier = Modifier.width(100.dp),
        text = player.playerName,
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )



}*/

















/*
@Composable
fun AnimatedTextSwitcher() {
    var isYellowBoxVisibility by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            isYellowBoxVisibility = !isYellowBoxVisibility
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "HELLO 👋 I'M",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box {
                this@Row.AnimatedVisibility(
                    visible = isYellowBoxVisibility,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    ),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0XFFCD921F))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "ANDROID DEVELOPER",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                }
                this@Row.AnimatedVisibility(
                    visible = !isYellowBoxVisibility,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    ),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0XFFC10628))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "ARDA KAZANCI",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
 */