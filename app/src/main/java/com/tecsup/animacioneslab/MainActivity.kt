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
import androidx.compose.ui.unit.dp
import com.tecsup.animacioneslab.ui.theme.AnimacionesLabTheme

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
            text = "Ejercicio 1: AnimatedVisibility",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { visible = !visible }
        ) {
            Text(
                text = if (visible) "Ocultar cuadro"
                else "Mostrar cuadro"
            )
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
                Text(
                    text = "¡Hola Compose!",
                    color = Color.White
                )
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
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Cambio de color"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ejercicio 2: animateColorAsState",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { isGreen = !isGreen }
        ) {
            Text(
                text = if (isGreen) "Cambiar a azul"
                else "Cambiar a verde"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(
                    color = animatedColor,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isGreen) "Color verde"
                else "Color azul",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Animación con tween de 1000 ms",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// EJERCICIO 3: Animación de tamaño y posición

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
        label = "Cambio de posicion"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ejercicio 3: Tamaño y Posición",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { expanded = !expanded }
        ) {
            Text(
                text = if (expanded) "Restablecer cuadro"
                else "Mover y agrandar"
            )
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
                        color = Color(0xFF9C27B0),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Animado",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Animación de tamaño y posición",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// EJERCICIO 4: AnimatedContent

enum class EstadoContenido {
    CARGANDO,
    CONTENIDO,
    ERROR
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
            text = "Ejercicio 4: AnimatedContent",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedContent(
            targetState = estado,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 700
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
            },
            label = "Transicion de estados"
        ) { estadoActual ->

            val color = when (estadoActual) {
                EstadoContenido.CARGANDO -> Color(0xFFFFA000)
                EstadoContenido.CONTENIDO -> Color(0xFF4CAF50)
                EstadoContenido.ERROR -> Color(0xFFE53935)
            }

            val mensaje = when (estadoActual) {
                EstadoContenido.CARGANDO -> "Cargando..."
                EstadoContenido.CONTENIDO -> "¡Contenido cargado!"
                EstadoContenido.ERROR -> "Ocurrio un error"
            }

            val descripcion = when (estadoActual) {
                EstadoContenido.CARGANDO ->
                    "Espere mientras se procesa la informacion"
                EstadoContenido.CONTENIDO ->
                    "La informacion se cargo correctamente"
                EstadoContenido.ERROR ->
                    "No se pudo completar la operacion"
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = mensaje,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = descripcion,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                estado = when (estado) {
                    EstadoContenido.CARGANDO ->
                        EstadoContenido.CONTENIDO
                    EstadoContenido.CONTENIDO ->
                        EstadoContenido.ERROR
                    EstadoContenido.ERROR ->
                        EstadoContenido.CARGANDO
                }
            }
        ) {
            Text("Cambiar estado")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Estado actual: ${estado.name}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Transicion: fadeIn 700 ms / fadeOut 500 ms",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
