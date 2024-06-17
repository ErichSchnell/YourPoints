package com.example.yourpoints

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