package src;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestCardGame.class, TestPlayer.class,
               TestDeck.class, TestCard.class})
public class TestSuite {
    
}
