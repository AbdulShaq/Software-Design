public class Card
{
    String CardSuite;
    int CardValue;

    Card(String suite, int val)
    {
        CardSuite = suite;
        CardValue = val;
    }

    public String getCardSuite()
    {

        return CardSuite;
    }

    public void setCardSuite(String cardSuite)
    {

        CardSuite = cardSuite;
    }

    public int getCardValue()
    {

        return CardValue;
    }

    public void setCardValue(int cardValue)
    {

        CardValue = cardValue;
    }

    public String toString()
    {

        return CardSuite + " has a value of " + CardValue ;
    }

    public boolean IsEqual(Card c1)
    {
        if(c1.getCardSuite() == CardSuite && c1.getCardValue() == CardValue)
        {
            return true;
        }
        return false;
    }

}
