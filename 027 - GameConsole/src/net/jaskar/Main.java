package net.jaskar;

import net.jaskar.game.GameConsole;
import net.jaskar.pirate.PirateGame;

public class Main {
    public static void main(String[] args) {
        var console = new GameConsole<>(new PirateGame("The Pirate Game"));
        int playerIndex = console.addPlayer();
        console.playGame(playerIndex);
    }
}
