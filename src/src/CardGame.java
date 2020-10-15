package src;

import java.util.Scanner;

class CardGame {
    private Integer inputNumPlayers() {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players");
        Integer numPlayers = sc.nextInt();
        sc.close();
        return numPlayers;
    }

    public static void main (String[] args) {
        CardGame cardGame = new CardGame();
        int numPlayers = cardGame.inputNumPlayers();
        
    }
}