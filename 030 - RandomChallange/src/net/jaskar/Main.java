package net.jaskar;

import net.jaskar.dice.DiceGame;
import net.jaskar.game.GameConsole;

public class Main {
    public static void main(String[] args) {
        var console = new GameConsole<>(new DiceGame("Dice Rolling Game"));
        console.playGame(console.addPlayer());
    }
}
