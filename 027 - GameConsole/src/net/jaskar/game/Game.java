package net.jaskar.game;

import java.util.*;

public abstract class Game<T extends Player> {
    private final String gameName;
    private final List<T> players = new ArrayList<>();
    private Map<Character, GameAction> standardActions = null;

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public abstract T createNewPlayer(String name);

    public abstract Map<Character, GameAction> getGameActions(int playerIndex);

    public int addPlayer(String playerName) {
        T player = createNewPlayer(playerName);
        if (player != null) {
            players.add(createNewPlayer(playerName));
            return players.size() - 1;
        }
        return -1;
    }

    public boolean executeGameAction(int player, GameAction action) {
        return action.action().test(player);
    }

    public boolean printPlayer(int playerIndex) {
        System.out.println(players.get(playerIndex));
        return false;
    }

    public boolean quitGame(int playerIndex) {
        Player player = players.get(playerIndex);
        System.out.println("Sorry to see you go, " + player.name());
        return true;
    }

    public String getGameName() {
        return gameName;
    }

    protected final T getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public Map<Character, GameAction> getStandardActions() {
        if (standardActions == null) {
            standardActions = new LinkedHashMap<>(Map.of(
                    'I', new GameAction('I', "Print Player Info", this::printPlayer),
                    'Q', new GameAction('Q', "Quit Game", this::quitGame)
            ));
        }
        return standardActions;
    }
}
