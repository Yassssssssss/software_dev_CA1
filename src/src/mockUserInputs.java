package src;

public class mockUserInputs implements UserInputs{
    @Override
    public Integer getNumPlayers() {
        return 3;
    }

    @Override
    public String getFileName() {
        // TODO Auto-generated method stub
        return "pdeck.txt";
    }
    
}
