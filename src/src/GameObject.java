package src;

import java.util.ArrayList;

public class GameObject {
    protected ArrayList<Card> cards = new ArrayList<Card>();

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
