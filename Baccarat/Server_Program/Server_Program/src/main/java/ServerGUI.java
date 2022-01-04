
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerGUI extends Application{

	
	TextField PortEntry;
	Label PortTitle, MainTitle, ClientCount;
	int ClientCounter = 0;
	Integer intPort;
	Button ServerOnBtn, ServerOffBtn;
	ListView<String> listInfo;
	Server ServerConnect;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Baccarat Server");

		listInfo = new ListView<>();
		ServerOnBtn = new Button("Start Server");
		ServerOffBtn = new Button("Close Server");
		PortTitle = new Label("Enter Port Number:");
		MainTitle = new Label("Welcome to the Baccarat\n                Server");
		ClientCount = new Label("Client Count: "+ClientCounter);
		PortEntry = new TextField();
		PortEntry.setPromptText("i.e. 5555");

		ServerOnBtn.setStyle("-fx-font-size: 20;-fx-font-weight: bold");
		ServerOffBtn.setStyle("-fx-font-size: 20;-fx-font-weight: bold");
		MainTitle.setStyle("-fx-font-size: 60; -fx-font-weight: bold");
        ClientCount.setStyle("-fx-font-size: 22;-fx-font-weight: bold");
		PortEntry.setStyle("-fx-font-size: 17;-fx-font-weight: bold");
		PortTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
		listInfo.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

		ServerOnBtn.setMinSize(120,40);
		ServerOffBtn.setMinSize(160,50);
		PortEntry.setMaxSize(150,35);
		listInfo.setMinSize(900,700);


		ServerOnBtn.setOnAction(e->{
			try {
				intPort = Integer.parseInt(PortEntry.getText());

			} catch (NumberFormatException f) {
				PortEntry.clear();
				return;
			}

			ServerConnect = new Server(data->{Platform.runLater(new Runnable() {
				@Override
				public void run() {
					UpdateCount(ServerConnect.getCount());
				}
			});
				listInfo.getItems().add(data.toString());
			},intPort);

			primaryStage.setScene(InfoScene());
		});


		ServerOffBtn.setOnAction(e->{
			Platform.exit();
			System.exit(0);
		});


		primaryStage.setScene(MainScene());
		primaryStage.show();





	}
	
Scene MainScene()
{
	VBox PortBox = new VBox(10,PortTitle,PortEntry);
	VBox MainBox = new VBox(45,MainTitle,PortBox,ServerOnBtn);

	BorderPane MainPane = new BorderPane();
    MainPane.setPadding(new Insets(40,40,40,40));

	PortBox.setAlignment(Pos.CENTER);
	MainBox.setAlignment(Pos.CENTER);
	MainTitle.setAlignment(Pos.CENTER);
	PortTitle.setAlignment(Pos.BOTTOM_CENTER);
	PortEntry.setAlignment(Pos.BOTTOM_CENTER);
	ServerOnBtn.setAlignment(Pos.BOTTOM_CENTER);

	MainPane.setCenter(MainBox);

	return new Scene(MainPane, 1000,850);
}

Scene InfoScene()
{
	BorderPane InfoPane = new BorderPane();
	InfoPane.setPadding(new Insets(30,40,40,40));
	VBox InfoBox = new VBox(20,ClientCount,listInfo);
    HBox CloseBtnBox = new HBox(ServerOffBtn);
	InfoPane.setCenter(InfoBox);
	InfoPane.setBottom(CloseBtnBox);
	ClientCount.setAlignment(Pos.TOP_LEFT);
	CloseBtnBox.setAlignment(Pos.BOTTOM_CENTER);
	return new Scene(InfoPane, 1000,900);
}
public void UpdateCount(int i)
{
	ClientCounter = i-1;
	ClientCount.setText("Client Count: "+ClientCounter);
}

}
