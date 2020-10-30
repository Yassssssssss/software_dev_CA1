package src;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestGameObject {
    GameObject gameObj;


    @Before
    public void setup() {
        this.gameObj = new GameObject();
    }

    @Test
    public void testAddGetCard() {
        GameObject expected = new GameObject();
        for (int i=0; i < 10; i++) {
            expected.addCard(new Card(i));
            gameObj.addCard(new Card(i));
        }
        assertTrue(gameObj.isSame(expected));
    }
}
