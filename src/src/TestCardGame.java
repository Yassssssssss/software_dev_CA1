package src;

import org.junit.Before;

public class TestCardGame {
    private mockUserInputs mockInputs;
    private CardGame cardGame;
    
    @Before 
    public void setupTests(){
        this.mockInputs = new mockUserInputs();
        this.cardGame = new CardGame(mockInputs);
    }

    
}
