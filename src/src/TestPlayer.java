package src;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

    Player player;
    Deck leftDeck;
    Deck rightDeck;

    @Before
    public void setup(){
        player = new Player(1);
        leftDeck = new Deck(1);
        rightDeck = new Deck(2);
        for(int i=0; i<4; i++){
            rightDeck.addCard(new Card(3-i));
            leftDeck.addCard(new Card(i));
        } 
        for (int i=0; i<3; i++){
            player.addCard(new Card(1));
        }
        player.addCard(new Card(3));
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
}
