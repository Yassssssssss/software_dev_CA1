package src;

import java.io.IOException;
import java.util.Random;

class Player extends GameObject{
    public int preference;

    public Player(int p){
        this.fileName = "Player" + Integer.toString(p) + "_output.txt";
        this.preference = p;
    }

    public boolean checkWon() throws IOException {
        int checkCard = cards.get(0).getValue();
        for(Card c : cards){
            if(c.getValue() != checkCard) {
                return false;
            } 
        }
        String message = "Player " + this.preference + " wins";
        System.out.println(message);
        return true;
    }

    public int makeMove(Deck left, Deck right) throws IOException{
        if(checkWon()){
            return preference;
        }
        Random r = new Random();
        Card takenCard = left.popTop();
        cards.add(takenCard);
        Card discardCard = cards.get(r.nextInt(cards.size()));
        while(discardCard.getValue() == this.preference){
            discardCard = cards.get(r.nextInt(cards.size()));
        }
        right.addToBottom(discardCard);
        cards.remove(discardCard);
        String message = String.format("Player %d draws a %d from deck %d \nPlayer %d discards a %d to deck %d \nPlayer %d current hand is %s", 
                                       preference, takenCard.getValue(), left.getNumber(),
                                       preference, discardCard.getValue(), right.getNumber(),
                                       preference, String.join(" ", cardsToStringList()));

        System.out.println(message);
        writeToFile(message);
        return -1;
    }

    public void writeDeckToFile() throws IOException{
        resetFile();
        writeToFile("Player " + this.preference + " Initial hand: " + String.join(" ", cardsToStringList()));
    }

    public void writeEnd(int winner) throws IOException {
        String message;
        if (preference == winner) message = "Player " + preference + " wins";
        else message = String.format("Player %d has informed player %d that player %d has won", winner, preference, winner);
        message += String.format("\nPlayer %d exits\n", preference);
        message += String.format("Player %d hand: %s", preference, String.join(" ", cardsToStringList()));
        writeToFile(message);
    }
}
