package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MockUserInputs implements UserInputsInterface {
    private int numPlayers;
    private String fileName;

    public MockUserInputs(int numPlayers, String fileName) {
        this.numPlayers = numPlayers;
        this.fileName = fileName;
    }

    @Override
    public int getNumPlayers(boolean testing) {
        return numPlayers;
    }

    @Override
    public ArrayList<Card> getPack(int numPlayers, boolean testing) {
        try {
            return readFile(fileName);
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Card>();
    }

    private ArrayList<Card> readFile(String fileName) throws NumberFormatException, FileNotFoundException {
        ArrayList<Card> returnPack = new ArrayList<Card>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            returnPack.add(new Card(Integer.parseInt(sc.nextLine())));
        }
        sc.close();
        return returnPack;
    }

    @Override
    public void closeScanner() {}
    
}