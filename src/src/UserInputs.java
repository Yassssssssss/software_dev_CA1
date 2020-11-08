package src;

import java.util.InputMismatchException;
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
     * @throws NumberFormatException - Thrown if number is too big or not positive.
     * @throws InputMismatchException - Thrown if user inputs anything other than a number.
     */
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

    
    /** 
     * Gets the file name from the user. Spaces are not allowed in the file name.
     * Also checks if the file name is not just a blank line.
     * 
     * @return String - File name.
     * @throws NoSuchElementException - Thrown if a blank line is input.
     */
    @Override
    public String getFileName() throws NoSuchElementException{
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
