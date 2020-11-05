package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class TestDeck {
    private Deck deck;

    @Before
    public void setup() {
        deck = new Deck(1);
        for (int i=0; i<4; i++) deck.addCard(new Card(i));
    }

    @Test
    public void testPopTop() {
        assertTrue(new Card(3).isSame(deck.popTop()));
        Deck expected = new Deck(1);
        for (int i=0; i<3; i++) expected.addCard(new Card(i));
    }

    @Test
    public void addToBottom() {
        deck.addToBottom(new Card(4));
        Deck expected = new Deck(1);
        for (int i=0; i<4; i++) expected.addCard(new Card(i));
        expected.addToBottom(new Card(4));
        assertTrue(expected.isSame(deck));
    }
    @Test
    public void testGetNumber(){
        assertEquals(1,deck.getNumber());
    }

    @Test
    public void testWriteEnd() {
        String data = "";
        try {
            deck.writeEnd(1);
            File file = new File("Deck1_output.txt");
            Scanner reader = new Scanner(file);
            data = reader.nextLine();
            reader.close();
        } catch (IOException e){

        }
        assertEquals("Deck 1 contents: 0 1 2 3", data);
    }
}
