package src;

public class MockUserInputs implements UserInputsInterface{
    private int numPlayers;
    private String fileName;

    public MockUserInputs(int numPlayers, String fileName) {
        this.numPlayers = numPlayers;
        this.fileName = fileName;
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void closeScanner() {}
    
}