package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardGameTest {
    private final MockUserInputs validMockInputs = new MockUserInputs(4, "4players_preferences.txt");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * generateRing not tested as impossible to test without writing the same code
     * as in the actual function.
     */

    @Test
    public void testDealCards() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        // Gets private methods dealCards
        Method dealCards = CardGame.class.getDeclaredMethod("dealCards");
        CardGame cardGame = new CardGame(validMockInputs);
        dealCards.setAccessible(true);
        dealCards.invoke(cardGame);
        // Gets priavte field gameRing
        Field gameRingField = CardGame.class.getDeclaredField("gameRing");
        gameRingField.setAccessible(true);
        ArrayList<GameObject> gameRing = (ArrayList<GameObject>) gameRingField.get(cardGame);
        Field number = GameObject.class.getDeclaredField("num");
        number.setAccessible(true);

        // Test all objects have the expected cards after cards are dealt.
        for (GameObject obj : gameRing) {
            int num = (int) number.get(obj);
            for (Card c : obj.getCards()) {
                assertEquals(num, c.getValue());
            }
        }
    }

    
    @Test
    public void testMakeSingleMove() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException, SecurityException, NoSuchMethodException {
        CardGame cardGame = new CardGame(validMockInputs);
        // Gets the private method makeSingleMove
        Method makeSingleMove = CardGame.class.getDeclaredMethod("makeSingleMove", Player.class);
        makeSingleMove.setAccessible(true);

        CardGame testCardGame = new CardGame(new MockUserInputs(4, "test4players.txt"));
        // Gets the private method dealCards
        Method dealCards = CardGame.class.getDeclaredMethod("dealCards");
        dealCards.setAccessible(true);
        dealCards.invoke(testCardGame);
        // Gets priavte field gameRing
        Field gameRingField = CardGame.class.getDeclaredField("gameRing");
        gameRingField.setAccessible(true);
        ArrayList<GameObject> gameRing = (ArrayList<GameObject>) gameRingField.get(testCardGame);

        makeSingleMove.invoke(testCardGame, (Player) gameRing.get(1));

        // Makes the expected Deck and Player classes
        Deck expectedLeft = new Deck(1);
        expectedLeft.addCard(new Card(1));
        expectedLeft.addCard(new Card(1));
        expectedLeft.addCard(new Card(1));

        Player expectedPlayer = new Player(1);
        expectedPlayer.addCard(new Card(1));
        expectedPlayer.addCard(new Card(1));
        expectedPlayer.addCard(new Card(1));
        expectedPlayer.addCard(new Card(1));

        Deck expectedRight = new Deck(2);
        expectedRight.addCard(new Card(3));
        expectedRight.addCard(new Card(3));
        expectedRight.addCard(new Card(3));
        expectedRight.addCard(new Card(3));
        expectedRight.addCard(new Card(3));

        // Check if the left deck, player and right deck have the expected outputs
        assertTrue(String.format("Left Deck failed. Expected %s but got %s",
                expectedLeft.cardsToStringList().toString(), gameRing.get(0).cardsToStringList().toString()),
                gameRing.get(0).isSame(expectedLeft));
        assertTrue(String.format("Player failed. Expected %s but got %s", expectedPlayer.cardsToStringList().toString(),
                gameRing.get(1).cardsToStringList().toString()), gameRing.get(1).isSame(expectedPlayer));
        assertTrue(String.format("Right deck failed. Expected %s but got %s",
                expectedRight.cardsToStringList().toString(), gameRing.get(2).cardsToStringList().toString()),
                gameRing.get(2).isSame(expectedRight));

        ArrayList<GameObject> gameRing2 = (ArrayList<GameObject>) gameRingField.get(cardGame);
        dealCards.invoke(cardGame);
        makeSingleMove.invoke(cardGame, (Player) gameRing2.get(1));

        // Check the winner field is changed accordingly
        Field winner = CardGame.class.getDeclaredField("winner");
        winner.setAccessible(true);
        try {
            Thread.sleep(1000); // Delay to wait for threads to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, ((AtomicInteger) winner.get(testCardGame)).get());
        assertEquals(1, ((AtomicInteger) winner.get(cardGame)).get());
    }

    @Test
    public void testStartGame() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        // Gets the private method dealCards
        Method dealCards = CardGame.class.getDeclaredMethod("dealCards");

        CardGame testCardGame = new CardGame(new MockUserInputs(4, "4players_p1win.txt"));

        dealCards.setAccessible(true);
        dealCards.invoke(testCardGame);
        // Gets the private method startGame
        Method startGame = CardGame.class.getDeclaredMethod("startGame");
        startGame.setAccessible(true);
        startGame.invoke(testCardGame);
        // Gets the private field winner
        Field winner = CardGame.class.getDeclaredField("winner");
        winner.setAccessible(true);

        try {
            Thread.sleep(1000); // Delay to wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, ((AtomicInteger) winner.get(testCardGame)).get());
    }
}
