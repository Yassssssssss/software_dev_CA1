package src;

public class MockUserInputs implements UserInputsInterface{

    @Override
    public Integer getNumPlayers() {
        return 3;
    }

    @Override
    public String getFileName() {
        return "pdeck.txt";
    }
    
}
