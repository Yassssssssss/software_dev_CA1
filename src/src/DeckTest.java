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
        deck.addToBottom(new Card(4));
        Deck expected = new Deck(1);
        for (int i=0; i<4; i++) expected.addCard(new Card(i));
        expected.addToBottom(new Card(4));
        assertTrue(expected.isSame(deck));
    }

    @Test
    public void testWriteEnd() {
        try {
            deck.writeEnd(1);
        } catch (IOException e){
            e.printStackTrace();
        }

        String data = TestSuite.readFromFile("Deck1_output.txt");
        assertEquals("Deck 1 contents: 0 1 2 3", data);
    }
}
