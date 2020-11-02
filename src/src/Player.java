package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

class Player extends GameObject{
    public int preference;

    public Player(int p){
        super();
        this.preference = p;
    }

    public boolean checkWon(){
        int checkCard = cards.get(0).getValue();
        for(Card c : cards){
            if(c.getValue() != checkCard) {
                return false;
            } 
        }
        System.out.println("Player" + this.preference + "has won");
        return true;
    }

    public synchronized Boolean makeMove(Deck left, Deck right){
        System.out.println(new HashSet<Card>(cards).toString());
        if(checkWon()){
            return true;
        }
        Random r = new Random();
        cards.add(left.popTop());
        Card c = cards.get(r.nextInt(cards.size()));
        while(c.getValue() == this.preference){
            c = cards.get(r.nextInt(cards.size()));
        }
        right.addToBottom(c);
        cards.remove(c);
        System.out.println("Player: " + Integer.toString(preference) + "::");
        printCards();
        return false;
    }
}
