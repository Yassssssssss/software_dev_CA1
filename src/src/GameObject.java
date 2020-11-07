package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Implements all the attributes and methods shared by Deck and Player
 * 
 * Attributes:
 * fileName - the name of the file to write to
 * cards - the hand for the Player or the cards in a Deck
 */
public abstract class GameObject {
    protected String fileName;
    protected volatile ArrayList<Card> cards = new ArrayList<Card>();
    

    /** 
     * Adds a card to the top of cards list
     * 
     * @param card - card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    

    /** 
     * Checks if another instance of a Gameobject has the same
     * list of cards(by value). The order matters.
     * Time complexity: O(n)
     * 
     * @param obj - Deck or Player to compare
     * @return boolean - true if the list of cards is the same, false if not.
     */
    public boolean isSame(GameObject obj) {
        if (obj.getCards().size() != cards.size()) return false;

        for (int i=0; i<cards.size(); i++) {
            if (cards.get(i).getValue() != obj.getCards().get(i).getValue()) return false;
        }
        return true;
    }

    
    /** 
     * Changes the list of Card to a list of String of the value of the cards.
     * Used for printing and writing to file purposes.
     * Time complexity O(n).
     * 
     * @return ArrayList<String> - A list of value of the cards
     */
    public ArrayList<String> cardsToStringList() {
        ArrayList<String> printList = new ArrayList<String>();
        for (Card c: cards) printList.add(Integer.toString(c.getValue()));
        return printList;
    }

    
    /** 
     * Wipes file.
     * @throws IOException - Thrown by FileWriter
     */
    protected void resetFile() throws IOException {
        FileWriter fileWriter = new FileWriter(this.fileName);
        fileWriter.close();
    }

    
    /** 
     * Writes a message to the file for this object
     * 
     * @param s - Message to write
     * @throws IOException - Thrown by FileWriter
     */
    protected void writeToFile(String s) throws IOException{
        FileWriter fileWriter = new FileWriter(this.fileName, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf(s + "\n");
        printWriter.close();
        fileWriter.close();
    }

    /** 
     * Getter for the cards
     * 
     * @return ArrayList<Card>
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }

    /**
     * Function to write the necessary message for the end of game.
     */
    abstract void writeEnd(int winner) throws IOException;
}
