package net.jaskar;

public record Card(Suit suit, String face) {
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;

        public char getImage() {
            return (new char[]{9827, 9830, 9829, 9824})[this.ordinal()];
        }
    }

    @Override
    public String toString() {
        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        return "%s%c".formatted(faceString, suit.getImage());
    }

    public static Card getNumericCard(Suit suit, int cardNumber) {
        if (cardNumber > 1 && cardNumber < 11) {
            return new Card(suit, String.valueOf(cardNumber));
        }
        System.out.println("Invalid Numeric card selected");
        return null;
    }

    public static Card getFaceCard(Suit suit, char abbrev) {
        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            return new Card(suit, "" + abbrev);
        }
        System.out.println("Invalid Face card selected");
        return null;
    }
}
