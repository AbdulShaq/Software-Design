import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class Server
{
    int count = 1;
    int Port;
    private Consumer<Serializable> callback;
    ArrayList<ClientThread> Clients = new ArrayList<ClientThread>();
    TheServer server = new TheServer();

    Server(Consumer<Serializable> call, int port)
    {
        callback = call;
        Port = port;
        server.start();
    }

    public int getCount()
    {
        return count;
    }

    public class TheServer extends Thread
    {
        public void run() {
            try (ServerSocket mysocket = new ServerSocket(Port);)
            {
                callback.accept("Server is waiting for a client! on Port:" + Port);

                while(true)
                {
                    ClientThread c = new ClientThread(mysocket.accept(),count);
                    callback.accept("client has connected to server: client #" + count);
                    count++;
                    callback.accept(" ");
                    Clients.add(c);
                    c.start();


                }
            }
            catch (Exception e)
            {
                callback.accept("Server socket did not launch");
            }
        }


        ///END OF INNER CLASS
    }

    public class ClientThread extends Thread
    {
        int Num;
        Socket Connection;
        ObjectOutputStream out;
        ObjectInputStream in;
        BaccaratGame BG;
        BaccaratDealer Dealer;
        BaccaratInfo INFO, INFO2;
        ArrayList<Card> BankerHand, PlayerHand;

        ClientThread(Socket connect, int ID)
        {
            Connection = connect;
            Num = ID;
        }


        public void run()
        {
            try
            {
                out = new ObjectOutputStream(Connection.getOutputStream());
                in = new ObjectInputStream(Connection.getInputStream());
                Connection.setTcpNoDelay(true);

            }catch (Exception e)
            {
                callback.accept("Client #"+ Num);

            }

            while (Connection.isClosed()==false)
            {
                try {
                    INFO = (BaccaratInfo) in.readObject();

                    callback.accept("Client #"+Num+ " Playing new hand: ");
                    callback.accept("Bid: $"+ INFO.Bid+ "; Hand: "+ INFO.Hand+"\n");
                    BG = new BaccaratGame(INFO.getHand(), INFO.getBid());
                    Dealer = BG.getTheDealer();
                    INFO2 = new BaccaratInfo(INFO.getBid(),INFO.getHand());
                    PlayerHand = BG.getPlayerHand();
                    BankerHand = BG.getBankerHand();

                    if(BaccaratGameLogic.evaluatePlayerDraw(PlayerHand)==true)
                    {
                        Card NewCard = Dealer.drawOne();
                        PlayerHand.add(NewCard);
                        if(BaccaratGameLogic.evaluateBankerDraw(PlayerHand, NewCard)==true)
                        {
                            Card BankersCard = Dealer.drawOne();
                            BankerHand.add(BankersCard);
                        }
                    }
                    INFO2.setPlayerHand(BG.cardString(BG.getPlayerHand()));
                    INFO2.setBankerHand(BG.cardString(BG.getBankerHand()));
                    INFO2.setWinner(BaccaratGameLogic.whoWon(BG.getPlayerHand(), BG.getBankerHand()));
                    INFO2.setWinnings(BG.evaluateWinnings());
                    INFO2.setPlayerTot(BaccaratGameLogic.handTotal(BG.getPlayerHand()));
                    INFO2.setBankerTot(BaccaratGameLogic.handTotal(BG.getBankerHand()));

                    out.writeObject(INFO2);
                    callback.accept("Results for Client #"+Num+":");
                    callback.accept("Winnings: $"+INFO2.winnings+"; Winning Hand: "+INFO2.Winner);
                    callback.accept("Banker Total: "+INFO2.getBankerTot()+"; Player Total: "+INFO2.getPlayerTot());
                    if(Objects.equals(INFO2.Winner,INFO.Hand))
                    {
                        callback.accept("Client #"+Num+" Won");
                    }
                    else{
                        callback.accept("Client #"+Num+" Lost");
                    }
                    callback.accept("  ");
                }
                catch (Exception e)
                {
                    callback.accept("Could not get request from client #" + Num);
                   try
                   {
                       Connection.close();
                   }
                   catch (Exception f)
                   {
                       callback.accept("Client #"+Num+"'s Connection could not be closed");
                   }
                }
            }
            callback.accept("Client #"+Num+"'s Connection closed");
            callback.accept(" ");
        }





        //END OF INNER CLASS
    }

  ///END OF OUTTER CLASS
}
