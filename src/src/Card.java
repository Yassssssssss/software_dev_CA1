package src;

public class Card {
    private int value;

    public Card(int x){
        this.value = x;
    }

    public int getValue(){
        return value;
    }

    public boolean isSame(Card card) {
        return card.getValue() == this.value;
    }
}
