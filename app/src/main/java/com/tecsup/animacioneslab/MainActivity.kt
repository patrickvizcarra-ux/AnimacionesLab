package com.tecsup.animacioneslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.animacioneslab.ui.theme.AnimacionesLabTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimacionesLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Ejercicio1()
                        HorizontalDivider(
                            modifier = Modifier.padding(20.dp)
                        )
                        Ejercicio2()
                        HorizontalDivider(
                            modifier = Modifier.padding(20.dp)
                        )
                        Ejercicio3()
                        HorizontalDivider(
                            modifier = Modifier.padding(20.dp)
                        )
                        Ejercicio4()
                        HorizontalDivider(
                            modifier = Modifier.padding(20.dp)
                        )
                        EjercicioFinal()
                    }
                }
            }
        }
    }
}

// EJERCICIO 1: AnimatedVisibility

@Composable
fun Ejercicio1() {
    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ejercicio 1: AnimatedVisibility",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Ocultar cuadro" else "Mostrar cuadro")
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(
                        Color(0xFF2196F3),
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("¡Hola Compose!", color = Color.White)
            }
        }
    }
}

// EJERCICIO 2: animateColorAsState

@Composable
fun Ejercicio2() {
    var isGreen by remember { mutableStateOf(false) }

    val animatedColor by animateColorAsState(
        targetValue = if (isGreen) Color(0xFF4CAF50)
        else Color(0xFF2196F3),
        animationSpec = tween(durationMillis = 1000),
        label = "Cambio de color"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ejercicio 2: animateColorAsState",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { isGreen = !isGreen }) {
            Text(if (isGreen) "Cambiar a azul" else "Cambiar a verde")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(animatedColor, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (isGreen) "Color verde" else "Color azul",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Animación con tween de 1000 ms")
    }
}

// EJERCICIO 3: Tamaño y posición

@Composable
fun Ejercicio3() {
    var expanded by remember { mutableStateOf(false) }

    val animatedSize by animateDpAsState(
        targetValue = if (expanded) 160.dp else 110.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Cambio de tamaño"
    )

    val animatedOffset by animateDpAsState(
        targetValue = if (expanded) 50.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Cambio de posición"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ejercicio 3: Tamaño y Posición",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Restablecer cuadro" else "Mover y agrandar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.size(220.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .size(animatedSize)
                    .background(
                        Color(0xFF9C27B0),
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Animado", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Animación de tamaño y posición")
    }
}

// EJERCICIO 4: AnimatedContent

enum class EstadoContenido {
    CARGANDO, CONTENIDO, ERROR
}

@Composable
fun Ejercicio4() {
    var estado by remember {
        mutableStateOf(EstadoContenido.CARGANDO)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ejercicio 4: AnimatedContent",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedContent(
            targetState = estado,
            transitionSpec = {
                fadeIn(tween(700)) togetherWith fadeOut(tween(500))
            },
            label = "Transición de estados"
        ) { estadoActual ->

            val color = when (estadoActual) {
                EstadoContenido.CARGANDO -> Color(0xFFFFA000)
                EstadoContenido.CONTENIDO -> Color(0xFF4CAF50)
                EstadoContenido.ERROR -> Color(0xFFE53935)
            }

            val mensaje = when (estadoActual) {
                EstadoContenido.CARGANDO -> "Cargando..."
                EstadoContenido.CONTENIDO -> "¡Contenido cargado!"
                EstadoContenido.ERROR -> "Ocurrió un error"
            }

            val descripcion = when (estadoActual) {
                EstadoContenido.CARGANDO ->
                    "Espere mientras se procesa la información"
                EstadoContenido.CONTENIDO ->
                    "La información se cargó correctamente"
                EstadoContenido.ERROR ->
                    "No se pudo completar la operación"
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(color, RoundedCornerShape(20.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        mensaje,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(descripcion, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                estado = when (estado) {
                    EstadoContenido.CARGANDO -> EstadoContenido.CONTENIDO
                    EstadoContenido.CONTENIDO -> EstadoContenido.ERROR
                    EstadoContenido.ERROR -> EstadoContenido.CARGANDO
                }
            }
        ) {
            Text("Cambiar estado")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Estado actual: ${estado.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("fadeIn 700 ms / fadeOut 500 ms")
    }
}

// EJERCICIO FINAL: Aventura Pixel

@Composable
fun EjercicioFinal() {
    var jumping by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var showPoint by remember { mutableStateOf(false) }

    val jumpOffset by animateDpAsState(
        targetValue = if (jumping) (-75).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Salto del personaje"
    )

    val characterSize by animateDpAsState(
        targetValue = if (jumping) 85.dp else 70.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Tamaño del personaje"
    )

    val characterColor by animateColorAsState(
        targetValue = if (score % 2 == 0)
            Color(0xFF7E57C2)
        else
            Color(0xFF26A69A),
        animationSpec = tween(durationMillis = 500),
        label = "Color del personaje"
    )

    LaunchedEffect(jumping) {
        if (jumping) {
            delay(450)
            jumping = false
        }
    }

    LaunchedEffect(showPoint) {
        if (showPoint) {
            delay(900)
            showPoint = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ejercicio Final: Aventura Pixel",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("¡Salta y consigue puntos!")

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("⭐ Puntos: ", fontSize = 20.sp)

            AnimatedContent(
                targetState = score,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = "Puntuación"
            ) { puntos ->
                Text(
                    text = puntos.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(
                    Color(0xFFB3E5FC),
                    RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                "☀️",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                fontSize = 32.sp
            )

            Text(
                "⭐",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp),
                fontSize = 28.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Color(0xFF66BB6A),
                        RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = jumpOffset)
                    .padding(bottom = 38.dp)
                    .size(characterSize)
                    .background(
                        characterColor,
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("😎", fontSize = 36.sp)
            }

            // CORRECCIÓN: receptor explícito de ColumnScope
            this@Column.AnimatedVisibility(
                visible = showPoint,
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(tween(300)) + slideInVertically(),
                exit = fadeOut(tween(300)) + slideOutVertically()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "+1 ⭐",
                        color = Color(0xFFFF9800),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (!jumping) {
                    jumping = true
                    score++
                    showPoint = true
                }
            },
            enabled = !jumping,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (jumping) "¡Saltando!" else "SALTAR")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                score = 0
                jumping = false
                showPoint = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar partida")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Presiona SALTAR para conseguir puntos.")
    }
}
