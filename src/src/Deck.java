package src;

class Deck extends GameObject {

    public Card popTop(){
        return cards.remove(cards.size()-1);
    }

    public void addToBottom(Card card){
        cards.add(0, card);
    }

    public void printCards(){
        for(Card c: cards){
            System.out.println(c.getValue());
        }
    }
}
