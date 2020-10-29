package src;

import java.util.Random;

class Player extends GameObject  {
    private int preference;

    public Player(int x){
        super();
        this.preference = x;
    }

    public void makeMove(Deck left, Deck right){
        Random r = new Random();
        cards.add(left.popTop());
        Card c = cards.get(r.nextInt(cards.size()));
        while(c.getValue() == this.preference){
            c = cards.get(r.nextInt(cards.size()));
        }
        right.addToBottom(c);
        cards.remove(c);
    }
}
