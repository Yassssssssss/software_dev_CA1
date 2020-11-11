package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import src.Card;

public class CardTest {
    private Card card;

    @Before
    public void setup() {
        this.card = new Card(3);
    }

    @Test
    public void testGetValue() {
        assertEquals(3, card.getValue());
        for (int i=0; i<20; i++) {
            assertEquals(i, new Card(i).getValue());
        }
        assertEquals(-2147483648, new Card(-2147483648).getValue());
        assertEquals(2147483647, new Card(2147483647).getValue());
    }

    @Test
    public void testIsSame() {
        assertTrue(card.isSame(new Card(3)));
        assertFalse(card.isSame(new Card(-1)));
        assertFalse(card.isSame(new Card(1028)));

        for (int i=0; i<20; i++) {
            assertTrue(new Card(i).isSame(new Card(i)));
            assertFalse(new Card(i).isSame(new Card(i+1)));
        }
        assertTrue(new Card(-2147483648).isSame(new Card(-2147483648)));
        assertTrue(new Card(2147483647).isSame(new Card(2147483647)));
    }
}
