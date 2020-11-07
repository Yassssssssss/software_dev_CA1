package src;

import java.util.Scanner;

/**
 * Asks the user to input number of players and the file name.
 */
public class UserInputs implements UserInputsInterface{
    private Scanner sc = new Scanner(System.in);

    @Override
    public Integer getNumPlayers() throws NumberFormatException {
        System.out.println("How many players");
        int num = sc.nextInt();
        if (num <= 0) throw new NumberFormatException("Player number has to be positive");
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
