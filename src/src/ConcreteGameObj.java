package src;

import java.io.IOException;

public class ConcreteGameObj extends GameObject {

    public ConcreteGameObj () {
        this.fileName = "test.txt";
    }

    @Override
    public void writeEnd(int winner) throws IOException {
        // This function is tested in the subclasses so
        // no need to test
    }
    
}
