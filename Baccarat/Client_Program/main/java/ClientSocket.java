import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientSocket  extends Thread
{

    Socket socketClient;
    private String IP;
    private int Port;
    private Consumer<Serializable> callback;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocket(Consumer<Serializable> call, String ip, int port)
    {
        callback = call;
        IP = ip;
        Port = port;
           run();
    }


    public void run()
    {
        try
        {
            socketClient = new Socket(IP,Port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);

        }
        catch (Exception e)
        {
             System.out.println("Could not connect to IP:"+ IP+", Port:"+ Port);
             return;
        }


    }

    public void Send(double bid,String hand) throws IOException
    {
       BaccaratInfo INFO = new BaccaratInfo(bid, hand);
       out.writeObject(INFO);
    }

    public BaccaratInfo Recieve() throws IOException, ClassNotFoundException
    {
        BaccaratInfo INFO2 =(BaccaratInfo) in.readObject();
        return INFO2;
    }

    public int getPort()
    {
        return Port;
    }

    public Socket getSocketClient()
    {
        return socketClient;
    }

    public String getIP()
    {
        return IP;
    }

}
