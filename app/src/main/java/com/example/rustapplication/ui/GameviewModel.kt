package com.example.rustapplication.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rustapplication.lib.Game
import com.example.rustapplication.lib.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun switch_player(currentPlayer: String) {
        var newPlayer = "X"
        if (currentPlayer == "X") {
            newPlayer = "O"
        }
        _uiState.update { currentState ->
            currentState.copy(
                currentPlayer = newPlayer
            )
        }
    }

    fun reset() {
        _uiState.update { currentState -> currentState.copy(
            isGameOver = false,
            isGameWon = false,
            winner = "",
            board = Array(9) { _ -> "" },
            game = Game(),
            currentPlayer = "X",
            currentState = "X to play"
        ) }
    }

    fun updateMove(boardPosition: Int) {
        val updatedBoard = _uiState.value.board
        val currentPlayer = _uiState.value.currentPlayer
        updatedBoard[boardPosition] = currentPlayer
        switch_player(currentPlayer)
        val processInput = _uiState.value.game.processInput(boardPosition)

        if (processInput == GameState.XWon) {
            _uiState.update { currentState -> currentState.copy(
                isGameWon = true,
                winner = "X",
                isGameOver = true,
                currentState = "X Won!!"
            ) }
        }

        if (processInput == GameState.OWon) {
            _uiState.update { currentState -> currentState.copy(
                isGameWon = true,
                winner = "O",
                isGameOver = true,
                currentState = "O Won!!"

            ) }
        }

        if (processInput == GameState.DRAW) {
            _uiState.update { currentState -> currentState.copy(
                isGameWon = false,
                isGameOver = true,
                currentState = "Match Draw!"

            ) }
        }

        if (processInput == GameState.XToPlay) {
            _uiState.update { currentState -> currentState.copy(
                currentState = "X to play"
            ) }
        }


        if (processInput == GameState.OToPlay) {
            _uiState.update { currentState -> currentState.copy(
                currentState = "O to play"
            ) }
        }
        Log.v("rust", "gamestate=${processInput}")
        Log.v("rust", "board0=${updatedBoard[0]}")
        Log.v("rust", "board1=${updatedBoard[1]}")
        Log.v("rust", "board2=${updatedBoard[2]}")
        Log.v("rust", "board3=${updatedBoard[3]}")
        Log.v("rust", "board4=${updatedBoard[4]}")
        Log.v("rust", "board5=${updatedBoard[5]}")
        Log.v("rust", "board6=${updatedBoard[6]}")
        Log.v("rust", "board7=${updatedBoard[7]}")
        Log.v("rust", "board8=${updatedBoard[8]}")
        Log.v("rust", "boardPosition=${boardPosition}")
        _uiState.update { currentState ->
            currentState.copy(
                board = updatedBoard
            )
        }
    }
}