import java.util.Random;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

public class Board {

	private int size = 3;
	private int winningRowSize = 3;
	private int [][] tBoard;
	private int moveCount;
	private Random ran = new Random();
	
	// 0 represents empty, 1 represents O or player 1, 2 represents X or player 2.
	public static final char[] token = {' ','O','X'};
	public static final char[] tokenClassic = {'+','O','X'};

	//Constructor. Return empty board.
	public Board(int size, int winningRowSize) {
	   	this.size = size;
	   	this.winningRowSize = winningRowSize;
	   	this.tBoard = new int [size][size];
	   	for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			tBoard[i][j]=0;
	   		}
	   	}
	}
	
	// evaluate and return the score of each possible move
	public int MiniMax(int[][] refBoard, int player){
		int score = -2;
		int temp;
		// clone the board
		int newboard[][] = new int [size][size];
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			newboard[i][j] = refBoard[i][j];
	   		}
	   	}
		
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			if (newboard[i][j]!=0){
	   				continue;
	   			}

	   			newboard[i][j] = player;
	   			if (CheckGameOver(newboard) == player){
	   				return 1;
	   			}
	   			
	   			if (CheckFullBoard(newboard)){
	   				temp = 0;
	   			}
	   			else{
		   			temp = -MiniMax(newboard, player%2+1);
	   			}
	   			score = Math.max(score,temp);
	   			newboard[i][j] = 0;
	   		}
	   	}		
		return score;		
	}

	
	// Lack optimization (Exhaustive search, lacks depth limitation and heuristic value for larger size board)
	public void AIMove()
	{
		int player = (moveCount % 2) +1;
		int moveX,moveY;
		int temp;
		int score = -2; //this is set so that at least one move would be select.
		
		moveX = moveY = -1; //This should never happen.
		
		/*AI do calculation and update the board*/
	   
		int newboard[][] = new int [size][size];
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			newboard[i][j] = tBoard[i][j];
	   		}
	   	}
		
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			if (newboard[i][j]!=0){
	   				continue;
	   			}
	   			newboard[i][j] = player;
	   			if (CheckGameOver(newboard) == player){
	   				tBoard[i][j] = player;
	   				System.out.printf("AI%d has make the move by placing on row %d,colomn %d\n", player, i+1, j+1);
	   				PrintBoard();
	   				moveCount++;
	   				return;
	   			}
	   			if (CheckFullBoard(newboard)){
	   				temp = 0;
	   			}
	   			else{
		   			temp = -MiniMax(newboard, player%2+1);
	   			}
	   			if (temp > score)
	   			{
	   				score = temp;
	   				moveX = i;
		   			moveY = j;
	   			}
	   			newboard[i][j] = 0;
	   		}
	   	}
		tBoard[moveX][moveY] = player;
		System.out.printf("AI%d has make the move by placing on row %d,colomn %d, the score of this move is %d\n", player, moveX+1, moveY+1, score);
		PrintBoard();
		moveCount++;
	}
	
	// A random move generator for testing purpose.
	public void RandomMove()
	{
		int player = (moveCount % 2) +1;
		int x,y;
		 
		
		// loop error check for the input, whether it is a solid move?
		while (true)
		{
			x = ran.nextInt(size-1);
			y = ran.nextInt(size-1);
			
			if (tBoard[x][y] == 0)
				break;
		}
		tBoard[x][y] = player;
		//if valid, update the board 
		
		System.out.printf("RAM_AI%d has make the move by placing on row %d,colomn %d\n", player, x+1, y+1);
		PrintBoard();
		moveCount++;
	}
	
	//Prompt the player to input
	public void PlayerMove()
	{
		int cToken = (moveCount % 2) +1;
		int x,y;
		String[] temp;    
		
		// loop error check for the input, whether it is a solid move?
		while (true)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.printf("Player%d, please indicate your move by row,colomn, for example 5,7 means row 5 and column 7:\n", cToken);
			String input = keyboard.nextLine();
			temp = input.split(",");
			x = Integer.parseInt(temp[0])-1;
			y = Integer.parseInt(temp[1])-1;
			
			if (x < size && y < size && tBoard[x][y] == 0)
				break;
			System.out.printf("Player%d, that's not a valid move.\n", cToken);
		}
		tBoard[x][y] = cToken;
		//if valid, update the board 
		
		moveCount++;
		PrintBoard();
	}
	
	//Print the current board
 	public void PrintBoard(){
		for (int i = 0; i < size-1 ; i++)
	   	{
	   		for (int j = 0; j < size-1; j++)
	   		{
	   			System.out.print(token[tBoard[i][j]]+" | ");
	   		}
	   		System.out.println(token[tBoard[i][size-1]]);

	   		for (int j = 0; j < size-1; j++)
	   		{
	   			System.out.print("- + ");
	   		}
	   		System.out.println("-");
	   	}
		
   		for (int j = 0; j < size-1; j++)
   		{
   			System.out.print(token[tBoard[size-1][j]]+" | ");
   		}
   		System.out.println(token[tBoard[size-1][size-1]]);
	}
	
 	//Print the current board with a classic style
	public void PrintBoardClassic(){
		for (int i = 0; i < size-1 ; i++)
	   	{
	   		for (int j = 0; j < size-1; j++)
	   		{
	   			System.out.print(tokenClassic[tBoard[i][j]]+" - ");
	   		}
	   		System.out.println(tokenClassic[tBoard[i][size-1]]);

	   		for (int j = 0; j < size-1; j++)
	   		{
	   			System.out.print("|   ");
	   		}
	   		System.out.println("|");
	   	}
		
   		for (int j = 0; j < size-1; j++)
   		{
   			System.out.print(tokenClassic[tBoard[size-1][j]]+" - ");
   		}
   		System.out.println(tokenClassic[tBoard[size-1][size-1]]);
	}
	
	//Return 0 if no one win, 1 for player 1 win, 2 for player 2 win.
	public int CheckGameOver(){
		//0 is no winner (yet), 1 is player 1 win, 2 is player 2 win.
		int result = 0;
		int count = 0;
		
		//check all possible rows
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = j; k < j+winningRowSize; k++)
	   			{
	   				if (tBoard[i][k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += tBoard[i][k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		//check all possible columns		
		for (int j = 0; j < size ; j++)
	   	{
	   		for (int i = 0; i < size-winningRowSize +1; i++)
	   		{
	   			count = 0;
	   			for (int k = i; k < i+winningRowSize; k++)
	   			{
	   				if (tBoard[k][j] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += tBoard[k][j];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		//check all possible \ diagonal
		for (int i = 0; i < size-winningRowSize +1 ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (tBoard[i+k][j+k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += tBoard[i+k][j+k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		//check all possible / diagonal
		for (int i = winningRowSize - 1; i < size; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (tBoard[i-k][j+k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += tBoard[i-k][j+k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		return result;
	}
	
	public int CheckGameOver(int[][] currentboard){
		//0 is no winner (yet), 1 is player 1 win, 2 is player 2 win.
		int result = 0;
		int count = 0;
		
		//check all possible rows
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = j; k < j+winningRowSize; k++)
	   			{
	   				if (currentboard[i][k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += currentboard[i][k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		//check all possible columns		
		for (int j = 0; j < size ; j++)
	   	{
	   		for (int i = 0; i < size-winningRowSize +1; i++)
	   		{
	   			count = 0;
	   			for (int k = i; k < i+winningRowSize; k++)
	   			{
	   				if (currentboard[k][j] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += currentboard[k][j];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		//check all possible \ diagonal
		for (int i = 0; i < size-winningRowSize +1 ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (currentboard[i+k][j+k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += currentboard[i+k][j+k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		//check all possible / diagonal
		for (int i = winningRowSize - 1; i < size; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			count = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (currentboard[i-k][j+k] == 0)
	   				{
	   					count = 0;
	   					break;
	   				}
	   				count += currentboard[i-k][j+k];   					
	   			}
	   			if (count == 2 * winningRowSize){
	   				result = 2;
	   				return result;
	   			}
	   			else if (count == winningRowSize){
	   				result = 1;
	   				return result;
	   			}
	   		}
	   	}
		
		return result;
	}
	
	/*!!!!!!!!!!!!!!!!!!! another check should be provide based on the newest move, so that no need to check every possible row, column and diagonal*/
	
	//Check whether is last move, help to determine the last step.
	public boolean CheckLastMove(){
		return size*size == moveCount;
	}
	
	//Similar to checklastmove, accept variable.
	public boolean CheckFullBoard(int curboard[][]){
		boolean result = true;
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			if (curboard[i][j] == 0 ){
	   				result = false;
	   			}
	   		}
	   	}
		return result;
	}
	
	public void Player2Player(){
		//Player to player game
		System.out.println("Game start! Player 1 vs Player 2!");
		while(true)
		{
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			PlayerMove();
			if (CheckGameOver()==1)
			{
				System.out.println("Player 1 win!");
				break;
			}
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			PlayerMove();
			if (CheckGameOver()==2)
			{
				System.out.println("Player 2 win!");
				break;
			}
		}		
	}
	
	public void Player2AI(){
		System.out.println("Game start! Player 1 vs AI 2!");
		while(true)
		{
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			PlayerMove();
			if (CheckGameOver()==1)
			{
				System.out.println("Player 1 win!");
				break;
			}
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			AIMove();
			if (CheckGameOver()==2)
			{
				System.out.println("AI 2 win!");
				break;
			}
		}	
	}
	
	public void AI2AI(){
		System.out.println("Game start! AI 1 vs AI 2!");
		while(true)
		{
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			AIMove();
			if (CheckGameOver()==1)
			{
				System.out.println("AI 1 win!");
				break;
			}
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			AIMove();
			if (CheckGameOver()==2)
			{
				System.out.println("AI 2 win!");
				break;
			}
		}	
	}
	
	public void AI2RANDOM(){
		System.out.println("Game start! AI 1 vs AI 2!");
		while(true)
		{
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			AIMove();
			if (CheckGameOver()==1)
			{
				System.out.println("AI 1 win!");
				break;
			}
			if (CheckLastMove())
			{
				System.out.println("This is a draw!");
				break;
			}
			RandomMove();
			if (CheckGameOver()==2)
			{
				System.out.println("Random 2 win!");
				break;
			}
		}	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board newboard = new Board(3, 3);
		//newboard.tBoard[0][0] = newboard.tBoard[0][1] = newboard.tBoard[0][2] = newboard.tBoard[0][3] = newboard.tBoard[0][4] = 2;
		//newboard.tBoard[0][0] = newboard.tBoard[1][1] = newboard.tBoard[2][2] = newboard.tBoard[3][3] = newboard.tBoard[4][4] = 2;
		//newboard.tBoard[3][3] = 1;
		//newboard.tBoard[0][0] = newboard.tBoard[1][0] = newboard.tBoard[2][0] = newboard.tBoard[3][0] = newboard.tBoard[4][0] = 2;
		//newboard.tBoard[0][4] = newboard.tBoard[1][3] = newboard.tBoard[2][2] = newboard.tBoard[3][1] = newboard.tBoard[4][0] = 2;
		newboard.PrintBoard();
		//System.out.println(newboard.checkGameOver());
		//newboard.Player2Player();
		//newboard.Player2AI();
		newboard.AI2AI();
		//newboard.AI2RANDOM();
		//Player to AI game
	

	}

}
