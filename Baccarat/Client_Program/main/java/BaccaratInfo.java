import java.io.Serializable;
import java.util.ArrayList;

public class BaccaratInfo implements Serializable
{
    private static final long serialVersionID = 1L;

    double Bid, winnings = 0;
    String Hand, Winner= " ";
    int PlayerTot, BankerTot;

    ArrayList<String> PlayerHand = new ArrayList<>();
    ArrayList<String> BankerHand = new ArrayList<>();

    BaccaratInfo(double bid, String hand)
    {
        Bid = bid;
        Hand = hand;
    }

    public double getBid() {
        return Bid;
    }

    public String getHand() {
        return Hand;
    }

    public ArrayList<String> getBankerHand() {
        return BankerHand;
    }

    public ArrayList<String> getPlayerHand() {
        return PlayerHand;
    }

    public void setBankerHand(ArrayList<String> bankerHand) {
        BankerHand = bankerHand;
    }

    public void setPlayerHand(ArrayList<String> playerHand) {
        PlayerHand = playerHand;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public void setWinnings(double winnings) {
        this.winnings = winnings;
    }

    public void setBankerTot(int bankerTot) {
        BankerTot = bankerTot;
    }

    public void setPlayerTot(int playerTot) {
        PlayerTot = playerTot;
    }

    public int getBankerTot() {
        return BankerTot;
    }

    public int getPlayerTot() {
        return PlayerTot;
    }
}
