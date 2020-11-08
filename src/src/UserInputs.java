package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks the user to input number of players and the file name.
 */
public class UserInputs implements UserInputsInterface{
    private Scanner sc = new Scanner(System.in);

    
    /** 
     * Gets the number of players from the user.
     * Checks the number is not too big and the number is positive.
     * 
     * @return int - Number of players.
     */
    @Override
    public int getNumPlayers(boolean testing) {
        int num = -1;
        boolean validNumber = false;
        while (!validNumber) {
            System.out.println("How many players");
            try {
                num = sc.nextInt();
                validNumber = num > 0 && num < 2147483647/8;
            } catch(NoSuchElementException e) {
                sc.next();
            }
            if (!validNumber) System.out.println("Please input a valid player number");
            if (testing) break;
        }
        return num;
    }

    
    /** 
     * Gets the file name from the user and parses it to a list of cards.
     * Spaces are not allowed in the file name.
     * Also checks if the file name is not just a blank line and that the file is the
     * right length.
     * 
     * @return String - File name.
     */
    @Override
    public ArrayList<Card> getPack(int numPlayers, boolean testing){
        Boolean validFile = false;
        ArrayList<Card> cardList = new ArrayList<Card>();
        while (!validFile) {
            try {
                System.out.println("Give file name");
                String fileName = sc.next();
                cardList = readFile(fileName);
                validFile = cardList.size() == numPlayers * 8;
            } catch (FileNotFoundException | NumberFormatException | NoSuchElementException e) {}
            if (!validFile) System.out.println("Please input a valid file");
            if (testing) break;
        }
        return cardList;
    }

    /**
     * Function for parsing a pack file. Also checks that a file exists and
     * that file only contains numbers.
     * 
     * @param fileName - the name of the .txt file to read
     * @return ArrayList<Card> - List of Card objects representing a pack. Not necessarily valid.
     * 
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
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

    /**
     * Since the user determines when the scanner needs to be closed,
     * closing the scanner is done publicly.
     */
    @Override
    public void closeScanner() {
        this.sc.close();
    }

}
