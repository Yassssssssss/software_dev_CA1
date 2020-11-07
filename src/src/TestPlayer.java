package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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

    
    @Test
    public void testCheckWon() {
        for (int i=0; i<4; i++) player.addCard(new Card(1));
        assertEquals(true, player.checkWon());
        addFourCards();
        assertEquals(false, player.checkWon());
    }

    
    @Test
    public void testMakeMove() {
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

        int result = 0;
        try {
            result = player.makeMove(actualLeftDeck, actualRightDeck, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(1, result);
        
        // Bypass checking if player won.
        try {
            player.makeMove(actualLeftDeck, actualRightDeck, true);
        } catch (IOException e){
            e.printStackTrace();
        }

        assertTrue(expectedPlayer.isSame(player));
        assertTrue(leftExpected.isSame(actualLeftDeck));
        assertTrue(rightExpected.isSame(actualRightDeck));
    }

    @Test
    public void testWriteDeckToFile(){
        addFourCards();
        try {
            player.writeDeckToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = TestSuite.readFromFile("Player1_output.txt");
        assertEquals("Player 1 Initial hand: 0 1 2 3", data);
    }

    
    /** 
     * @throws IOException
     */
    @Test
    public void testWriteEnd() {
        // Winning case (Actual hands doesn't matter)
        addFourCards();
        try {
            player.resetFile();
            player.writeEnd(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = TestSuite.readFromFile("Player1_output.txt");
        assertEquals("Player 1 winsPlayer 1 exitsPlayer 1 hand: 0 1 2 3", data);
        
        // Losing case
        try {
            player.resetFile();
            player.writeEnd(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = TestSuite.readFromFile("Player1_output.txt");
        assertEquals("Player 2 has informed player 1 that player 2 has wonPlayer 1 exitsPlayer 1 hand: 0 1 2 3", data);
    }
}

