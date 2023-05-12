extern crate termcolor;
use self::termcolor::{Color, ColorChoice, ColorSpec, StandardStream, WriteColor};

use std::io::Write;

use rifgen::rifgen_attr::*;

#[derive(Clone, Copy, Debug, PartialEq)]
pub enum Player {
    X,
    O,
}

const X: i32 = 0;

const O: i32 = 1;

impl Player {
    // #[generate_interface(constructor)]
    pub fn new(player_type: i32) -> Player {
        match player_type {
            X => Player::X,
            O => Player::O,
            _ => Player::X
        }
    }

    pub fn to_char(&self) -> char {
        if *self == Self::X {
            'X'
        } else {
            'O'
        }
    }

    pub fn is_x(&self) -> bool {
        if *self == Player::X { true } else { false }
    }

    // #[generate_interface]
    pub fn switch(&self) -> Player {
        if self.is_x() { Player::O } else { Player::X }
    }

    pub fn print(&self) {
        let mut stdout = StandardStream::stdout(ColorChoice::Always);

        if *self == Player::X {
            stdout
                .set_color(ColorSpec::new().set_fg(Some(Color::Blue)))
                .unwrap();
        } else if *self == Player::O {
            stdout
                .set_color(ColorSpec::new().set_fg(Some(Color::Green)))
                .unwrap();
        }

        write!(&mut stdout, "{}", self.to_char()).unwrap();
        stdout.reset().unwrap();
    }
}

#[cfg(test)]
mod player_tests {
    use super::Player;

    #[test]
    fn should_switch_player() {
        let mut player = Player::X;

        player.switch();

        assert_eq!(player, Player::O);
    }
}