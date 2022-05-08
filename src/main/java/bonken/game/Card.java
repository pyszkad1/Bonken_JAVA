package bonken.game;

public class Card {
    private int suit;
    private int rank;

    //suit definitions
    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    //ranks definitions
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;


    public final String[] suitNames = new String[]
            {"Clubs", "Diamonds", "Hearts", "Spades"};

    public final String[] faceNames = new String[]
            {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    public Card(int rank, int suit) {
        // TODO check -1 suit
        if (suit == -1) {
        } else if ((suit < 0) || (suit > 3) || (rank < 2) || (rank > 14)) {
            throw new IllegalArgumentException();
        }
        this.suit = suit;
        this.rank = rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return faceNames[rank - 2] + " of " + suitNames[suit];
    }

}
