package com.example.rustapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = "Tic Tac Toe",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(10.dp))
            Board(gameViewModel, gameUiState, onClicked = { gameViewModel.updateMove(it) })


            Text(text = gameUiState.currentState, Modifier.padding(5.dp))

            if (gameUiState.isGameOver || gameUiState.isGameWon)
                Button(
                    onClick = {
                        gameViewModel.reset()
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Reset", fontSize = 28.sp)
                }
        }
    }
}

@Composable
private fun Board(
    gameViewModel: GameViewModel,
    gameUiState: GameUiState,
    onClicked: (Int) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(9) {

            Box(
                modifier = Modifier
                    .clickable(
                        enabled = gameUiState.board[it] == ""
                    ) {
                        onClicked(it)
                    }
                    .border(
                        BorderStroke(1.dp, Color.Blue)
                    )
                    .height(48.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = gameUiState.board[it],
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}