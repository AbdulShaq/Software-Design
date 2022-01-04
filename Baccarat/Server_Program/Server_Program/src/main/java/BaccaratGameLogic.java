import java.util.ArrayList;

public class BaccaratGameLogic
{
    public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2)
    {
        int PlayerTotal = handTotal(hand1);
        int BankerTotal = handTotal(hand2);
        int diffp = 9 -PlayerTotal;
        int diffb = 9 - BankerTotal;

        if(diffp < diffb)
        {
          return "Player";
        }
        else if(diffp > diffb)
        {
            return "Banker";
        }
        else
        {
            return "Draw";
        }

    }

    public static int handTotal(ArrayList<Card> hand)
    {
        int Total = 0;
        int size = hand.size();

        for(int i = 0; i< size; i++)
        {
            if(hand.get(i).getCardValue()<10)
            {
            Total += hand.get(i).getCardValue();
            }
        }

        if(Total < 10)
        {
            return Total;
        }
        else
        {
            return Total%10;
        }
    }

    public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)
    {
        int BankerTotal =handTotal(hand);
        int Val =playerCard.getCardValue();

        if(BankerTotal > 6)
        {
            return false;
        }
        else if(BankerTotal < 6 && playerCard == null)
        {
            return true;
        }

        if(BankerTotal <= 2)
        {
            return true;
        }
        else if(BankerTotal == 3 && Val != 8)
        {
            return true;
        }
        else if(BankerTotal == 4 && Val< 8 && Val > 1)
        {
            return true;
        }
        else if(BankerTotal == 5 && Val< 8 && Val > 3)
        {
            return true;
        }
        else if(BankerTotal == 6 && Val < 8 && Val > 5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean evaluatePlayerDraw(ArrayList<Card> hand)
    {
        int playerTotal = handTotal(hand);
        if(playerTotal < 6)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
