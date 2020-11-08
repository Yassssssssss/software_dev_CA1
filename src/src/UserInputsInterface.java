package src;

import java.util.ArrayList;

public interface UserInputsInterface {
    public int getNumPlayers(boolean testing);
    public ArrayList<Card> getPack(int numPlayers, boolean testing);
    public void closeScanner();
}
