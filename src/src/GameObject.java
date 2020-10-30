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

    public boolean isSame(GameObject obj) {
        if (obj.getCards().size() != cards.size()) return false;

        for (int i=0; i<cards.size(); i++) {
            if (cards.get(i).getValue() != obj.getCards().get(i).getValue()) return false;
        }
        return true;
    }
}
