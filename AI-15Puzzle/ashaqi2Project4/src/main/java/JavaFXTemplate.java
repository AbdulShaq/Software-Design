import javafx.animation.PauseTransition;
import javafx.application.Application;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.*;

import javafx.scene.layout.GridPane;


public class JavaFXTemplate extends Application {
	StackPane MainStack;
	GridPane Board;
	Button NewPuz;
	Button Quit;
	Button Sol;
	Button H1;
	Button H2;
	Button SolveAll;
	Button NewGame;
	Button ExitGame;
	HBox TopOpts;
	HBox AIOpts;
	HBox SolOpts;
	HBox WinOpts;
	VBox BotOpts;
	VBox MainBox;
	VBox WelcomeBox;
	VBox WinBox;
	VBox Overlay;
	Label Welcome;
	Label Winner;
	String heuristic;
	int[]  Puzzle1= {1,5,2,3,0,4,6,7,8,9,10,11,12,13,14,15},Puzzle2={4,1,0,3,8,5,2,7,9,10,6,11,12,13,14,15},Puzzle3 = {8,4,1,2,0,5,6,3,12,9,11,7,13,14,10,15},Puzzle4={1,2,6,3,4,9,7,0,8,13,5,11,12,14,10,15},Puzzle5={5,4,2,3,1,13,9,7,8,14,6,11,12,0,10,15}, Puzzle6={1,5,2,3,4,9,6,0,14,13,10,7,8,12,15,11},Puzzle7={1,5,3,7,4,9,6,2,8,10,0,11,12,13,14,15},Puzzle8 ={1,5,2,3,4,6,10,7,8,9,14,0,12,13,15,11},Puzzle9 ={2,4,3,7,9,5,10,0,1,13,11,6,8,12,14,15},Puzzle10={2,4,6,3,1,0,14,10,8,5,9,7,12,13,15,11};
	ArrayList<int[]> PUZZLES = new ArrayList<>();
	int[] goalState = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	int[] Puzzle;
	double TimePause = 2000;
	BorderPane GamePane;
	EventHandler<ActionEvent> PuzTiles, ShutDown;
	TranslateTransition tt;
	PauseTransition pause1;
	ExecutorService ex = Executors.newFixedThreadPool(10);
	Future<ArrayList<Integer>> future;
	ArrayList<Integer> Order;
	Group TILES;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Abdul Shaqildi's 15 Puzzle");

        //DECLARE MAIN STACK AND SET UP PUZZLE
		 MainStack = new StackPane();
		 TILES = new Group();
             PUZZLES.add(Puzzle1);
		     PUZZLES.add(Puzzle2);
			 PUZZLES.add(Puzzle3);
			 PUZZLES.add(Puzzle4);
		     PUZZLES.add(Puzzle5);
		     PUZZLES.add(Puzzle6);
			 PUZZLES.add(Puzzle7);
		     PUZZLES.add(Puzzle8);
		     PUZZLES.add(Puzzle9);
		     PUZZLES.add(Puzzle10);
        SetStyles();

		//TRANSITION FROM WELCOME SCREEN TO GAME
		PauseTransition pause = new PauseTransition(Duration.millis(2750));
		 pause1 = new PauseTransition(Duration.millis(700));
		pause.setOnFinished(e-> {MainStack.getChildren().remove(WelcomeBox);MainStack.getChildren().remove(Overlay);});
        /////////////////////////////////////////////

		//EVENT HANDLERS
         NewPuz.setOnAction(e->{Board.getChildren().clear();BuildPuzzle();Sol.setDisable(true);SolveAll.setDisable(true);H1.setDisable(false);H2.setDisable(false);});

		 ShutDown = new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent actionEvent) {
				 ex.shutdown();
				 Platform.exit();
				 System.exit(0);
			 }
		 };
         Quit.setOnAction(ShutDown);

		 ExitGame.setOnAction(ShutDown);

		 NewGame.setOnAction(e->{
			 try {
				 start(primaryStage);
			 } catch (Exception ex) {
				 ex.printStackTrace();
			 }
		 });

		 H1.setOnAction(e->{
			 heuristic ="heuristicOne";
			 future = ex.submit(new MyCall(GetBoardArray(),heuristic));

			 try {
				ex.submit(()->{Order = future.get(); return null;});
				 Sol.setDisable(false);
				 SolveAll.setDisable(false);
			 } catch (Exception ex) {System.out.println(ex.getMessage());}

		 });

		H2.setOnAction(e->{
			heuristic ="heuristicTwo";
			future = ex.submit(new MyCall(GetBoardArray(),heuristic));
			try {
				ex.submit(()->{Order = future.get(); return null;});
				Sol.setDisable(false);
				SolveAll.setDisable(false);
			} catch (Exception ex) {System.out.println(ex.getMessage());}

		});

		SolveAll.setOnAction(e->{
			Sol.setDisable(true);
			SolveAll.setDisable(true);
			H1.setDisable(true);
			H2.setDisable(true);
			PauseTransition pauser = new PauseTransition(Duration.millis((Order.size()*TimePause)));
			pauser.setOnFinished(re->{Sol.setDisable(false); SolveAll.setDisable(false);H1.setDisable(false);H2.setDisable(false);});
			pauser.play();
			AISolve(Order);
		});

		Sol.setOnAction(e->{
			Sol.setDisable(true);
			SolveAll.setDisable(true);
			H1.setDisable(true);
			H2.setDisable(true);
				ArrayList<Integer> Sollst = SubLst();
			PauseTransition pauser = new PauseTransition(Duration.millis((Sollst.size()*TimePause)));
			pauser.setOnFinished(re->{Sol.setDisable(false); SolveAll.setDisable(false);H1.setDisable(false);H2.setDisable(false);});
			pauser.play();
				AISolve(Sollst);


		});

		 PuzTiles = new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event)
			 {
                Button Btn = (Button)event.getSource();
				 StackPane T = (StackPane)Btn.getParent();
				 StackPane TOP, LEFT, BTM, RIGHT;
				int R = GridPane.getRowIndex(T);
				int C = GridPane.getColumnIndex(T);
				int INDEX = Board.getChildren().indexOf(T);
				int Top = Math.min(R,R-1);
				int Btm = Math.max(R,R+1);
				int Left = Math.min(C,C-1);
				int Right = Math.max(C,C+1);



				if(Top < 0 && Left< 0) //TOP LEFT CORNER
				{//ONLY LOOK AT BTM AND RIGHT
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);
					Button BOTBTN = (Button) BTM.getChildren().get(0);
					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{
						DownTrans(Btn,BTM);
						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							T.getChildren().add(BOTBTN);
							BTM.getChildren().remove(BOTBTN);
							BTM.getChildren().add(Btn);
							CheckWin();});
					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}
                else if(Top < 0 && Right> 3) //TOP RIGHT CORNER
				{	//ONLY LOOK BTM AND LEFT
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					Button BOTBTN = (Button) BTM.getChildren().get(0);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{
						DownTrans(Btn,BTM);

						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							T.getChildren().add(BOTBTN);
							BTM.getChildren().remove(BOTBTN);
							BTM.getChildren().add(Btn);
							CheckWin();});

					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}

				 }
				else if(Btm > 3 && Right > 3) //BOTTOM RIGHT CORNER
				{
                    //ONLY LOOK UP AND LEFT
					TOP = (StackPane) Board.getChildren().get(INDEX-4);
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);
					Button TOPBTN = (Button) TOP.getChildren().get(0);
					if(Objects.equals(TOPBTN.getText(),"0"))
					{

						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
						T.getChildren().remove(Btn);
						TOP.getChildren().remove(TOPBTN);
						T.getChildren().add(TOPBTN);
						TOP.getChildren().add(Btn);
							CheckWin();
						});

					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}
				}
				else if(Btm > 3 && Left < 0) //BOTTOM LEFT CORNER
				{
                      // ONLY LOOK UP AND RIGHT
					TOP = (StackPane) Board.getChildren().get(INDEX-4);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);
					Button TOPBTN = (Button) TOP.getChildren().get(0);
					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);
					if(Objects.equals(TOPBTN.getText(),"0"))
					{
						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							TOP.getChildren().remove(TOPBTN);
							T.getChildren().add(TOPBTN);
							TOP.getChildren().add(Btn);
							CheckWin();
						});
					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}
				else if(Top < 0) // TOP MIDDLE 2
				{
					//LOOK LEFT RIGHT AND DOWN
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);

					Button BOTBTN = (Button) BTM.getChildren().get(0);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);
					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{

						DownTrans(Btn,BTM);
						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							T.getChildren().add(BOTBTN);
							BTM.getChildren().remove(BOTBTN);
							BTM.getChildren().add(Btn);
							CheckWin();});
					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}
				else if(Btm > 3) // BOTTOM MIDDLE 2
				{//LOOK UP LEFT AND RIGHT
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);
					TOP = (StackPane) Board.getChildren().get(INDEX-4);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);
					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);
					Button TOPBTN = (Button) TOP.getChildren().get(0);

					if(Objects.equals(TOPBTN.getText(),"0"))
					{
						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							TOP.getChildren().remove(TOPBTN);
							T.getChildren().add(TOPBTN);
							TOP.getChildren().add(Btn);
							CheckWin();
						});

					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}


				}
				else if(Left < 0) // LEFT MIDDLE 2
				{
					//LOOK UP DOWN AND RIGHT
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);
					TOP = (StackPane) Board.getChildren().get(INDEX-4);

					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);
					Button TOPBTN = (Button) TOP.getChildren().get(0);
					Button BOTBTN = (Button) BTM.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{
						DownTrans(Btn,BTM);

						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							T.getChildren().add(BOTBTN);
							BTM.getChildren().remove(BOTBTN);
							BTM.getChildren().add(Btn);
							CheckWin();});
					}

					if(Objects.equals(TOPBTN.getText(),"0"))
					{
						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							TOP.getChildren().remove(TOPBTN);
							T.getChildren().add(TOPBTN);
							TOP.getChildren().add(Btn);
							CheckWin();
						});

					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}
				else if(Right > 3) // RIGHT MIDDLE 2
				{
					//LOOK UP DOWN AND LEFT
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					TOP = (StackPane) Board.getChildren().get(INDEX-4);

					Button TOPBTN = (Button) TOP.getChildren().get(0);
					Button BOTBTN = (Button) BTM.getChildren().get(0);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{
						DownTrans(Btn,BTM);

						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
						T.getChildren().remove(Btn);
						T.getChildren().add(BOTBTN);
						BTM.getChildren().remove(BOTBTN);
						BTM.getChildren().add(Btn);
							CheckWin();});

					}

					if(Objects.equals(TOPBTN.getText(),"0"))
					{
						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							TOP.getChildren().remove(TOPBTN);
							T.getChildren().add(TOPBTN);
							TOP.getChildren().add(Btn);
							CheckWin();
						});
					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}
				else // LOOK UP DOWN LEFT AND RIGHT
				{
					BTM = (StackPane) Board.getChildren().get(INDEX+4);
					RIGHT = (StackPane) Board.getChildren().get(INDEX+1);
					LEFT = (StackPane) Board.getChildren().get(INDEX-1);
					TOP = (StackPane) Board.getChildren().get(INDEX-4);

					Button TOPBTN = (Button) TOP.getChildren().get(0);
					Button BOTBTN = (Button) BTM.getChildren().get(0);
					Button LEFTBTN = (Button) LEFT.getChildren().get(0);
					Button RIGHTBTN = (Button) RIGHT.getChildren().get(0);

					if(Objects.equals(BOTBTN.getText(),"0"))
					{
						DownTrans(Btn,BTM);

						pause1.play();
						pause1.setOnFinished(e->{
							BTM.setViewOrder(0);
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							T.getChildren().add(BOTBTN);
							BTM.getChildren().remove(BOTBTN);
							BTM.getChildren().add(Btn);
							CheckWin();});
					}

					if(Objects.equals(TOPBTN.getText(),"0"))
					{
						UpTrans(Btn,TOP);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateY(0);
							T.getChildren().remove(Btn);
							TOP.getChildren().remove(TOPBTN);
							T.getChildren().add(TOPBTN);
							TOP.getChildren().add(Btn);
							CheckWin();
						});

					}

					if(Objects.equals(RIGHTBTN.getText(),"0"))
					{
						RightTrans(Btn,RIGHT);
						pause1.play();
						pause1.setOnFinished(e->{
							RIGHT.setViewOrder(0);
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							RIGHT.getChildren().remove(RIGHTBTN);
							T.getChildren().add(RIGHTBTN);
							RIGHT.getChildren().add(Btn);
							CheckWin();
						});
					}

					if(Objects.equals(LEFTBTN.getText(),"0"))
					{
						LeftTrans(Btn,LEFT);
						pause1.play();
						pause1.setOnFinished(e->{
							Btn.setTranslateX(0);
							T.getChildren().remove(Btn);
							LEFT.getChildren().remove(LEFTBTN);
							T.getChildren().add(LEFTBTN);
							LEFT.getChildren().add(Btn);
							CheckWin();
						});
					}

				}




			 }
		 };
        /////////
		primaryStage.setScene(MainScene());
		primaryStage.show();
		pause.play();

		//Thread t = new Thread(()-> {A_IDS_A_15solver ids = new A_IDS_A_15solver(Puzzle,heuristic);});
		//t.start();

	}

	Scene MainScene()
	{
		 GamePane = new BorderPane();

		GamePane.setStyle("-fx-background-image: url(BackGround.jpg); -fx-background-size: cover");
		BuildPuzzle();
		GamePane.setCenter(MainBox);
		MainStack.getChildren().addAll(GamePane,Overlay,WelcomeBox);
		return  new Scene(MainStack, 900,900);
	}

	void BuildPuzzle()
	{
		//Collections.shuffle(Puzzle);
		Random rand = new Random();
		Puzzle = PUZZLES.get(rand.nextInt(10));
		int num = 0;

		for(int i =0; i < 4; i++)
		{
			String val;
			for(int j =0; j < 4; j++)
			{
				val = Integer.toString(Puzzle[num]);
				Button GB = new Button();
				StackPane Tile = new StackPane();
				Tile.setMaxSize(105,105);

				Tile.setStyle("-fx-background-color:#8298a2 ;  -fx-background-radius: 30; -fx-border-radius: 30  ");

				GB.setText(val);
				GB.setMinSize(105, 105);
                GB.setOnAction(PuzTiles);
				if (Puzzle[num]!= 0)
				{
					GB.setStyle("-fx-font-size: 35;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-color: #ffb347;-fx-background-radius: 30;");
				}
				else
				{
					GB.setStyle("-fx-opacity: 0; ");
					GB.setDisable(true);
				}

				num++;
				Tile.getChildren().add(GB);
				Board.add(Tile, j, i);
			}
		}

	}

	void SetStyles()
	{
		//WELCOME SCREEN WITH OVERLAY
		Welcome = new Label("Welcome to\n 15 Puzzle!");
		Welcome.setStyle("-fx-font-size: 85; -fx-font-weight: bolder; -fx-font-family: 'Kristen ITC' ; -fx-font-style: italic" );
		WelcomeBox = new VBox(Welcome);
		Overlay = new VBox();
		Overlay.setStyle("-fx-background-color: white; -fx-opacity: .51");

		Welcome.setAlignment(Pos.CENTER);
		WelcomeBox.setAlignment(Pos.CENTER);
		Overlay.setAlignment(Pos.CENTER);
		/////////////////////////////

		//GAME SCENE SET UP
		Board = new GridPane();

		SolveAll = new Button("See Full Solution");
		NewGame = new Button("Play Again");
		ExitGame = new Button("Exit");
		NewPuz = new Button("New Puzzle");
		Quit = new Button("Quit");
		Sol = new Button("Next Ten Moves");
		H1 = new Button("AI H1");
		H2 = new Button("AI H2");
		Winner = new Label("You Solved\nThe Puzzle!");
		TopOpts = new HBox(45,NewPuz,Quit);
		AIOpts = new HBox(30,H1,H2);
		WinOpts = new HBox(55, NewGame,ExitGame);
		SolOpts = new HBox(30,Sol,SolveAll);
		BotOpts = new VBox(30, AIOpts,SolOpts);
		MainBox = new VBox(40, TopOpts,Board,BotOpts);
		WinBox = new VBox(45,Winner,WinOpts);
		Board.setMaxSize(520,520);
		Board.setPadding(new Insets(35,25,25,35));
		Board.setHgap(10);
		Board.setVgap(10);
		Sol.setDisable(true);
        SolveAll.setDisable(true);

		Winner.setAlignment(Pos.CENTER);
		WinOpts.setAlignment(Pos.CENTER);
		WinBox.setAlignment(Pos.CENTER);
		MainBox.setAlignment(Pos.CENTER);
		TopOpts.setAlignment(Pos.CENTER);
		AIOpts.setAlignment(Pos.CENTER);
		SolOpts.setAlignment(Pos.CENTER);
		BotOpts.setAlignment(Pos.CENTER);

		SolveAll.setMinSize(100,35);
		NewGame.setMinSize(100,35);
		ExitGame.setMinSize(100,35);
		NewPuz.setMinSize(100,35);
		Quit.setMinSize(150,35);
		Sol.setMinSize(100,35);
		H1.setMinSize(150,35);
		H2.setMinSize(150,35);

		Board.setStyle("-fx-background-color: #AECBD6;-fx-background-radius: 30;");
		Winner.setStyle("-fx-font-size: 85; -fx-font-weight: bolder; -fx-font-family: 'Kristen ITC' ; -fx-font-style: italic" );
		NewGame.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC'; -fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		ExitGame.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC'; -fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		NewPuz.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC'; -fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		Quit.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		Sol.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		SolveAll.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		H1.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-radius: 30;-fx-background-color:#8298a2 ;");
		H2.setStyle("-fx-font-size: 25;-fx-font-weight: bold;-fx-font-family: 'Kristen ITC';-fx-background-radius: 30;-fx-background-color:#8298a2 ;");


	}

	void UpTrans(Button B,StackPane S)
	{
		tt = new TranslateTransition(Duration.millis(700), B);
		tt.setByY(-115);
		tt.play();

	}

	void DownTrans(Button B,StackPane S)
	{
		S.setViewOrder(1);
		tt = new TranslateTransition(Duration.millis(600), B);
		tt.setByY(115);
		tt.play();
	}

	void LeftTrans(Button B,StackPane S)
	{
		tt = new TranslateTransition(Duration.millis(600), B);
		tt.setByX(-115);
		tt.play();
	}

	void RightTrans(Button B,StackPane S)
	{
		S.setViewOrder(1);
		tt = new TranslateTransition(Duration.millis(600), B);
		tt.setByX(115);
		tt.play();
	}

	void CheckWin()
	{
		int[] curr;

		curr =  GetBoardArray();

		if(Arrays.equals(curr, goalState))
		{
			MainStack.getChildren().add(Overlay);
			MainStack.getChildren().add(WinBox);

		}


	}

	int[] GetBoardArray()
	{
		int[] BoardArray = new int[16];

		for(int i = 0; i < 16;i++)
		{
			StackPane Stacktmp = (StackPane) Board.getChildren().get(i);
			Button tmp = (Button) Stacktmp.getChildren().get(0);
			BoardArray[i] = Integer.parseInt(tmp.getText());
		}
		return BoardArray;
	}

	void AISolve(ArrayList<Integer> Sol)
	{
		if(Sol.size() == 0)
		{
			return;
		}

		PauseTransition pause2 = new PauseTransition(Duration.millis(TimePause));
				int INDEX = Sol.get(0);
				//System.out.println("Index: " +INDEX);
				StackPane T = (StackPane) Board.getChildren().get(INDEX);
				Button Btn = (Button) T.getChildren().get(0);
				//System.out.println("Button: " + Btn.getText());
				Btn.fire();
				pause2.play();

				pause2.setOnFinished(e->{Sol.remove(0);AISolve(Sol);});

	}

	ArrayList<Integer> SubLst()
	{
		ArrayList<Integer> Sub = new ArrayList<>();
		System.out.println("BEFORE IF: "+Order.size());
		if(Order.size() > 10)
		{
			System.out.println("BEFORE ADD: "+Order.size());
			for(int i = 0; i <10;i++)
			{Sub.add(Order.get(i));}
			System.out.println("BEFORE REMOVE: "+Order.size());
			for(int k = 0; k <10;k++)
			{ Order.remove(0);}
			System.out.println("AFTER ALL: "+Order.size());
		}
		else
		{
			System.out.println("ERROR FOUR");
			Sub = Order;
		}
		return Sub;
	}
}

class MyCall implements Callable<ArrayList<Integer>>
{
String type;
int UnsolP[];

	MyCall( int[] puz, String H)
	{
		type = H;
		UnsolP = puz;
	}

	@Override
	public ArrayList<Integer> call() throws Exception
	{
		ArrayList<Integer> path;
		A_IDS_A_15solver ids = new A_IDS_A_15solver(UnsolP,type);
		path = ids.getINDEXList();
		return path;

	}

 }

