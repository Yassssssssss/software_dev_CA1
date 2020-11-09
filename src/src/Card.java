package src;

public class Card {
    private final int value;

    public Card(int x){
        this.value = x;
    }

    public int getValue(){
        return value;
    }

    /**
     * Checks if a card has the same value as this card
     * 
     * @param card - Card to compare with
     * @return - true if same, false if not.
     */
    public boolean isSame(Card card) {
        return card.getValue() == value;
    }
}
