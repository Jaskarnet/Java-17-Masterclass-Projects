package net.jaskar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {
    private Deck deck;
    private List<Player> players;
    private Player dealer;
    private Scanner scanner;

    public BlackjackGame() {
        this(new Deck(), new ArrayList<>(List.of(new Player())));
    }

    public BlackjackGame(int numberOfPlayers) {
        this(new Deck(), createDefaultPlayers(numberOfPlayers));
    }

    public BlackjackGame(Deck deck, Collection<Player> players) {
        this.deck = deck;
        this.players = new ArrayList<>(players); // Convert to List for indexing
        this.dealer = new Player();
        this.scanner = new Scanner(System.in);
        deck.shuffle();
        dealInitialHands();
        System.out.println("Dealer's hand: " + dealer.getHand() + " (Value: " + dealer.getHandValue() + ")");
    }

    public static int getCardValue(Card card) {
        String face = card.face();
        return switch (face) {
            case "J", "Q", "K" -> 10;
            case "A" -> 11;
            default -> Integer.parseInt(face);
        };
    }

    private void dealInitialHands() {
        for (Player player : players) {
            player.addCard(deck.deal());
            player.addCard(deck.deal());
        }
        dealer.addCard(deck.deal());
        dealer.addCard(deck.deal());
    }

    private static List<Player> createDefaultPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }
        return players;
    }

    public void startGame() {
        int playerIndex = 1;
        for (Player player : players) {
            System.out.println("Player " + playerIndex + "'s turn:");
            playTurn(player);
            playerIndex++;
        }
        if (!dealer.isBusted()) {
            dealerPlay();
        }
        determineWinner();
    }

    private void playTurn(Player player) {
        boolean playerDone = false;
        while (!playerDone) {
            System.out.println("Your hand: " + player.getHand() + " (Value: " + player.getHandValue() + ")");
            System.out.println("Do you want to (H)it or (S)tand?");
            String action = scanner.nextLine().trim().toUpperCase();

            if ("H".equals(action)) {
                player.addCard(deck.deal());
                System.out.println("You drew: " + player.getHand().get(player.getHand().size() - 1));
                if (player.isBusted()) {
                    System.out.println("Busted! Your hand value is " + player.getHandValue());
                    playerDone = true; // End turn for this player
                }
            } else if ("S".equals(action)) {
                System.out.println("You stand.");
                playerDone = true; // End turn for this player
            } else {
                System.out.println("Invalid input. Please enter 'H' to hit or 'S' to stand.");
            }
        }
    }

    private void dealerPlay() {
        // Dealer's logic to hit until reaching at least 17
        while (dealer.getHandValue() < 17) {
            dealer.addCard(deck.deal());
            System.out.println("Dealer draws: " + dealer.getHand().get(dealer.getHand().size() - 1));
        }
        System.out.println("Dealer's hand: " + dealer.getHand() + " (Value: " + dealer.getHandValue() + ")");
    }

    private void determineWinner() {
        int dealerScore = dealer.getHandValue();
        boolean dealerBusted = dealerScore > 21;

        System.out.println("Dealer's hand value: " + dealerScore);
        if (dealer.isBusted()) {
            System.out.println("Dealer busts!");
        }

        int playerIndex = 1;
        for (Player player : players) {
            int playerScore = player.getHandValue();
            System.out.println("Player " + playerIndex + "'s hand value: " + playerScore);
            if (player.isBusted()) {
                System.out.println("Player " + playerIndex + " busts!");
            } else if (dealerBusted) {
                System.out.println("Dealer busts! Player " + playerIndex + " wins!");
            } else if (playerScore > dealerScore) {
                System.out.println("Player " + playerIndex + " wins!");
            } else if (playerScore == dealerScore) {
                System.out.println("Player " + playerIndex + " ties with the dealer!");
            } else {
                System.out.println("Dealer wins against Player " + playerIndex + "!");
            }
            playerIndex++;
        }
    }
}
