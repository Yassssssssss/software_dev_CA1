package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({GameObjectTest.class, PlayerTest.class,
               DeckTest.class, UserInputsTest.class,
               CardTest.class, CardGameTest.class})
public class TestSuite {
    /**
     * Static method to read what's written in a file. Used only in testing classes.
     */
    public static String readFromFile(String fileName) {
        String data = "";
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) data += reader.nextLine();
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
