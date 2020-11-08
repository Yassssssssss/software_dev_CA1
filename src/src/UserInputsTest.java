package src;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

import org.junit.jupiter.api.Test;

public class UserInputsTest {
    
    @Test
    public void testGetNumPlayers() {
        System.setIn(new ByteArrayInputStream("2048".getBytes()));
        assertEquals(2048, new UserInputs().getNumPlayers());
        System.setIn(new ByteArrayInputStream("poop".getBytes()));
        assertThrows(InputMismatchException.class, () -> new UserInputs().getNumPlayers());
        System.setIn(new ByteArrayInputStream("0".getBytes()));
        assertThrows(NumberFormatException.class, () -> new UserInputs().getNumPlayers());
        System.setIn(new ByteArrayInputStream("-9876".getBytes()));
        assertThrows(NumberFormatException.class, () -> new UserInputs().getNumPlayers());
        System.setIn(new ByteArrayInputStream("2147483647".getBytes()));
        assertThrows(NumberFormatException.class, () -> new UserInputs().getNumPlayers());
        
    }

    @Test
    public void testGetFileName() {
        System.setIn(new ByteArrayInputStream("Biden won.txt".getBytes()));
        assertEquals("Biden won.txt", new UserInputs().getFileName());
        System.setIn(new ByteArrayInputStream(" ".getBytes()));
        assertEquals(" ", new UserInputs().getFileName());
        System.setIn(new ByteArrayInputStream("9834".getBytes()));
        assertEquals("9834", new UserInputs().getFileName());
    }
}