package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.Card;
import src.UserInputs;

public class UserInputsTest {
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
    
    @Test
    public void testGetNumPlayers() {
        System.setIn(new ByteArrayInputStream("2048".getBytes()));
        assertEquals(2048, new UserInputs().getNumPlayers(true));

        StringWriter expectedStringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(expectedStringWriter);

        printWriter.println("How many players");
        printWriter.print("Please input a valid player number");
        printWriter.close();

        String expected = expectedStringWriter.toString();

        outContent.reset();
        System.setIn(new ByteArrayInputStream("poop".getBytes()));
        new UserInputs().getNumPlayers(true);
        assertEquals(expected, outContent.toString().trim());
        outContent.reset();
        System.setIn(new ByteArrayInputStream("0".getBytes()));
        new UserInputs().getNumPlayers(true);
        assertEquals(expected, outContent.toString().trim());
        outContent.reset();
        System.setIn(new ByteArrayInputStream("-9876".getBytes()));
        new UserInputs().getNumPlayers(true);
        assertEquals(expected, outContent.toString().trim());
        outContent.reset();
        System.setIn(new ByteArrayInputStream("2147483647".getBytes()));
        new UserInputs().getNumPlayers(true);
        assertEquals(expected, outContent.toString().trim()); 
    }

    @Test
    public void testGetPack() {
        ArrayList<Card> expectedPack = new ArrayList<Card>();
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 4; j++) {
                expectedPack.add(new Card(j));
            }
        }

        System.setIn(new ByteArrayInputStream("4players_preferences.txt".getBytes()));
        ArrayList<Card> actualPack = new UserInputs().getPack(4, true);
        for (int i = 0; i < expectedPack.size(); i++) {
            assertTrue(expectedPack.get(i).isSame(actualPack.get(i)));
        }

        StringWriter expectedStringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(expectedStringWriter);

        printWriter.println("Give file name");
        printWriter.print("Please input a valid file");
        printWriter.close();

        String expected = expectedStringWriter.toString();
        // Invalid inputs
        outContent.reset();
        System.setIn(new ByteArrayInputStream("3players.txt".getBytes()));
        new UserInputs().getPack(4, true);
        assertEquals(expected, outContent.toString().trim());
        outContent.reset();
        System.setIn(new ByteArrayInputStream("Obama's-last-name.txt".getBytes()));
        new UserInputs().getPack(4, true);
        assertEquals(expected, outContent.toString().trim());
        outContent.reset();
        System.setIn(new ByteArrayInputStream("4players_invalid.txt".getBytes()));
        new UserInputs().getPack(4, true);
        assertEquals(expected, outContent.toString().trim());
    }

    @Test
    public void testReadFile() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        Method readFile = UserInputs.class.getDeclaredMethod("readFile", new Class[] { String.class });
        readFile.setAccessible(true);

        ArrayList<Card> expectedPack = new ArrayList<Card>();
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 4; j++) {
                expectedPack.add(new Card(j));
            }
        }
        // Valid inputs
        @SuppressWarnings("unchecked")
        ArrayList<Card> actualPack = (ArrayList<Card>) readFile.invoke(new UserInputs(), "4players_preferences.txt");
        for (int i = 0; i < expectedPack.size(); i++) {
            assertTrue(expectedPack.get(i).isSame(actualPack.get(i)));
        }

        // Testing for exceptions
        Exception exception = assertThrows(InvocationTargetException.class,
                () -> readFile.invoke(new UserInputs(), "file-not-found.txt"));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());

        exception = assertThrows(InvocationTargetException.class,
                () -> readFile.invoke(new UserInputs(), "4players_invalid.txt"));
        assertEquals(NumberFormatException.class, exception.getCause().getClass());
    }
}