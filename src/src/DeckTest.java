package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {
    private Deck deck;

    @Before
    public void setup() {
        // Makes a deck containing 0, 1, 2, 3
        deck = new Deck(1);
        for (int i=0; i<4; i++) deck.addCard(new Card(i));
    }

    @Test
    public void testPopTop() {
        assertTrue(new Card(3).isSame(deck.popTop()));
        assertTrue(new Card(2).isSame(deck.popTop()));
        assertTrue(new Card(1).isSame(deck.popTop()));
        assertTrue(new Card(0).isSame(deck.popTop()));
        assertThrows(IndexOutOfBoundsException.class, () -> new Card(0).isSame(deck.popTop()));
    }

    @Test
    public void testAddToBottom() {
        // Adds a card to the bottm of deck then builds another deck in the same way
        // and tests if they are the same
        deck.addToBottom(new Card(4));
        Deck expected = new Deck(1);
        for (int i=0; i<4; i++) expected.addCard(new Card(i));
        expected.addToBottom(new Card(4));
        assertTrue(expected.isSame(deck));
    }

    @Test
    public void testGetNumber(){
        assertEquals(1, deck.getNumber());
    }

    @Test
    public void testWriteEnd() {
        // Tests different types of writeEnd() to different files
        try {
            deck.writeEnd(1);
            new Deck(2147483647).writeEnd(-2147483648);
            Deck tDeck = new Deck(-2147483648);
            tDeck.addCard(new Card(2147483647));
            tDeck.addToBottom(new Card(-2147483648));
            tDeck.writeEnd(2147483647);
        } catch (IOException e){
            e.printStackTrace();
        }
        assertEquals("Deck 1 contents: 0 1 2 3", TestSuite.readFromFile("Deck1_output.txt"));
        assertEquals("Deck 2147483647 contents: ", TestSuite.readFromFile("Deck2147483647_output.txt"));
        assertEquals("Deck -2147483648 contents: -2147483648 2147483647", TestSuite.readFromFile("Deck-2147483648_output.txt"));
    }
}
