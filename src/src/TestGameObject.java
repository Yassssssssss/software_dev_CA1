package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestGameObject {
    GameObject obj;


    @Before
    public void gameObjectSetup() {
        
        this.obj = new GameObject();
    }

    @Test
    public void testAddGetCard() {
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for (int i=0; i < 10; i++) {
            expected.add(i);
            this.obj.addCard(i);
        }
        assertEquals(expected, this.obj.getCards());
    }
}
