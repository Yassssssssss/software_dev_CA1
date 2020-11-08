package src;


public class MockUserWithException implements UserInputsInterface{
    public Integer getNumPlayers() {
        throw new NumberFormatException();
    }

    public String getFileName() {
        return "";
    }

    @Override
    public void closeScanner() {}
}
