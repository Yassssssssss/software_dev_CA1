package src;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

class CardGame {
    private int numPlayers;
    private ArrayList<GameObject> gameRing;
    private ArrayList<Integer> cardList;
    private Scanner sc;

    public CardGame(){
        this.sc = new Scanner(System.in);
        this.numPlayers = inputNumPlayers();
        this.gameRing = generateRing();
        this.cardList = readFile(inputCardsFile());
        this.sc.close();
    }

    public int getNumPlayers(){
        return this.numPlayers;
    }

    private Integer inputNumPlayers() {
        System.out.println("How many players");
        Integer numPlayers = sc.nextInt();
        
        return numPlayers;
    }

    private String inputCardsFile() {
        System.out.println("Give file name");

        String fileName = sc.next();
        
        return fileName;
    }

    private ArrayList<GameObject> generateRing(){
        ArrayList<GameObject> ring = new ArrayList<GameObject>();
        for(int i=0; i< this.numPlayers; i++){
            Player player = new Player();
            ring.add(player);
            Deck deck = new Deck();
            ring.add(deck);
        }
        return ring;
    }

    private ArrayList<Integer> readFile(String fileName){
        ArrayList<Integer> returnPack = new ArrayList<Integer>();
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                returnPack.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return returnPack;
    } 

    private ArrayList<Integer> generatePack(){
        ArrayList<Integer> packList = new ArrayList<Integer>();
        Random r = new Random();
        for (int i=0; i < this.numPlayers*8; i++){
            int x = r.nextInt(this.numPlayers) + 1;
            packList.add(x);
        }
        return packList;
    }

    public static void main (String[] args) {
        CardGame cardGame = new CardGame();
        
    }
}