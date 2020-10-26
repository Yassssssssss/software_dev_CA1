package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class CardGame {
    private int numPlayers;
    public  ArrayList<GameObject> gameRing;
    private ArrayList<Integer> cardList;
    

    public CardGame(UserInputs inputs){
        this.numPlayers = inputs.getNumPlayers();
        this.gameRing = generateRing();
        this.cardList = readFile(inputs.getFileName());
    }

    private ArrayList<GameObject> generateRing(){
        ArrayList<GameObject> ring = new ArrayList<GameObject>();
        for(int i=0; i< this.numPlayers; i++){
            ring.add(new Player());
            ring.add(new Deck());
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

    public void dealCards(){
        for (int i=0; i<cardList.size(); i++){
            if (i < numPlayers*4){
                ((Player) gameRing.get((i% numPlayers)*2)).addCard(cardList.get(i));
            } else {
                ((Deck) gameRing.get((i% numPlayers)*2+1)).addCard(cardList.get(i));
            }
        }  
    }


    public static void main (String[] args) {
        CardGame cardGame = new CardGame(new ActualUserInputs());
        cardGame.dealCards();
        System.out.println(cardGame.gameRing.toString());
        
    }
}