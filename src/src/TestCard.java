package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestCard {
    private Card card;

    @Before
    public void setup() {
        this.card = new Card(3);
    }

    @Test
    public void testGetValue() {
        assertEquals(3, card.getValue());
    }

    @Test
    public void testIsSame() {
        assertTrue(card.isSame(new Card(3)));
        assertFalse(card.isSame(new Card(-1)));
        assertFalse(card.isSame(new Card(1028)));
    }
}
