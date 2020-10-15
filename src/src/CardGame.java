package src;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


class CardGame {
    private int numPlayers;

    public CardGame(){
        this.numPlayers = inputNumPlayers();
    }
    public int getNumPlayers(){
        return this.numPlayers;
    }
    private Integer inputNumPlayers() {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players");
        Integer numPlayers = sc.nextInt();
        sc.close();
        return numPlayers;
    }
    private ArrayList<Integer> generatePack(){
        ArrayList<Integer> packList = new ArrayList<Integer>();
        Random r = new Random();
        for (int i= 0; i < this.numPlayers*8; i++){
            int x = r.nextInt(this.numPlayers)+1 ;
            packList.add(x);
        }
        return packList;
    }
    public static void main (String[] args) {
        CardGame cardGame = new CardGame();
        
        System.out.println(cardGame.generatePack());
        
    }
    
}