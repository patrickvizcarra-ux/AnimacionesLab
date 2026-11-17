package com.tecsup.animacioneslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.animacioneslab.ui.theme.AnimacionesLabTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimacionesLabTheme {
                GalaxyRunner()
            }
        }
    }
}

@Composable
fun GalaxyRunner() {

    var playerLane by remember { mutableIntStateOf(1) }
    var objectLane by remember { mutableIntStateOf(0) }

    var objectY by remember { mutableFloatStateOf(-60f) }
    var isMeteor by remember { mutableStateOf(true) }

    var score by remember { mutableIntStateOf(0) }
    var lives by remember { mutableIntStateOf(3) }

    var jumping by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }
    var started by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("¡Prepárate para sobrevivir!") }

    val level = (score / 50) + 1

    val scope = rememberCoroutineScope()

    // Animación del salto
    val jumpHeight by animateDpAsState(
        targetValue = if (jumping) 90.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Salto"
    )

    // Animación del astronauta
    val playerColor by animateColorAsState(
        targetValue = if (score >= 50)
            Color(0xFF00E5FF)
        else
            Color(0xFF9C6BFF),
        animationSpec = tween(500),
        label = "Color del astronauta"
    )

    // Animación del tamaño del personaje
    val playerSize by animateDpAsState(
        targetValue = if (jumping) 62.dp else 54.dp,
        animationSpec = spring(),
        label = "Tamaño del astronauta"
    )

    // Movimiento automático de meteoritos y cristales
    LaunchedEffect(started, gameOver) {
        while (started && !gameOver) {
            delay(35)

            objectY += 5f + level * 1.2f

            // Detectar colisión
            if (objectY >= 300f && objectY <= 365f &&
                objectLane == playerLane
            ) {
                if (isMeteor) {
                    if (!jumping) {
                        lives--
                        message = "¡CUIDADO! Meteorito"
                    }
                } else {
                    score += 10
                    message = "¡CRISTAL RECOLECTADO! +10"
                }

                objectY = -70f
                objectLane = Random.nextInt(0, 3)
                isMeteor = Random.nextBoolean()

                if (lives <= 0) {
                    lives = 0
                    gameOver = true
                    message = "¡MISIÓN FALLIDA!"
                }
            }

            // Crear nuevo objeto cuando sale de pantalla
            if (objectY > 420f) {
                objectY = -70f
                objectLane = Random.nextInt(0, 3)
                isMeteor = Random.nextBoolean()
            }
        }
    }

    // Fondo principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF090B24),
                        Color(0xFF17143D),
                        Color(0xFF25144C)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ENCABEZADO
            Text(
                text = "GALAXY RUNNER",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFB69CFF)
            )

            Text(
                text = "SPACE SURVIVAL",
                fontSize = 12.sp,
                letterSpacing = 4.sp,
                color = Color(0xFF70E7FF)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // PANEL DE INFORMACIÓN
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF29234E),
                        RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "PUNTOS",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    AnimatedContent(
                        targetState = score,
                        transitionSpec = {
                            fadeIn(tween(250)) togetherWith
                                    fadeOut(tween(250))
                        },
                        label = "Puntuación"
                    ) { points ->
                        Text(
                            "$points",
                            color = Color(0xFFFFD166),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "NIVEL",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    Text(
                        "$level",
                        color = Color(0xFF70E7FF),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "VIDAS",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    Text(
                        "❤️".repeat(lives) + "🖤".repeat(3 - lives),
                        fontSize = 19.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ESCENARIO DEL JUEGO
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF171638),
                                Color(0xFF28205C),
                                Color(0xFF11152F)
                            )
                        ),
                        RoundedCornerShape(24.dp)
                    )
            ) {

                val laneWidth = maxWidth / 3

                // Estrellas del escenario
                Text(
                    "✦",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(25.dp),
                    color = Color.White,
                    fontSize = 23.sp
                )

                Text(
                    "✧",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(35.dp),
                    color = Color(0xFF70E7FF),
                    fontSize = 28.sp
                )

                Text(
                    "✦",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 90.dp),
                    color = Color(0xFFFFD166),
                    fontSize = 17.sp
                )

                // Planeta decorativo
                Text(
                    "🪐",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 100.dp, end = 15.dp),
                    fontSize = 34.sp
                )

                // Líneas de los carriles
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .offset(x = laneWidth)
                        .background(Color(0x3370E7FF))
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .offset(x = laneWidth * 2)
                        .background(Color(0x3370E7FF))
                )

                // METEORITO O CRISTAL
                Text(
                    text = if (isMeteor) "☄️" else "💎",
                    fontSize = 39.sp,
                    modifier = Modifier
                        .offset(
                            x = laneWidth * objectLane +
                                    (laneWidth - 45.dp) / 2,
                            y = objectY.dp
                        )
                )

                // ASTRONAUTA
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(
                            x = laneWidth * playerLane +
                                    (laneWidth - playerSize) / 2,
                            y = -jumpHeight - 18.dp
                        )
                        .size(playerSize)
                        .background(
                            playerColor,
                            RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🧑‍🚀",
                        fontSize = 34.sp
                    )
                }

                // MENSAJE DE ESTADO
                AnimatedContent(
                    targetState = message,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp),
                    transitionSpec = {
                        fadeIn(tween(250)) togetherWith
                                fadeOut(tween(250))
                    },
                    label = "Mensaje del juego"
                ) { currentMessage ->
                    Text(
                        currentMessage,
                        color = Color(0xFFFFD166),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // PANTALLA DE INICIO O GAME OVER
                if (!started || gameOver) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xDD090B24)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                if (gameOver) "💥" else "🚀",
                                fontSize = 55.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                if (gameOver) "GAME OVER"
                                else "¡SALVA LA GALAXIA!",
                                color = Color.White,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                if (gameOver)
                                    "Puntuación final: $score"
                                else
                                    "Esquiva meteoritos y recoge cristales",
                                color = Color(0xFFB9B4D9),
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    score = 0
                                    lives = 3
                                    playerLane = 1
                                    objectLane = Random.nextInt(0, 3)
                                    objectY = -70f
                                    isMeteor = true
                                    jumping = false
                                    gameOver = false
                                    message = "¡Sobrevive!"
                                    started = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF7651DB)
                                )
                            ) {
                                Text(
                                    if (gameOver) "VOLVER A JUGAR"
                                    else "INICIAR PARTIDA",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // MENSAJE DE CONTROLES
            Text(
                "CONTROLES DE NAVEGACIÓN",
                color = Color(0xFFB9B4D9),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // BOTONES DE MOVIMIENTO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (started && !gameOver && playerLane > 0) {
                            playerLane--
                        }
                    },
                    enabled = started && !gameOver,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF343064)
                    )
                ) {
                    Text("◀ IZQUIERDA", fontSize = 11.sp)
                }

                Button(
                    onClick = {
                        if (started && !gameOver && !jumping) {
                            scope.launch {
                                jumping = true
                                delay(650)
                                jumping = false
                            }
                        }
                    },
                    enabled = started && !gameOver && !jumping,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7651DB)
                    )
                ) {
                    Text("⬆ SALTAR", fontSize = 11.sp)
                }

                Button(
                    onClick = {
                        if (started && !gameOver && playerLane < 2) {
                            playerLane++
                        }
                    },
                    enabled = started && !gameOver,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF343064)
                    )
                ) {
                    Text("DERECHA ▶", fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Recolecta 💎 y evita ☄️ para sobrevivir",
                color = Color(0xFF8D89B5),
                fontSize = 11.sp
            )
        }
    }
}
