package src;

import org.junit.Before;
import org.junit.Test;

public class TestCardGame {
    private MockUserInputs mockInputs;
    
    @Before
    public void setup(){
        this.mockInputs = new MockUserInputs();
        
    }
    
    @Test
    public void testCardGame(){
        new CardGame(mockInputs);
    }
}
