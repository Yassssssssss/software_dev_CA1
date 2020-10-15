package src;

import java.util.ArrayList;


class Player{
    private ArrayList<Integer> cards = new ArrayList<Integer>();

    public ArrayList<Integer> getCards(){
        return this.cards;
    }

    public void addCard(int card) {
        cards.add(card);
    }
}
