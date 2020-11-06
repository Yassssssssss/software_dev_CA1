package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

    Player player;
    Player player2;
    Deck leftDeck;
    Deck rightDeck;

    @Before
    public void setup() throws IOException{
        FileWriter fileWriter = new FileWriter("Player1_output.txt");
        FileWriter fileWriter2 = new FileWriter("Player2_output.txt");
        fileWriter.close();
        fileWriter2.close();
        player = new Player(1);
        player2 = new Player(2);
        leftDeck = new Deck(1);
        rightDeck = new Deck(2);
        for(int i=0; i<4; i++){
            rightDeck.addCard(new Card(3-i));
            leftDeck.addCard(new Card(i));
        } 
        for (int i=0; i<3; i++){
            player.addCard(new Card(1));
            player2.addCard(new Card(2));
        }
        player.addCard(new Card(3));
    }
    @Test
    public void testCheckWon(){
        //TODO
    }

    @Test
    public void testMakeMove() throws IOException{
        player.makeMove(leftDeck, rightDeck);
        Deck rightExpected = new Deck(2);
        Deck leftExpected = new Deck(1);
        for(int i=0; i<4; i++){
            rightExpected.addCard(new Card(3-i));
            leftExpected.addCard(new Card(i));
        }
        rightExpected.addToBottom(new Card(3));
        leftExpected.popTop();
        assertTrue(leftExpected.isSame(leftDeck));
        assertTrue(rightExpected.isSame(rightDeck));
    }

    @Test
    public void testWriteDeckToFile(){
        String data = "";
        try {
            player.writeDeckToFile();
            File file = new File("Player1_output.txt");
            Scanner reader = new Scanner(file);
            data = reader.nextLine();
            reader.close();
        } catch (IOException e){
        }
        assertEquals("Player 1 Initial hand: 1 1 1 3", data);
    }

    @Test
    public void testWriteEnd() throws IOException {
        
        String player1Output = "";
        String player2Output = "";
        try {
            player.writeEnd(1);
            player2.writeEnd(1);
            File file = new File("Player1_output.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                player1Output += reader.nextLine();
              }
            reader.close();
            File file2 = new File("Player2_output.txt");
            Scanner reader2 = new Scanner(file2);
            while (reader2.hasNextLine()) {
                player2Output += reader2.nextLine();
              }
            reader2.close();
        } catch (IOException e){

        }
        assertEquals("Player 1 winsPlayer 1 exitsPlayer 1 hand: 1 1 1 3", player1Output);
        assertEquals("Player 1 has informed player 2 that player 1 has wonPlayer 2 exitsPlayer 2 hand: 2 2 2"
        , player2Output);
    }
}

