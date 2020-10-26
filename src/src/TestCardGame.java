package src;

import org.junit.Before;
import org.junit.Test;

public class TestCardGame {
    private mockUserInputs mockInputs;
    
    @Before
    public void setupTests(){
        this.mockInputs = new mockUserInputs();
        
    }
    
    
    @Test
    public void testCardGame(){
        new CardGame(mockInputs);
    }


    
}
