import java.util.ArrayList


class Deck{
    private ArrayList<int> cards = new ArrayList<int>();

    public ArrayList getCards(){
        return this.cards;
    }

    public void addCard(int card) {
        cards.add(card);
    }
}