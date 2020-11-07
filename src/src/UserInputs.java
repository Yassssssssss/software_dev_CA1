package src;

import java.util.Scanner;

public class UserInputs implements UserInputsInterface{
    private int numPlayers;
    private String fileName;
    private Scanner sc = new Scanner(System.in);
    

    public UserInputs() {
        this.numPlayers = inputNumPlayers();
        this.fileName = inputCardsFile();
    }

    private Integer inputNumPlayers() throws NumberFormatException {
        System.out.println("How many players");
        return sc.nextInt();
    }

    public String inputCardsFile() {
        System.out.println("Give file name");
        String returnstr = sc.next();
        return returnstr;
    }

    @Override
    public Integer getNumPlayers() {
        return this.numPlayers;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public void closeScanner() {
        this.sc.close();
    }
}
