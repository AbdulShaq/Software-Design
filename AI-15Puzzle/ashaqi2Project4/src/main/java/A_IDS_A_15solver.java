
import java.util.ArrayList;

public class A_IDS_A_15solver {
	
	/*
	 * @author Mark Hallenbeck
	 * Copyright© 2014, Mark Hallenbeck, All Rights Reservered.
	 */
	ArrayList<Node> solutionPath;
	DB_Solver2 start_A_Star;
	ArrayList<Integer> INDEX = new ArrayList<>();
public A_IDS_A_15solver(int[] T, String heuristic){
		
		UserInterface puzzle = new UserInterface(T);			//class for reading in puzzle from user
		
		Node startState = new Node(puzzle.getPuzzle());		//node contains the start state of puzzle
		
		startState.setDepth(0);
	if(heuristic == "heuristicOne")
	{
		System.out.println("\nStarting A* Search with heuristic #1....This may take a while\n\n");

		A_Star(startState, heuristic);                            //A* search with heuristic 1 (misplaced tiles)
	}
	else
	{
		System.out.println("\nStarting A* Search with heuristic #2....This may take a while\n\n");

		
		A_Star(startState, heuristic);							//A* search with heuristic 2 (manhattan)
	}
		
		System.out.println("\nThanks for using me to solve your 15 puzzle......Goodbye");
		//System.exit(1);
		
	}

/**
 * Method takes node with the start state as well as which heuristic to use and initializes a DB_Solver2 object(A* search).
 * It then solves the puzzle and prints out some metadata and the solution path
 * @param startState
 * @param heuristic
 */
	public void A_Star(Node startState, String heuristic){


		start_A_Star = new DB_Solver2(startState, heuristic);	//DB_Solver class initialized with startState node
		
				
		Long start = System.currentTimeMillis();

		Node solution = start_A_Star.findSolutionPath();	//returns the node that contains the solved puzzle
		
		Long end = System.currentTimeMillis();

		System.out.println("\n******Run Time for A* "+ heuristic + " is: "+ (end-start) + " milliseconds**********");
		
		if(solution == null)								//no solution was found
		{
			System.out.println("\nThere did not exist a solution to your puzzle with A* search\n");
		}
		else											//found a solution so, get the path and print it
		{
			ArrayList<Node> solutionPath = start_A_Star.getSolutionPath(solution);	//creates ArrayList of solution path
			
			printSolution(solutionPath);
			
			//System.out.println("\n$$$$$$$$$$$$$$ the solution path is "+ solutionPath.size()+ " moves long\n");
		}
		
		
	}
	
	

	public void printSolution(ArrayList<Node> path){
	
		System.out.print("\n\n");
		
		System.out.println("**************Initial State******************");
		for(int i=0; i<path.size(); i++){
			
			printState(path.get(i),i);
			
			if(i != (path.size() - 1))
				System.out.print("\nNext State => "+i+"\n\n");
			
		}
		System.out.println("\n**************Goal state****************");
	}

	public void printState(Node node,int start){
	
		int[] puzzleArray = node.getKey();

		for(int i =0; i< puzzleArray.length; i++){
		
			System.out.printf("%4d ",puzzleArray[i]);
			if( start!= 0 && puzzleArray[i]== 0)
			{
				INDEX.add(i);

			}
			if(i == 3 || i == 7 || i == 11)
				System.out.print("\n");
		}
	
}

	public ArrayList<Integer> getINDEXList() {
		return INDEX;
	}

}
