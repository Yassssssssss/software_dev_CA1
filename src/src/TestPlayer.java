package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
    Player player;

    /**
     * Adds 0, 1, 2, 3 to player's hand
     */
    private void addFourCards() {
        for (int i=0; i<4; i++) player.addCard(new Card(i));
    }

    @Before
    public void setup(){
        player = new Player(1);
    }

    
    /** 
     * @throws IOException
     */
    @Test
    public void testCheckWon() throws IOException {
        for (int i=0; i<4; i++) player.addCard(new Card(1));
        assertEquals(true, player.checkWon());
        addFourCards();
        assertEquals(false, player.checkWon());
    }

    
    /** 
     * @throws IOException
     */
    @Test
    public void testMakeMove() throws IOException{
        // Creating left and right decks to the player
        // Left deck contains 0, 1, 2, 3
        // Right deck contains 3, 2, 1, 0
        // player contains 1, 1, 1, 1 so movement is predictable
        Deck actualLeftDeck = new Deck(1);
        Deck actualRightDeck = new Deck(2);
        for(int i=0; i<4; i++){
            actualRightDeck.addCard(new Card(3-i));
            actualLeftDeck.addCard(new Card(i));
            player.addCard(new Card(1));
        }

        // Creating expected left and right decks
        Deck rightExpected = new Deck(2);
        Deck leftExpected = new Deck(1);
        for(int i=0; i<4; i++){
            rightExpected.addCard(new Card(3-i));
            leftExpected.addCard(new Card(i));
        }
        rightExpected.addToBottom(new Card(3));
        leftExpected.popTop();

        // Creating expected player
        Player expectedPlayer = new Player(1);
        for (int i=0; i<4; i++) expectedPlayer.addCard(new Card(1));

        assertEquals(1, player.makeMove(actualLeftDeck, actualRightDeck, false));
        
        // Bypass checking if player won.
        player.makeMove(actualLeftDeck, actualRightDeck, true);
        assertTrue(expectedPlayer.isSame(player));
        assertTrue(leftExpected.isSame(actualLeftDeck));
        assertTrue(rightExpected.isSame(actualRightDeck));

    }

    @Test
    public void testWriteDeckToFile(){
        addFourCards();
        String data = "";
        try {
            player.writeDeckToFile();
            File file = new File("Player1_output.txt");
            Scanner reader = new Scanner(file);
            data = reader.nextLine();
            reader.close();
        } catch (IOException e){
            System.out.println(e.getStackTrace());
        }
        assertEquals("Player 1 Initial hand: 0 1 2 3", data);
    }

    
    /** 
     * @throws IOException
     */
    @Test
    public void testWriteEnd() throws IOException {
        // Winning case (Actual hands doesn't matter)
        addFourCards();
        String data = "";
        try {
            player.resetFile();
            player.writeEnd(1);
            File file = new File("Player1_output.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data += reader.nextLine();
            }
            reader.close();
            assertEquals("Player 1 winsPlayer 1 exitsPlayer 1 hand: 0 1 2 3", data);
        } catch (IOException e){
            System.out.println(e.getStackTrace());
        }
        
        // Losing case
        try {
            player.resetFile();
            player.writeEnd(2);
            File file = new File("Player1_output.txt");
            Scanner reader = new Scanner(file);
            data = "";
            while (reader.hasNextLine()) {
                data += reader.nextLine();
            }
            reader.close();
            assertEquals("Player 2 has informed player 1 that player 2 has wonPlayer 1 exitsPlayer 1 hand: 0 1 2 3", data);
        } catch (IOException e){
            System.out.println(e.getStackTrace());
        }    
    }
}

