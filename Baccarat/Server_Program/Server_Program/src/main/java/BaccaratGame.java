import java.util.ArrayList;

public class BaccaratGame
{
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer theDealer = new BaccaratDealer();
    String Hand;
    double currentBet;
    double totalWinnings =0 ;

    BaccaratGame(String hand, double bet)
    {
        Hand = hand;
        currentBet =bet;
        theDealer.generateDeck();
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();

    }

    public double evaluateWinnings()
    {
        String Won = BaccaratGameLogic.whoWon(playerHand,bankerHand);


        if(Won.equals(Hand) && !Won.equals("Draw"))
        {

            totalWinnings += currentBet;
            return totalWinnings;
        }
        else if(Won.equals(Hand) && Won.equals("Draw"))
        {

            totalWinnings += (currentBet*8) + currentBet;
            return totalWinnings;
        }
        else
        {
            totalWinnings += -1 * currentBet;
            return totalWinnings;
        }

    }

    public ArrayList<String> cardString(ArrayList<Card> cards)
    {
        ArrayList<String> cardStrings = new ArrayList<>();
        int size =cards.size();
        String S;

        for (int i=0;i<size;i++ )
        {
            S = cards.get(i).getCardSuite() + "-" + cards.get(i).getCardValue()+".jpg";
            cardStrings.add(S);
        }

        return cardStrings;
    }

    public double getCurrentBet()
    {
        return currentBet;
    }

    public double getTotalWinnings()
    {
        return totalWinnings;
    }

    public String getHand()
    {
        return Hand;
    }

    public ArrayList<Card> getBankerHand()
    {
        return bankerHand;
    }

    public ArrayList<Card> getPlayerHand()
    {
        return playerHand;
    }

    public BaccaratDealer getTheDealer()
    {
        return theDealer;
    }

    public void setCurrentBet(double CurrentBet) {
        currentBet = CurrentBet;
    }
}
