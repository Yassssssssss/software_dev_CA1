package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import src.Card;
import src.ConcreteGameObj;
import src.GameObject;

public class GameObjectTest {
    GameObject gameObj;

    @Before
    public void setup() {
        // Sets up a game object containing cars 0, 1, 2, 3
        gameObj = new ConcreteGameObj();
        for (int i=0; i<4; i++) gameObj.addCard(new Card(i));
    }


    /**
     * AddCard and isSame is tested in the same function since
     * we can't be certain that addCard works without comparing
     * the result with the expected cards using the isSame function.
     *
     * No need to test cards at the edge of the integer limit as 
     * that is tested in TestCard
     */
    @Test
    public void testAddAndIsSame() {
        ConcreteGameObj testObj = new ConcreteGameObj();
        for (int i=0; i<4; i++) testObj.addCard(new Card(i));
        assertTrue(testObj.isSame(gameObj));
        testObj.addCard(new Card(0));
        assertFalse(testObj.isSame(gameObj));
        testObj.addCard(new Card(-2147483648));
        gameObj.addCard(new Card(0));
        gameObj.addCard(new Card(-2147483648));
        assertTrue(testObj.isSame(gameObj));
        testObj.addCard(new Card(0));
        testObj.addCard(new Card(2147483647));
        gameObj.addCard(new Card(2147483647)); 
        assertFalse(testObj.isSame(gameObj));
    }

    @Test
    public void testCardsToStringList() {
        ArrayList<String> expectedList = new ArrayList<String>(Arrays.asList(new String[]{"0", "1", "2", "3"}));
        assertEquals(expectedList, gameObj.cardsToStringList());
        gameObj.addCard(new Card(-2147483648));
        expectedList.add("-2147483648");
        assertEquals(expectedList, gameObj.cardsToStringList());
    }

    /**
     * Resetting and writing to file is tested in the same 
     * function as wiping a file cannot be tested without 
     * writing something to a file first.
     */
    @Test
    public void testResetAndWriteToFile() {
        try{
            // Tests if writeToFile() can write obscure characters as well as write to many files
            gameObj.resetFile();
            gameObj.writeToFile("testing...1234 \n I&*^!");
            assertEquals("testing...1234  I&*^!", TestSuite.readFromFile("test.txt"));
            gameObj.resetFile();
            assertEquals("", TestSuite.readFromFile("test.txt"));
            gameObj.writeToFile(" ");
            assertEquals(" ", TestSuite.readFromFile("test.txt"));
            gameObj.writeToFile("testing 2\n");
            assertEquals(" testing 2", TestSuite.readFromFile("test.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
