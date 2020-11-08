package src;

import java.io.IOException;

/**
 * A deck class that allows cards to be removed from top of deck
 * and to be added to the bottom.
 */
class Deck extends GameObject {
    /**
     * @param int - The deck number
     */
    public Deck(int num) {
        this.fileName = "Deck" + Integer.toString(num) + "_output.txt";
        this.num = num;
    }


    /** 
     * Pops a card from top of the cards list in deck
     * 
     * @return Card - Top card
     */
    public Card popTop(){
        return cards.remove(cards.size()-1);
    }

    
    /** 
     * Add a card to the bottom of deck
     * 
     * @param card - Card to add
     */
    public void addToBottom(Card card){
        cards.add(0, card);
    }

    
    /** 
     * Getter for the deck number
     * 
     * @return int - Deck number
     */
    public int getNumber() {
        return num;
    }

    
    /** 
     * Writes the message that needs to be written at end to file
     * 
     * @param winner - The player number of player that won
     * @throws IOException - Thrown by writeToFile function and resetFile
     */
    public void writeEnd(int winner) throws IOException{
        resetFile();
        writeToFile(String.format("Deck %d contents: %s", num, String.join(" ", cardsToStringList())));
    }
}
