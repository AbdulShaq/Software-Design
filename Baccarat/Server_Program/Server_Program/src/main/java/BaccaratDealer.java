import java.util.ArrayList;
import java.util.Random;

public class BaccaratDealer
{
    ArrayList<Card> Deck = new ArrayList<>();
    String[] Suites;

    BaccaratDealer()
    {
        Suites = new String[] {"CLUBS","DIAMONDS","HEARTS","SPADES"};
    }

    public void generateDeck()
    {
        Deck.removeAll(Deck);
        int suiteSize = 4;
        int NumSuites = 13;
        boolean isHere;
        int val;
        String suite;
        Card c1 = null;
        Random Rand = new Random();

        for(int i =0; i <52; i++)
        {
          isHere = true;

          while (isHere == true)
          {
               suite = Suites[Rand.nextInt(suiteSize)];
               val = Rand.nextInt(NumSuites)+1;
               c1 = new Card(suite,val);
               isHere = Deck.contains(c1);
          }
           Deck.add(c1);
         c1 = null;
        }

    }

    public ArrayList<Card> dealHand()
    {
        ArrayList<Card> Cards = new ArrayList<>();
        Card C1 = drawOne();
        Card C2 = drawOne();
        Cards.add(C1);
        Cards.add(C2);

        return Cards;
    }

    public Card drawOne()
    {
        Random rand = new Random();
        int size = Deck.size();
        int num = rand.nextInt(size);
        Card C1 = Deck.remove(num);

        return C1;
    }

    public void shuffleDeck()
    {
        this.generateDeck();
    }

    public int deckSize()
    {
        return Deck.size();
    }

    public String[] getSuites()
    {
        return Suites;
    }

}
