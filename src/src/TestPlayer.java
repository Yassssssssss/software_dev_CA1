package src;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

    Player player;
    Deck leftDeck;
    Deck rightDeck;

    @Before
    public void setup(){
        player = new Player(1);
        leftDeck = new Deck();
        rightDeck = new Deck();
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
    public void testMakeMove(){
        player.makeMove(leftDeck, rightDeck);
        Deck rightExpected = new Deck();
        Deck leftExpected = new Deck();
        for(int i=0; i<4; i++){
            rightExpected.addCard(new Card(3-i));
            leftExpected.addCard(new Card(i));
        }
        rightExpected.addToBottom(new Card(3));
        leftExpected.popTop();
        //assertTrue(leftExpected.equals(leftDeck.getCards()));
        //assertTrue(rightExpected.equals(rightDeck.getCards()));
        leftExpected.printCards();
        System.out.println("NEW DECK START");
        rightExpected.printCards();
        System.out.println("NEW DECK START");
        leftDeck.printCards();
        System.out.println("NEW DECK START");
        rightDeck.printCards();
    }
    public static void main(String[] args) {
        TestPlayer player = new TestPlayer();
        player.setup();
        player.testMakeMove();
    } 
}
