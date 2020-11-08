package src;


public class MockUserWithException implements UserInputsInterface{
    @Override
    public int getNumPlayers() {
        throw new NumberFormatException();
    }

    @Override
    public String getFileName() {
        return "";
    }

    @Override
    public void closeScanner() {}
}
