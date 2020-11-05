package src;

import java.io.IOException;

class Deck extends GameObject {
    private int num;

    public Deck(int num) {
        this.fileName = "Deck" + Integer.toString(num) + "_output.txt";
        this.num = num;
    }

    public Card popTop(){
        return cards.remove(cards.size()-1);
    }

    public void addToBottom(Card card){
        cards.add(0, card);
    }

    public int getNumber() {
        return num;
    }

    public void writeEnd(int winner) throws IOException{
        resetFile();
        writeToFile(String.format("Deck %d contents: %s", num, String.join(" ", cardsToStringList())));
    }
}
