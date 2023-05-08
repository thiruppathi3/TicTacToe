package com.playground.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playground.tictactoe.ui.theme.JetpackTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    Board()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var context = LocalContext.current
    Row(Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Hi $name!",
            modifier = modifier
        )

        Spacer(Modifier.size(50.dp))
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = { Toast.makeText(context, "Click!!", Toast.LENGTH_SHORT).show() },
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Text(text = "Click!!")
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackTestAppTheme {
        Greeting("Android")
    }
}

@Composable
fun Board() {
    Box(contentAlignment = Alignment.Center) {
        var isPlayerX by remember {
            mutableStateOf(true)
        }
        Column() {
            Text(
                text = "Tic Tac Toe",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(10.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(9) {
                    var state by remember {
                        mutableStateOf("")
                    }

                    var enable by remember {
                        mutableStateOf(true)
                    }

                    Box(
                        modifier = Modifier
                            .clickable(enabled = enable) {
                                state = if (isPlayerX) "X" else "O"
                                isPlayerX = !isPlayerX
                                enable = false
                            }
                            .border(
                                BorderStroke(1.dp, Color.Blue)
                            )
                            .height(48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = state, color = Color.Blue, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board()
}