package src;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Asks the user to input number of players and the file name.
 */
public class UserInputs implements UserInputsInterface{
    private Scanner sc = new Scanner(System.in);

    @Override
    public int getNumPlayers() throws NumberFormatException, InputMismatchException{
        int num = -1;
        System.out.println("How many players");
        try {
            num = sc.nextInt();
        } catch(Exception e) {
            sc.next();
            throw(e);
        }
        
        if (num <= 0) throw new NumberFormatException("Player number has to be positive");
        if (num > 2147483647/8) throw new NumberFormatException("Too many players");
        return num;
    }

    @Override
    public String getFileName() {
        System.out.println("Give file name");
        String returnstr = sc.next();
        return returnstr;
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
