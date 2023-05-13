use board::Board;
use game::Game;
use player::Player;

mod board;
mod game;
mod player;

fn main() {
    let mut game = Game::new();

    game.greeting();

    loop {
        game.board().draw();

        game.gets_input_from_current_player();

        if game.is_won_by_any_player() {
            game.board().draw();
            print!("Player '");
            game.current_player.print();
            println!("' won! \\(^.^)/");
            break;
        }

        if game.is_over() {
            game.board().draw();
            println!("Match Draw! (._.)");
            break;
        }

        game.switch_player();
    }
}
