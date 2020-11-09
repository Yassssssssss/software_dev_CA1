package src;

import java.io.IOException;
import java.util.Random;

/**
 * A player class that handles each move a player makes
 * and writing to files.
 */
class Player extends GameObject{
    /**
     * @param p - The player's preference
     */
    public Player(int p){
        this.fileName = "Player" + Integer.toString(p) + "_output.txt";
        this.num = p;
    }

    /**
     * Method to check if player has won
     * Time complexity O(n)
     * 
     * @return - If the player has won
     */
    public boolean checkWon() {
        int checkCard = cards.get(0).getValue();
        for(Card c: cards){
            if(c.getValue() != checkCard) {
                return false;
            } 
        }
        String message = "Player " + this.num + " wins";
        System.out.println(message);
        return true;
    }

    
    /** 
     * Performs a single move for player and writes to file
     * 
     * @param left - The left deck i.e. the deck to take from
     * @param right - The right deck i.e. the deck to discard to
     * @param testing - Setting this to true allows the bypass of checking
     *                  if player has won
     * @return int - Returns the player's preference number if player has won.
     *               If not, -1 is returned.
     * @throws IOException - Thrown in writeToFile function
     */
    public int makeMove(Deck left, Deck right, boolean testing) throws IOException{
        // If testing, don't check if player has won to allow for testing further down
        if(checkWon() && !testing) return num;
        
        Random r = new Random();
        Card takenCard = left.popTop(); // Take from left
        cards.add(takenCard); // Add to own hand

        // Choose a random card until a card that is not the preference is chosen
        // and discard that card.
        // Could potentially induce an infinite loop
        Card discardCard = cards.get(r.nextInt(cards.size()));
        while(discardCard.getValue() == num){
            discardCard = cards.get(r.nextInt(cards.size()));
        }
        right.addToBottom(discardCard); // Add discarded card to right deck
        cards.remove(discardCard); // Remove card from hand
        
        String message = String.format("Player %d draws a %d from deck %d \nPlayer %d discards a %d to deck %d \nPlayer %d current hand is %s", 
                                       num, takenCard.getValue(), left.getNumber(),
                                       num, discardCard.getValue(), right.getNumber(),
                                       num, String.join(" ", cardsToStringList()));
        //System.out.println(message);
        writeToFile(message);
        // If testing, don't check if player has won to allow for testing further down
        if(checkWon()) return num;
        return -1;
    }

    
    /** 
     * Writes the initial hand to file
     * 
     * @throws IOException - Thrown by writeToFile function and resetFile
     */
    public void writeDeckToFile() throws IOException{
        resetFile();
        writeToFile("Player " + num + " Initial hand: " + String.join(" ", cardsToStringList()));
    }

    
    /** 
     * Writes the messages for the end of game to file.
     * 
     * @param winner - The preference number(player number) of the winner
     * @throws IOException - Thrown by writeToFile function
     */
    @Override
    public void writeEnd(int winner) throws IOException {
        String message;
        if (num == winner) message = "Player " + num + " wins";
        else message = String.format("Player %d has informed player %d that player %d has won", winner, num, winner);
        message += String.format("\nPlayer %d exits\n", num);
        message += String.format("Player %d hand: %s", num, String.join(" ", cardsToStringList()));
        writeToFile(message);
    }
}
