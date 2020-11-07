package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class GameObject {
    protected String fileName;
    protected volatile ArrayList<Card> cards = new ArrayList<Card>();

    
    /** 
     * @return ArrayList<Card>
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }

    
    /** 
     * @param card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    
    /** 
     * @param obj
     * @return boolean
     */
    public boolean isSame(GameObject obj) {
        if (obj.getCards().size() != cards.size()) return false;

        for (int i=0; i<cards.size(); i++) {
            if (cards.get(i).getValue() != obj.getCards().get(i).getValue()) return false;
        }
        return true;
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> cardsToStringList() {
        ArrayList<String> printList = new ArrayList<String>();
        for (Card c: cards) printList.add(Integer.toString(c.getValue()));
        return printList;
    }

    
    /** 
     * @throws IOException
     */
    protected void resetFile() throws IOException {
        FileWriter fileWriter = new FileWriter(this.fileName);
        fileWriter.close();
    }

    
    /** 
     * @param s
     * @throws IOException
     */
    protected void writeToFile(String s) throws IOException{
        FileWriter fileWriter = new FileWriter(this.fileName, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf(s + "\n");
        printWriter.close();
        fileWriter.close();
    }

    abstract void writeEnd(int winner) throws IOException;
}
