package net.jaskar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>(52);
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                cards.add(Card.getNumericCard(suit, i));
            }
            for (char c : new char[]{'J', 'Q', 'K', 'A'}) {
                cards.add(Card.getFaceCard(suit, c));
            }
        }
    }

    public static List<Card> getStandardDeck() {
        List<Card> deck = new ArrayList<>(52);
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                deck.add(Card.getNumericCard(suit, i));
            }
            for (char c : new char[]{'J', 'Q', 'K', 'A'}) {
                deck.add(Card.getFaceCard(suit, c));
            }
        }
        return deck;
    }

    public void printDeck() {
        printDeck("Current Deck", 4);
    }

    public void printDeck(String description, int rows) {
        System.out.println("---------------------------");
        if (description != null) {
            System.out.println(description);
        }
        int cardsInRow = cards.size() / rows;
        for (int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;
            cards.subList(startIndex, endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot deal from an empty cards.");
        }
        return cards.remove(0);
    }
}
