
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application{

	
	TextField IPEntry, PortEntry, BetEntry;
	Label IPTitle, PortTitle, MainTitle, PlayerTotal, BankerTotal, dollar$, CurrWin, WinAmt, WinTitle;
	Button ConnectBtn, PlayBtn, BetPlayerBtn, BetBankerBtn,BetDrawBtn, QuitBtn;
	ListView<ImageView> PlayerCards, BankerCards;
    ObservableList<ImageView> PcardList, BcardList;
	Integer intPort;
	String Choice= " ";
	ClientSocket ClientConnect;
	ImageView LogoView;
	Image Logo;
	double bid, WinTotal=(0*100.00)/100.00;

	private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Baccarat");

	   PlayerCards = new ListView<>();
	   BankerCards = new ListView<>();
	   PcardList = FXCollections.observableArrayList();
	   BcardList = FXCollections.observableArrayList();

       IPTitle = new Label("Enter IP:");
	   PortTitle = new Label("Enter Port:");
	   MainTitle = new Label("Welcome to Baccarat!");
	   dollar$ = new Label("$");
	   PlayerTotal = new Label("Player Total: ");
	   BankerTotal = new Label("Banker Total: ");
       CurrWin = new Label("");
	   WinAmt = new Label("Current Winning:\n$"+ String.format("%.2f",WinTotal));
	   WinTitle = new Label("");

	   IPEntry = new TextField();
	   PortEntry = new TextField();
	   BetEntry = new TextField();

	   PlayBtn = new Button("Play!");
	   ConnectBtn = new Button("Connect to Server");
	   BetBankerBtn = new Button("Bet On\nBanker");
	   BetPlayerBtn = new Button("Bet On\nPlayer");
	   BetDrawBtn = new Button("Bet On\nDraw");
	   QuitBtn = new Button("Quit");

	   Logo = new Image("Baccaratlogo.jpg");
	   LogoView = new ImageView(Logo);

	   LogoView.setFitHeight(200);
	   LogoView.setFitWidth(550);
	   BetDrawBtn.setMinSize(90,25);
	   BetPlayerBtn.setMinSize(90,25);
	   BetBankerBtn.setMinSize(90,25);
	   BetEntry.setMaxWidth(55);
	   PlayBtn.setMinSize(160,50);
	   ConnectBtn.setMinSize(160,50);
	   BankerCards.setMinSize(400,500);
	   PlayerCards.setMinSize(400,500);
	   QuitBtn.setMinSize(160,50);
	   WinAmt.setMinWidth(130);
	   BetEntry.setMinSize(120,35);

	   PlayerTotal.setStyle("-fx-font-size: 25;-fx-font-weight: bold; -fx-text-fill: white");
	   BankerTotal.setStyle("-fx-font-size: 25;-fx-font-weight: bold; -fx-text-fill: white");
	   BetDrawBtn.setStyle("-fx-font-size: 15;-fx-font-weight: bold");
	   BetBankerBtn.setStyle("-fx-font-size: 15;-fx-font-weight: bold");
	   BetPlayerBtn.setStyle("-fx-font-size: 15;-fx-font-weight: bold");
	   dollar$.setStyle("-fx-font-size: 25;-fx-font-weight: bold; -fx-text-fill: white");
	   WinAmt.setStyle("-fx-font-size: 22;-fx-font-weight: bold; -fx-text-fill: white");
	   PlayBtn.setStyle("-fx-font-size: 25;-fx-font-weight: bold");
	   QuitBtn.setStyle("-fx-font-size: 25;-fx-font-weight: bold");
	   ConnectBtn.setStyle("-fx-font-size: 25;-fx-font-weight: bold");
	   MainTitle.setStyle("-fx-font-size: 50; -fx-font-weight: bold");
	   WinTitle.setStyle("-fx-font-size: 50; -fx-font-weight: bold; -fx-text-fill: white");
	   IPTitle.setStyle("-fx-font-size: 25; -fx-font-weight: bold; -fx-text-fill: white");
	   PortTitle.setStyle("-fx-font-size: 25; -fx-font-weight: bold; -fx-text-fill: white");
	   CurrWin.setStyle("-fx-font-size: 45;-fx-font-weight: 650; -fx-text-fill: white");
	   IPEntry.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
	   PortEntry.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
	   BetEntry.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

	   IPEntry.setPromptText("i.e. 127.0.0.1");
	   PortEntry.setPromptText("i.e. 5555");
	   BetEntry.setPromptText("123.45");

		ConnectBtn.setOnAction(e->{

			try {
				intPort = Integer.parseInt(PortEntry.getText());

				} catch (NumberFormatException f) {
				PortEntry.clear();
				return;
				}

				if(!validate(IPEntry.getText()))
				{
					IPEntry.clear();
					return;
				}
              ClientConnect = new ClientSocket(data -> {
				Platform.runLater(() -> {
					System.out.println(data);
				});
			},IPEntry.getText(),intPort);

			primaryStage.setScene(GameScene());

		});

		BetPlayerBtn.setOnAction(e->{
              BetPlayerBtn.setDisable(true);
			  BetDrawBtn.setDisable(false);
			  BetBankerBtn.setDisable(false);
			  Choice = "Player";
		 });

		BetDrawBtn.setOnAction(e->{
			BetPlayerBtn.setDisable(false);
			BetDrawBtn.setDisable(true);
			BetBankerBtn.setDisable(false);
			Choice = "Draw";
		});

		BetBankerBtn.setOnAction(e->{
			BetPlayerBtn.setDisable(false);
			BetDrawBtn.setDisable(false);
			BetBankerBtn.setDisable(true);
			Choice = "Banker";
		});

		PlayBtn.setOnAction(e->{

			try {
				bid = Double.parseDouble(BetEntry.getText());
			} catch (Exception f) {
				BetEntry.getText();
				return;
			}

			if(Objects.equals(Choice, " "))
			{
				return;
			}

			PlayerCards.getItems().removeAll(PcardList);
			BankerCards.getItems().removeAll(BcardList);
			PcardList.removeAll();
			BcardList.removeAll();

			try {

				ClientConnect.Send(bid, Choice);
				BaccaratInfo results = ClientConnect.Recieve();



				Image C1, C2,C3;
				C1 = new Image(results.getPlayerHand().get(0));
				C2 = new Image(results.getPlayerHand().get(1));
				ImageView P1= new ImageView(C1),P2= new ImageView(C2);
				P1.setFitHeight(200);
				P1.setFitWidth(150);
				P2.setFitHeight(200);
				P2.setFitWidth(150);
				PcardList.add(P1);
				PcardList.add(P2);
				if(results.getPlayerHand().size()== 3)
				{
					C3 = new Image(results.getPlayerHand().get(2));
					ImageView P3= new ImageView(C3);
					P3.setFitHeight(200);
					P3.setFitWidth(150);
					PcardList.add(P3);
				}
				PlayerCards.setItems(PcardList);

				Image C4, C5,C6;
				C4 = new Image(results.getBankerHand().get(0));
				C5 = new Image(results.getBankerHand().get(1));
				ImageView B1= new ImageView(C4),B2= new ImageView(C5);
				B1.setFitHeight(200);
				B1.setFitWidth(150);
				B2.setFitHeight(200);
				B2.setFitWidth(150);
				BcardList.add(B1);
				BcardList.add(B2);
				if(results.getBankerHand().size()== 3)
				{
					C6 = new Image(results.getBankerHand().get(2));
					ImageView B3= new ImageView(C6);
					B3.setFitHeight(200);
					B3.setFitWidth(150);
					BcardList.add(B3);
				}
				BankerCards.setItems(BcardList);

				WinTotal = WinTotal + results.winnings;
				WinAmt.setText("Current Winning:\n$"+ String.format("%.2f",WinTotal));
				PlayerTotal.setText("Player Total: "+results.getPlayerTot());
				BankerTotal.setText("Banker Total: "+results.getBankerTot());
				WinTitle.setText(results.Winner+" Wins!");


				  if(Objects.equals(Choice, results.Winner))
				  {
					  CurrWin.setText("Congrats, you bet "+results.Winner+"! You win!");
				  }
				  else
				  {
					  CurrWin.setText("Sorry, you bet "+Choice+"! You lose!");
				  }





			} catch (Exception f) {

				return;

			}


		});

		QuitBtn.setOnAction(e->{
			Platform.exit();
			System.exit(0);
		});

		primaryStage.setScene(WelcomeScene());
		primaryStage.show();
		
	}

	Scene WelcomeScene()
	{
		VBox IPBox = new VBox(15,IPTitle,IPEntry);
		VBox PortBox = new VBox(15,PortTitle,PortEntry);
		HBox Entries = new HBox(40,IPBox,PortBox);
        HBox LOGO = new HBox(LogoView);
		VBox Main = new VBox(80,LogoView,Entries,ConnectBtn);
		BorderPane startPane = new BorderPane();
		startPane.setPadding(new Insets(20,20,20,20));
		Main.setAlignment(Pos.CENTER);
		LOGO.setAlignment(Pos.CENTER);
		Entries.setAlignment(Pos.CENTER);
		ConnectBtn.setAlignment(Pos.BOTTOM_CENTER);
		//startPane.setTop(LOGO);
		startPane.setCenter(Main);
        startPane.setStyle("-fx-background-color: #9f0606;");

		return new Scene(startPane, 1200,900);
	}

	Scene GameScene()
	{
		BorderPane gamePane = new BorderPane();
		gamePane.setPadding(new Insets(70,40,55,40));

		HBox PlayerBanker = new HBox(25, BetPlayerBtn,BetBankerBtn);
		HBox BetBox = new HBox(2,dollar$,BetEntry);
		HBox BottomBox = new HBox(450,WinAmt, PlayBtn, QuitBtn);

		VBox centerStack = new VBox(15,BetBox,BetDrawBtn);
		VBox BetOptions = new VBox(25,centerStack,PlayerBanker);
		VBox PlayerSide = new VBox(10,PlayerTotal,PlayerCards);
		VBox BankerSide = new VBox(10,BankerTotal,BankerCards);
		VBox RoundResults = new  VBox(6,WinTitle,CurrWin);

		BetBox.setAlignment(Pos.CENTER);
		centerStack.setAlignment(Pos.CENTER);
		PlayerBanker.setAlignment(Pos.CENTER);
		BetOptions.setAlignment(Pos.CENTER);
        PlayerSide.setAlignment(Pos.CENTER_LEFT);
		BankerSide.setAlignment(Pos.CENTER_LEFT);
		RoundResults.setAlignment(Pos.TOP_CENTER);
		gamePane.setTop(RoundResults);
		gamePane.setCenter(BetOptions);
		gamePane.setLeft(PlayerSide);
		gamePane.setRight(BankerSide);
		gamePane.setBottom(BottomBox);

		gamePane.setStyle("-fx-background-image: url(GameBG.jpg);-fx-background-size: cover;-fx-background-repeat: no-repeat");
		return new Scene(gamePane, 1500,925);
	}

	public static boolean validate(final String ip)
	{
		return PATTERN.matcher(ip).matches();
	}

	//FUNCTION FOR UPDATING RESULTS HERE

}
