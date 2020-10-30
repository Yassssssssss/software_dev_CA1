package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

class CardGame {
    private int numPlayers;
    private ArrayList<GameObject> gameRing;
    private ArrayList<Card> cardList;
    private Boolean finished = false;

    public CardGame(UserInputsInterface inputs) {
        this.numPlayers = inputs.getNumPlayers();
        this.gameRing = generateRing();
        this.cardList = readFile(inputs.getFileName());
    }

    private ArrayList<GameObject> generateRing() {
        ArrayList<GameObject> ring = new ArrayList<GameObject>();
        for (int i = 0; i < this.numPlayers; i++) {
            ring.add(new Deck());
            ring.add(new Player(i));
        }
        return ring;
    }

    private ArrayList<Card> readFile(String fileName) {
        ArrayList<Card> returnPack = new ArrayList<Card>();
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                returnPack.add(new Card(Integer.parseInt(sc.nextLine())));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return returnPack;
    }

    // private ArrayList<Integer> generatePack(){
    // ArrayList<Integer> packList = new ArrayList<Integer>();
    // Random r = new Random();
    // for (int i=0; i < this.numPlayers*8; i++){
    // int x = r.nextInt(this.numPlayers) + 1;
    // packList.add(x);
    // }
    // return packList;
    // }

    private void dealCards() {
        for (int i = 0; i < cardList.size(); i++) {
            if (i < numPlayers * 4) {
                ((Player) gameRing.get((i % numPlayers) * 2 + 1)).addCard(cardList.get(i));
            } else {
                ((Deck) gameRing.get((i % numPlayers) * 2)).addCard(cardList.get(i));
            }
        }
    }

    private synchronized void makeSingleMove(Player player) {
        System.out.println(Thread.currentThread().getName());
        int index = gameRing.indexOf(player);
        Deck leftDeck = (Deck) gameRing.get(index - 1);
        Deck rightDeck = (Deck) gameRing.get((index + 1) % gameRing.size());
    }

    private void startGame() {
        for (int i=1; i<gameRing.size(); i+=2) {
            Player p = (Player) gameRing.get(i);
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (!finished) {
                        makeSingleMove(p);
                    }
                }
            });
            t.start();
        }
    }

    public static void main (String[] args) {
        CardGame cardGame = new CardGame(new UserInputs());
        cardGame.dealCards();
        cardGame.startGame();
    }
}