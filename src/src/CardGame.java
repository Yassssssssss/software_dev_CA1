package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements all the attributes and methods shared by Deck and Player
 * 
 * Attributes:
 * gamering - the ArrayList of GameObject
 * cardList - the ArrayList of Cards
 * winner - the variable which decides the winner
 * threads - the ArrayList of threads
 */
class CardGame {
    private int numPlayers;
    private ArrayList<GameObject> gameRing;
    private ArrayList<Card> cardList;
    private int winner = -1;
    private ArrayList<Thread> threads = new ArrayList<Thread>();

    
    /**
     * 
     * TODO
     * 
     * 
     * 
     * 
     * 
     */
    public CardGame(UserInputsInterface inputs) {
        this.numPlayers = inputs.getNumPlayers();
        this.gameRing = generateRing();
        this.cardList = getPackFromFile(inputs);
        inputs.closeScanner();
    }


    /**
     * Generates the ring of players and decks according to the number of players 
     * 
     * @return ArrayList<GameObject> - A list which contains an ArrayList of all Player and Deck
     * classes in play
     */
    private ArrayList<GameObject> generateRing() {
        ArrayList<GameObject> ring = new ArrayList<GameObject>();
        for (int i = 1; i < this.numPlayers + 1; i++) {
            ring.add(new Deck(i));
            ring.add(new Player(i));
        }
        return ring;
    }


    /**
     * TODO
     * 
     * @param inputs
     * @return ArrayList<Card> - A list of Cards
     */
    private ArrayList<Card> getPackFromFile(UserInputsInterface inputs) {
        Boolean validFile = false;
        String fileName;
        while (!validFile) {
            fileName = inputs.getFileName();
            try {
                cardList = readFile(fileName);
                validatePackLength(cardList);
                validFile = true;
            } catch (FileNotFoundException | NumberFormatException | InvalidLengthException e) {
                System.out.println("\nPlease input a valid file\n");
            }
        }
        return cardList;
    }


    /**
     * Reads the file fileName and adds a Card with that number
     * 
     * @param fileName - the name of the .txt file full of numbers to read
     * @return ArrayList<Card> - 
     * @throws FileNotFoundException - Thrown by File
     * @throws NumberFormatException
     */
    private ArrayList<Card> readFile(String fileName) throws FileNotFoundException, NumberFormatException{
        ArrayList<Card> returnPack = new ArrayList<Card>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            returnPack.add(new Card(Integer.parseInt(sc.nextLine())));
        }
        sc.close();
        return returnPack;
    }


    /**
     * TODO
     * 
     * @param pack - 
     * @throws InvalidLengthException
     */
    private void validatePackLength(ArrayList<Card> pack) throws InvalidLengthException{
        if (pack.size() != numPlayers * 8) {
            throw new InvalidLengthException(String.format("Pack length should be %d but is %d.", numPlayers, pack.size()));
        }
    }


    /**
     * Deals 4 cards to Player classes and rest to Deck classes
     * 
     * @throws IOException - Thrown by writeDeckToFile()
     */
    private void dealCards() throws IOException {
        for (int i = 0; i < cardList.size(); i++) {
            if (i < numPlayers * 4) {
                ((Player) gameRing.get((i % numPlayers) * 2 + 1)).addCard(cardList.get(i));
            } else {
                ((Deck) gameRing.get((i % numPlayers) * 2)).addCard(cardList.get(i));
            }
        }
        for (int i = 1; i < gameRing.size(); i += 2) {
            Player p = (Player) gameRing.get(i);
            p.writeDeckToFile();
        }
    }


    /**
     * Method for making a single move for each player in gameRing
     * 
     * @param player - the player who is making the move
     * @throws IOException - Thrown by endGame()
     */
    private synchronized void makeSingleMove(Player player) throws IOException {
        if (winner != -1)
            return;
        int index = gameRing.indexOf(player);
        Deck leftDeck = (Deck) gameRing.get(index - 1);
        Deck rightDeck = (Deck) gameRing.get((index + 1) % gameRing.size());
        if (leftDeck.getCards().size() > 0) {
            this.winner = player.makeMove(leftDeck, rightDeck, false);
            if (this.winner != -1) endGame();
        }
    }


    /**
     * Starts the game by making and starting the threads
     */
    private void startGame() {
        for (int i = 1; i < gameRing.size(); i += 2) {
            Player p = (Player) gameRing.get(i);
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (winner == -1) {
                        try {
                            makeSingleMove(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            threads.add(t);
            t.start();
        }
    }


    /**
     * Tests all GameObjects if == winner
     * 
     * @throws IOException - Thrown by writeEnd()
     */
    private void endGame() throws IOException {
        for (GameObject obj: gameRing) {
            obj.writeEnd(winner);
        }
    }

    /**
     * TODO
     * @param numPlayers
     * @return ArrayList<Integer> - 
     */
    public static ArrayList<Integer> generatePack(int numPlayers){
        ArrayList<Integer> packList = new ArrayList<Integer>();
        Random r = new Random();
        for (int i=0; i < numPlayers*8; i++){
            int x = r.nextInt(numPlayers) + 1;
            packList.add(x);
        }
        return packList;
    }

    
    public static void main (String[] args) throws IOException{
        CardGame cardGame = new CardGame(new UserInputs());
        cardGame.dealCards();
        cardGame.startGame();
    }
}