import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest
{
	BaccaratGame BG;
	BaccaratInfo BINFO;
	Card C1,C2,C3,C4,C5;
	BaccaratDealer BD;
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	ArrayList<Card> Deck,Deck2;

	@BeforeEach
	void Set()
	{
		BG = new BaccaratGame("Player",120.50);
		BINFO = new BaccaratInfo(20.50,"Banker");
		C1 = new Card("CLUBS",6);
		C2 = new Card("SPADES",3);
		C3 = new Card("DIAMONDS",7);
		C4 = new Card("HEARTS",12);
		BD = new BaccaratDealer();
		playerHand = new ArrayList<>();
		bankerHand = new ArrayList<>();
		Deck = new ArrayList<>();
		Deck2 = new ArrayList<>();
	}

	//BACCARAT GAME TESTS
	@Test
	void BaccaratGameConstructorTest()
	{
		assertEquals(120.50,BG.getCurrentBet(),"Wrong Amount");
		assertEquals("Player",BG.getHand(),"Wrong Hand");
	}

	@Test
	void evaluateWinningsTest1()
	{
		double Amt = BG.evaluateWinnings();
		if(Amt%1==0)
		{
			assertEquals(true,(BG.getCurrentBet()%1== 0), "Correct Winnings");
		}
		else
		{
			assertEquals(true,(BG.getCurrentBet()%1!= 0), "Correct Winnings");
		}
	}

	@Test
	void evaluateWinningsTest2()
	{
        BG.setCurrentBet(-45.32);
		double Amt = BG.evaluateWinnings();
		if(Amt%1==0)
		{
			assertEquals(true,(BG.getCurrentBet()%1== 0), "Correct Winnings");
		}
		else
		{
			assertEquals(true,(BG.getCurrentBet()%1!= 0), "Correct Winnings");
		}
	}

	//BACCARAT GAME LOGIC TESTS
	@Test
	void whoWonTest1()
	{
		playerHand.add(C1);
		playerHand.add(C2);
		bankerHand.add(C3);
		bankerHand.add(C4);
		String Won = BaccaratGameLogic.whoWon(playerHand,bankerHand);
		assertEquals("Player",Won,"Wrong Winner");
	}

	@Test
	void whoWonTest2()
	{
        bankerHand.add(C1);
		bankerHand.add(C2);
		playerHand.add(C3);
		playerHand.add(C4);
		String Won = BaccaratGameLogic.whoWon(playerHand,bankerHand);
		assertEquals("Banker",Won,"Wrong Winner");
	}

	@Test
	void handTotalTest1()
	{
		bankerHand.add(C1);
		bankerHand.add(C2);
         int Total = BaccaratGameLogic.handTotal(bankerHand);
		assertEquals(9,Total,"Wrong Total");
	}

	@Test
	void handTotalTest2()
	{
		playerHand.add(C3);
		playerHand.add(C4);
		int Total = BaccaratGameLogic.handTotal(playerHand);
		assertEquals(7,Total,"Wrong Total");
	}

	@Test
	void evaluateBankerDrawTest1()
	{
		C1.setCardValue(3);
		C2.setCardValue(1);
		C4.setCardValue(3);
		bankerHand.add(C1);
		bankerHand.add(C2);

		assertEquals(true,BaccaratGameLogic.evaluateBankerDraw(bankerHand,C4),"Wrong");
	}

	@Test
	void evaluateBankerDrawTest2()
	{
		C1.setCardValue(3);
		C2.setCardValue(10);
		C4.setCardValue(8);
		bankerHand.add(C1);
		bankerHand.add(C2);

		assertEquals(false,BaccaratGameLogic.evaluateBankerDraw(bankerHand,C4),"Wrong");
	}

	@Test
	void evaluatePlayerDrawTest1()
	{
		C1.setCardValue(3);
		C2.setCardValue(6);
		playerHand.add(C1);
		playerHand.add(C2);

		assertEquals(false,BaccaratGameLogic.evaluatePlayerDraw(playerHand),"Wrong");
	}

	@Test
	void evaluatePlayerDrawTest2()
	{
		C1.setCardValue(3);
		C2.setCardValue(1);
		playerHand.add(C1);
		playerHand.add(C2);

		assertEquals(true,BaccaratGameLogic.evaluatePlayerDraw(playerHand),"Wrong");
	}


	//BACCARAT DEALER TESTS
	@Test
	void BaccaratDealerConstructorTest()
	{
		assertEquals(20.50,BINFO.getBid(),"Wrong Amount");
		assertEquals("Banker",BINFO.getHand(),"Wrong Hand");
	}

	@Test
	void generateDeckTest1()
	{
		 BD.generateDeck();
		 int size = BD.deckSize();
		 assertEquals(52, size,"Wrong size");
	}

	@Test
	void generateDeckTest2()
	{
		BD.generateDeck();
		int size = BD.deckSize();
		int TypeCount =0;
		for(int i =0; i <52; i++)
		{

			if(BD.Deck.get(i).getCardSuite()== "SPADES")
			{
				TypeCount++;
			}
			else if(BD.Deck.get(i).getCardSuite()== "CLUBS")
			{
				TypeCount++;
			}
			else if(BD.Deck.get(i).getCardSuite()== "DIAMONDS")
			{
				TypeCount++;
			}
			else if(BD.Deck.get(i).getCardSuite()== "HEARTS")
			{
				TypeCount++;
			}

			if(TypeCount==4)
			{
				break;
			}
		}

		assertEquals(4, TypeCount,"Wrong size");
	}

	@Test
	void dealHandTest1()
	{
		BD.generateDeck();
		playerHand = BD.dealHand();
		assertEquals(true,(playerHand.size()>0),"Wrong Hand");
	}

  @Test
	void dealHandTest2()
	{
		BD.generateDeck();
		bankerHand = BD.dealHand();
		assertEquals(false,(bankerHand.size()==0),"Wrong Hand");
	}

	@Test
	void drawOneTest1()
	{
        BD.generateDeck();
		playerHand = BD.dealHand();
		C5 = BD.drawOne();
		playerHand.add(C5);
		assertEquals(true,C5.IsEqual(playerHand.get(2)),"Wrong Card");

	}

	@Test
	void drawOneTest2()
	{
		BD.generateDeck();
		bankerHand = BD.dealHand();
		C5 = BD.drawOne();
		bankerHand.add(C5);
		assertEquals(C5.getCardSuite(),bankerHand.get(2).getCardSuite(),"Wrong Card");
	}

	@Test
	void shuffleDeckTest1()
	{
	    BD.generateDeck();
		int size = BD.deckSize();
		BD.shuffleDeck();
		int size2 = BD.deckSize();

		assertEquals(size, size2,"Wrong size");

	}

	@Test
	void shuffleDeckTest2()
	{
		BD.generateDeck();
		int size1 = BD.deckSize();
		BD.shuffleDeck();
		int size2 = BD.deckSize();
		int size = size1 + size2;
		assertEquals(104, size,"Wrong size");


	}

	@Test
	void deckSizeTest1()
	{
		BD.generateDeck();
		int size = BD.deckSize();
		assertEquals(52, size,"Wrong size");
	}

	@Test
	void deckSizeTest2()
	{
		BD.generateDeck();
		int size1 = BD.deckSize();

		BD.generateDeck();
		int size2 = BD.deckSize();

		int size = size1+size2;
		assertEquals(104, size,"Wrong size");

	}

	//BACCARAT INFO TESTS
	@Test
	void BaccaratInfoConstructorTest()
	{
		assertEquals(20.50,BINFO.getBid(),"Wrong Amount");
		assertEquals("Banker",BINFO.getHand(),"Wrong Hand");
	}

	//BACCARAT CARD TESTS
	@Test
	void BaccaratCardConstructorTest()
	{
		assertEquals(6,C1.getCardValue(),"Wrong Amount");
		assertEquals("CLUBS",C1.getCardSuite(),"Wrong Hand");
	}



}
