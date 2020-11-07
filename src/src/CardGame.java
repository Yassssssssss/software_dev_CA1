package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements methods for the card game. Stores all the players, decks necessary.
 * Additionally, threading is dealt with in this class.
 * 
 * Attributes:
 * gameRing - A list of alternating Deck objects and Player objects, starting with a Deck
 * cardList - The pack. A list of Card objects.
 * winner - The player number for the player that has won. Set to -1 while no one has won.
 */
class CardGame {
    private int numPlayers;
    private ArrayList<GameObject> gameRing;
    private ArrayList<Card> cardList;

    // -1 can be used to represent nobody winning since player number will never be negative.
    private int winner = -1;
    
    /**
     * Instatiates the card game. Gets the player number from user, validates it,
     * generates the game ring and gets the valid pack file.
     */
    public CardGame(UserInputsInterface inputs) {
        boolean validNumber = false;
        while (!validNumber) {
            try {
                this.numPlayers = inputs.getNumPlayers();
                validNumber = true;
            } catch(NumberFormatException e) {
                System.out.println("Please input a valid player number");
            }
        }
        this.gameRing = generateRing();
        this.cardList = getPackFromFile(inputs);
        inputs.closeScanner();
    }


    /**
     * Generates the ring of decks and players according to the number of players.
     * The list contains alternating Deck and Player objects. The list is treated as a 
     * circular list in the rest of the program.
     * 
     * @return ArrayList<GameObject> - List of Deck and Player objects.
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
     * Asks user for the pack filename and parses it into a list of Card objects.
     * Validation is also done.
     * 
     * @param inputs - An object that implements UserInputsInterface.
     * @return ArrayList<Card> - A list of Card objects representing pack.
     */
    private ArrayList<Card> getPackFromFile(UserInputsInterface inputs) {
        Boolean validFile = false;
        String fileName;
        while (!validFile) {
            fileName = inputs.getFileName();
            try {
                cardList = readFile(fileName); // Throws FileNotFoundException and NumberFormatException
                validatePackLength(cardList); // Throws InvalidLengthException
                validFile = true;
            } catch (FileNotFoundException | NumberFormatException | InvalidLengthException e) {
                System.out.println("\nPlease input a valid file");
            }
        }
        return cardList;
    }


    /**
     * Function for parsing a pack file. Also checks that a file exists and
     * that file only contains numbers.
     * 
     * @param fileName - the name of the .txt file to read
     * @return ArrayList<Card> - List of Card objects representing a pack. Not necessarily valid.
     * 
     * @throws FileNotFoundException
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
     * Checks the length of a pack.
     * Throws InvalidLengthException when the length of pack is not 8*numPlayers
     * 
     * @param pack - A list representing a pack.
     * @throws InvalidLengthException
     */
    private void validatePackLength(ArrayList<Card> pack) throws InvalidLengthException{
        if (pack.size() != numPlayers * 8) {
            throw new InvalidLengthException(String.format("Pack length should be %d but is %d.", numPlayers, pack.size()));
        }
    }


    /**
     * Deals 4 cards to Player classes and rest to Deck classes
     * Also writes all players' initial hand to their respective files.
     * Time complexity O(n).
     * 
     * @throws IOException - Thrown by writeDeckToFile()
     */
    private void dealCards() throws IOException {
        for (int i = 0; i < cardList.size(); i++) {
            if (i < numPlayers * 4) {
                // Distribute to players
                ((Player) gameRing.get((i % numPlayers) * 2 + 1)).addCard(cardList.get(i));
            } else {
                // Distribute to decks
                ((Deck) gameRing.get((i % numPlayers) * 2)).addCard(cardList.get(i));
            }
        }

        // Write initial hand
        for (int i = 1; i < gameRing.size(); i += 2) {
            Player p = (Player) gameRing.get(i);
            p.writeDeckToFile();
        }
    }


    /**
     * Method for making a single move for each player in gameRing
     * Synchronized to avoid a deadlock that can occur due to the access of 
     * shared Deck objects which is volatile.
     * 
     * @param player - The player who is making the move
     * @throws IOException - Thrown by endGame()
     */
    private synchronized void makeSingleMove(Player player) throws IOException {
        if (winner != -1) // Check no one has has after this method got scheduled.
            return;
        int index = gameRing.indexOf(player);
        Deck leftDeck = (Deck) gameRing.get(index - 1);
        Deck rightDeck = (Deck) gameRing.get((index + 1) % gameRing.size());
        if (leftDeck.getCards().size() > 0) {
            this.winner = player.makeMove(leftDeck, rightDeck, false); // Returns player's preference if player has won
            if (this.winner != -1) endGame(); // Call the endGame() method with the thread running this method.
        }
    }


    /**
     * Starts the game by making and starting the threads for each player.
     */
    private void startGame() {
        for (int i = 1; i < gameRing.size(); i += 2) {
            Player p = (Player) gameRing.get(i);
            // Create new thread with an anonymous class
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
            t.start();
        }
    }


    /**
     * Tell all decks and players that the game has finished,
     * making them write to their repective files their final lines.
     */
    private void endGame() {
        for (GameObject obj: gameRing) {
            try {
                obj.writeEnd(winner);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A static method to generate a valid pack file for a given number of players.
     * Used for testing purposes.
     * 
     * @param numPlayers - Number of players
     * @param fileName - File to write to
     */
    public static void generatePackFile(int numPlayers, String fileName){
        Random r = new Random();
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i=0; i < numPlayers*8; i++){
                int x = r.nextInt(numPlayers) + 1;
                printWriter.printf(Integer.toString(x) + "\n");
            }
            printWriter.close();
            fileWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void main (String[] args) throws IOException{
        // CardGame cardGame = new CardGame(new UserInputs());
        // cardGame.dealCards();
        // cardGame.startGame();

        for (int i=0; i < 10; i++) {
            CardGame.generatePackFile(i, String.format("pdeck_{}.txt", i));
        }
        CardGame.generatePackFile(2147483647, String.format("pdeck_2147483647.txt"));
    }
}