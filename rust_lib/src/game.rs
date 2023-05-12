use crate::board::BoardPosition;
use crate::board::BoardPosition::{NotOccupied, Occupied};
use rifgen::rifgen_attr::*;
use crate::{board::Board, player::Player};
use crate::GameState::{OWon, XWon};

#[generate_interface]
pub enum GameState {
    XWon,
    OWon,
    DRAW,
    Playing,
    Error,
}


pub struct Game {
    board: Board,
    current_player: Player,
}

impl Game {
    #[generate_interface(constructor)]
    pub fn new() -> Game {
        let board = Board::new();
        let player = Player::X;
        Self { board, current_player: player }
    }

    pub fn board(&self) -> Board {
        self.board
    }

    pub fn greeting(&self) {
        println!(
            "\nRust TicTacToe\n\
         --------------\n\
         A simple game written in the rust programming language.\n\
         Code is available at: https://github.com/flofriday/tictactoe"
        )
    }

    // #[generate_interface]
    pub fn get_current_player(&self) -> i32 {
        if self.current_player.is_x() { 0 } else { 1 }
    }

    pub fn gets_input_from_current_player(&mut self) {
        loop {
            print!("Player '");
            self.current_player.print();
            println!("', enter a number: ");

            let mut input = String::new();
            if std::io::stdin().read_line(&mut input).is_err() {
                println!("Couldn't read line! Try again.");
                continue;
            }

            if let Ok(number) = input.trim().parse::<usize>() {
                if number < 1 || number > 9 {
                    println!("The field number must be between 1 and 9.");
                    continue;
                }
                let number = number - 1;
                self.input(number as i32);
                break;
            } else {
                println!("Only numbers are allowed.");
                continue;
            }
        }
    }
    #[generate_interface]
    pub fn input(&mut self, number: i32) -> GameState {
        let mut state = self.board().state();
        match state[number as usize] {
            NotOccupied(_) => {}
            Occupied(player) =>
                if player == Player::X || player == Player::O {
                    print!("This field is already taken by '");
                    player.print();
                    println!("'.");
                }
        }

        state[number as usize] = Occupied(self.current_player);

        self.board.update_state(state);

        if self.is_won_by_any_player() {
            return match self.current_player {
                Player::X => {
                    XWon
                }
                Player::O => {
                    OWon
                }
            };
        }
        if self.is_over() {
            return GameState::DRAW;
        }
        GameState::Playing
    }

    // #[generate_interface]
    pub fn is_won_by_any_player(&self) -> bool {
        let state = self.board().state();
        for board_position in 0..3 {
            if state[board_position] == state[board_position + 3] && state[board_position] == state[board_position + 6] {
                return true;
            }

            let board_position = board_position * 3;

            if state[board_position] == state[board_position + 1] && state[board_position] == state[board_position + 2] {
                return true;
            }
        }

        if (state[0] == state[4] && state[0] == state[8])
            || (state[2] == state[4] && state[2] == state[6])
        {
            return true;
        }

        false
    }

    // #[generate_interface]
    pub fn is_over(&self) -> bool {
        self.board().state().iter().all(|&v| v == Occupied(Player::X) || v == Occupied(Player::O))
    }

    // #[generate_interface]
    pub fn switch_player(&mut self) {
        self.current_player = self.current_player.switch();
    }
}

#[cfg(test)]
mod game_tests {
    use board::BoardPosition::{NotOccupied, Occupied};
    use Player;
    use Player::{O, X};
    use std::io::BufRead;
    use std::io::Write;

    use crate::board::Board;

    use super::Game;

    #[test]
    fn should_return_true_if_game_has_been_won_by_either_of_the_player() {
        let state = [
            Occupied(X),
            Occupied(X),
            Occupied(X),
            NotOccupied(4),
            NotOccupied(5),
            NotOccupied(6),
            NotOccupied(7),
            NotOccupied(8),
            NotOccupied(9),
        ];
        let mut board = Board::new();
        board.update_state(state);
        let game = Game::new(board, Player::O);

        let game_won = game.is_won_by_any_player();

        assert!(game_won);
    }

    #[test]
    fn should_return_false_if_game_has_not_been_won_by_either_of_the_player() {
        let state = [
            Occupied(X),
            Occupied(O),
            Occupied(X),
            NotOccupied(4),
            NotOccupied(5),
            NotOccupied(6),
            NotOccupied(7),
            NotOccupied(8),
            NotOccupied(9),
        ];
        let mut board = Board::new();
        board.update_state(state);
        let game = Game::new(board, Player::O);

        let game_won = game.is_won_by_any_player();

        assert!(!game_won);
    }

    #[test]
    fn should_return_true_if_game_is_over() {
        let state = [
            Occupied(X),
            Occupied(O),
            Occupied(X),
            Occupied(X),
            Occupied(O),
            Occupied(X),
            Occupied(O),
            Occupied(X),
            Occupied(O),
        ];
        let mut board = Board::new();
        board.update_state(state);
        let game = Game::new(board, Player::O);

        let game_over = game.is_over();

        assert!(game_over);
    }

    #[test]
    fn should_return_false_if_game_is_not_over() {
        let state = [
            Occupied(X),
            Occupied(O),
            Occupied(X),
            Occupied(X),
            Occupied(O),
            Occupied(X),
            Occupied(O),
            Occupied(X),
            NotOccupied(9)
        ];
        let mut board = Board::new();
        board.update_state(state);
        let game = Game::new(board, Player::O);

        let game_over = game.is_over();

        assert!(!game_over);
    }
}
