package net.jaskar;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;

    public Player() {
        this.hand = new ArrayList<>(10);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        int total = 0;
        int numberOfAces = 0;
        for (Card card : hand) {
            int cardValue = BlackjackGame.getCardValue(card);
            total += cardValue;
            if (card.face().trim().equalsIgnoreCase("A")) {
                numberOfAces++;
            }
        }
        while (total > 21 && numberOfAces > 0) {
            total -= 10;
            numberOfAces--;
        }
        return total;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }
}
