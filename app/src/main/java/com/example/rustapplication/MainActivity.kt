package com.example.rustapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustapplication.lib.Game
import com.example.rustapplication.ui.theme.RustApplicationTheme

private const val TAG = "TicTacToeActivity"

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("rust_lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RustApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Homepage()
                    Board()
                }
            }
        }

    }
}

@Composable
fun Board() {
    val game = Game()
    Box(contentAlignment = Alignment.Center) {
        var isPlayerX by remember {
            mutableStateOf(true)
        }
        Column {
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
                            .clickable(
                                enabled = (enable)
                            ) {
                                state = if (isPlayerX) "X" else "O"
                                isPlayerX = !isPlayerX
                                enable = false
                                val gameState = game.input(it)
                                Log.v(TAG, "Game State=$gameState");
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