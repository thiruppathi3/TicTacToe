package com.example.rustapplication.ui

import com.example.rustapplication.lib.Game
import com.example.rustapplication.lib.GameState


/**
 * Data class that represents the game UI state
 */
data class GameUiState(
    val currentPlayer: String = "X",
    val board: Array<String> = Array(9) { _ -> "" },
    val isGameOver: Boolean = false,
    val isGameWon: Boolean = false,
    val winner: String = "",
    val game: Game = Game(),
    val currentState: String = "X to play"
)