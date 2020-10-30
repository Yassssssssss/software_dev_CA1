package src;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestDeck {
    private Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
        for (int i=0; i<4; i++) deck.addCard(new Card(i));
    }

    @Test
    public void testPopTop() {
        assertTrue(new Card(3).isSame(deck.popTop()));
        Deck expected = new Deck();
        for (int i=0; i<3; i++) expected.addCard(new Card(i));
    }

    @Test
    public void addToBottom() {
        deck.addToBottom(new Card(4));
        Deck expected = new Deck();
        for (int i=0; i<4; i++) expected.addCard(new Card(i));
        expected.addToBottom(new Card(4));
        assertTrue(expected.isSame(deck));
        
    }
}
