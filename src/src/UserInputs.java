package src;

import java.util.Scanner;

public class UserInputs {
    private int numPlayers;
    private String fileName;
    private Scanner sc;

    public UserInputs() {
        this.numPlayers = inputNumPlayers();
        this.fileName = inputCardsFile();
    }

    private Integer inputNumPlayers() {
        System.out.println("How many players");
        return sc.nextInt();
    }

    private String inputCardsFile() {
        System.out.println("Give file name");
        return sc.next();
    }

    public Integer getNumPlayers() {
        return this.numPlayers;
    }

    public String getFileName() {
        return this.fileName;
    }



}
