package Bonken.Game;

import java.util.ArrayList;
import java.util.Scanner;

public class CardHand {
    ArrayList<Card> hand;
    ArrayList<Card> playableCards;

    public CardHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void getPlayableCards(Card firstCard) {
        if (firstCard.suit == -1) {
            playableCards = hand;
        }
        playableCards = hasThisSuit(firstCard);
        if (playableCards.size() == 0) {
            playableCards = hand;
        }
    }

    public ArrayList<Card> hasThisSuit(Card card){
        ArrayList<Card> ret = new ArrayList<Card>();
        for (int i = 0; i < hand.size(); i++) {
            if (card.suit == hand.get(i).suit) {
                ret.add(hand.get(i));
            }
        }
        return ret;
    }

    public Card play(Card card) {
        //TODO
        getPlayableCards(card);
        System.out.println(playableCards);
        Scanner scanner = new Scanner(System.in);
        int playedCard = scanner.nextInt();
        Card tmp = playableCards.get(playedCard);
        hand.remove(tmp);
        return tmp;
    }

    @Override
    public String toString() {
        String ret = "";
        int cnt = 0;
        for (Card card : hand) {
            ret += card.toString();
            if (cnt >= 0 && cnt < 51) {
                ret += ", ";
            }
            cnt++;
        }
        return ret;
    }
}

