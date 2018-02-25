package GTTT;
import java.util.Scanner;
import java.lang.*;
import java.util.HashSet;


public class Board {

	private int size = 3;
	private int winningRowSize = 3;
	
	public boolean searchMode1 = true;
	public boolean searchMode2 = true;
	public boolean searchMode3 = true;
	
	private int [][] tBoard;
	private int player;
	private int winner;
	private HashSet<Integer> movesAvailable;
	private HashSet<Integer> movesHalfAvailable;
	private HashSet<Integer> movesAdjacent;
	
	//the score from player 1 perspective.
	private double score1 = 0;
	private double score2 = 0;
	private int moveCount;
	private boolean gameOver;
	
	public static final char[] token = {' ','X','O'};
	public static final char[] tokenClassic = {'+','X','O'};
	public static final char[] tokenLite = {'.','X','O'};
	
	
	// 0 represents empty, 1 represents O or player 1, 2 represents X or player 2.
	//Construct and ini. Return empty board.
	public Board(int size, int winningRowSize) {
	   	this.size = size;
	   	this.winningRowSize = winningRowSize;
	   	this.tBoard = new int [size][size];
	   	movesAvailable = new HashSet<>();
	   	movesHalfAvailable = new HashSet<>();
	   	movesAdjacent = new HashSet<>();
	   	reset();
	}
	
	public Board(int size, int winningRowSize,int[] searchMode, int[] rivalSearchMode) {
		this.searchMode1 = (searchMode[0]==1||searchMode[1]==1||rivalSearchMode[0]==1||rivalSearchMode[1]==1)?true:false;
		this.searchMode2 = (searchMode[0]==2||searchMode[1]==2||rivalSearchMode[0]==2||rivalSearchMode[1]==2)?true:false;
		this.searchMode3 = (searchMode[0]==3||searchMode[1]==3||rivalSearchMode[0]==3||rivalSearchMode[1]==3)?true:false;
	   	this.size = size;
	   	this.winningRowSize = winningRowSize;
	   	this.tBoard = new int [size][size];
	   	movesAvailable = new HashSet<>();
	   	if (searchMode2)
	   		movesHalfAvailable = new HashSet<>();
	   	if (searchMode3)
	   		movesAdjacent = new HashSet<>();
	   	reset();
	}
	
	void reset(){
		moveCount = 0;
		gameOver = false;
		player = 1;
		winner = 0;
		score1 = 0;
		score2 = 0;
		initialize();
	}
	
	private void initialize () {
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size; j++)
	   		{
	   			tBoard[i][j]=0;
	   		}
	   	}
		movesAvailable.clear();
		if (searchMode2)
		movesHalfAvailable.clear();
		if (searchMode3)
			movesAdjacent.clear();
		
		if (!searchMode1){
			for (int i = 0; i < size*size; i++) {
	            movesAvailable.add(i);
	        }
		}
	}
	
	private int hashIndex(int x, int y){
		return x *size +y;
	}
	
	private String hashIndexToCoordinate(int index){
		return "(" + (index/size+1) + "," +  (index%size+1) + ")";
	}
	
	public boolean move(int index){
		return move(index/size,index%size);
	}
	
	public boolean move(int x, int y){
		if (gameOver){
			throw new IllegalStateException("Generalized TicTacToe is over. No moves can be played.");
		}
		if (tBoard[x][y] == 0){
			tBoard[x][y] = player;	
			
			gameOver = (size*size == moveCount);
			winner = CheckGameOver(x, y);
			//winner = CheckGameOver();
			if (winner!=0){
				gameOver = true;
			}
			
			if (gameOver && winner == 1) {
		        score1 = score2 = Double.POSITIVE_INFINITY;
		    } else if (gameOver && winner == 2) {
		    	score1 = score2 = Double.NEGATIVE_INFINITY;
		    } else {
		    	int[][] parsedBoard = new int [size][size];
		        for (int i = 0; i<size; i++)
		        {
		        	for (int j = 0; j<size; j++){
		        		if (tBoard[i][j] == 2){
		        			parsedBoard[i][j] = -1;
		        		}else{
		        			parsedBoard[i][j] = tBoard[i][j];
		        		}
		        	}
		        }
		    	score1 += Evaluation.evaluateIncrementScore(parsedBoard, x, y, winningRowSize, size, 1);
		    	parsedBoard[x][y] = (player==1?1:-1);
				score2 += Evaluation.evaluateIncrementScore(parsedBoard, x, y, winningRowSize, size, 2); 
		    }		
		}else{
			return false;
		}
		
		moveCount++;

		if (searchMode1){
			int x_start,x_end,y_start,y_end;
			x_start = Math.max(x - winningRowSize+1, 0);
			x_end = Math.min(x + winningRowSize-1, size-1);
			y_start = Math.max(y - winningRowSize+1, 0);
			y_end = Math.min(y + winningRowSize-1, size-1);
			// add the vertical column positions 
			for (int i = x_start;i<=x_end;i++){
				if (tBoard[i][y] == 0)
					movesAvailable.add(hashIndex(i, y));
			}
			// add the vertical column positions 
			for (int j = y_start;j<=y_end;j++){
				if (tBoard[x][j] == 0)
					movesAvailable.add(hashIndex(x,j));
			}
			//add the \ diagonal positions
			for (int i = x,j=y; i>=x_start && j >=y_start ;i--,j--){
				if (tBoard[i][j] == 0)
					movesAvailable.add(hashIndex(i,j));
			}
			for (int i = x,j=y; i<=x_end && j <=y_end ;i++,j++){
				if (tBoard[i][j] == 0)
					movesAvailable.add(hashIndex(i,j));
			}
			
			//add the / diagonal positions
			for (int i = x,j=y; i>=x_start && j <=y_end ;i--,j++){
				if (tBoard[i][j] == 0)
					movesAvailable.add(hashIndex(i,j));
			}
			for (int i = x,j=y; i<=x_end && j >=y_start ;i++,j--){
				if (tBoard[i][j] == 0)
					movesAvailable.add(hashIndex(i,j));
			}	
		}
		movesAvailable.remove(hashIndex(x, y));
		
		if (searchMode2){
			int x_start,x_end,y_start,y_end;
			x_start = Math.max(x - winningRowSize/2, 0);
			x_end = Math.min(x + winningRowSize/2, size-1);
			y_start = Math.max(y - (winningRowSize/2), 0);
			y_end = Math.min(y + winningRowSize/2, size-1);
			// add the vertical column positions 
			for (int i = x_start;i<=x_end;i++){
				if (tBoard[i][y] == 0)
					movesHalfAvailable.add(hashIndex(i, y));
			}
			// add the vertical column positions 
			for (int j = y_start;j<=y_end;j++){
				if (tBoard[x][j] == 0)
					movesHalfAvailable.add(hashIndex(x,j));
			}
			//add the \ diagonal positions
			for (int i = x,j=y; i>=x_start && j >=y_start ;i--,j--){
				if (tBoard[i][j] == 0)
					movesHalfAvailable.add(hashIndex(i,j));
			}
			for (int i = x,j=y; i<=x_end && j <=y_end ;i++,j++){
				if (tBoard[i][j] == 0)
					movesHalfAvailable.add(hashIndex(i,j));
			}
			
			//add the / diagonal positions
			for (int i = x,j=y; i>=x_start && j <=y_end ;i--,j++){
				if (tBoard[i][j] == 0)
					movesHalfAvailable.add(hashIndex(i,j));
			}
			for (int i = x,j=y; i<=x_end && j >=y_start ;i++,j--){
				if (tBoard[i][j] == 0)
					movesHalfAvailable.add(hashIndex(i,j));
			}
			movesHalfAvailable.remove(hashIndex(x, y));
		}

		
		if (searchMode3){
			movesAdjacent.remove(hashIndex(x, y));
			
			//Add adjacent move if they are blank while make sure they're not out of range.
			if (x-1>=0 && y-1 >=0 && tBoard[x-1][y-1] == 0)
				movesAdjacent.add(hashIndex(x-1, y-1));
			if (x-1>=0 && tBoard[x-1][y] == 0)
				movesAdjacent.add(hashIndex(x-1, y));
			if (x-1>=0 && y+1 <= size - 1 && tBoard[x-1][y+1] == 0)
				movesAdjacent.add(hashIndex(x-1, y+1));
			if (y-1 >=0 && tBoard[x][y-1] == 0)
				movesAdjacent.add(hashIndex(x, y-1));
			if (y+1 <= size - 1 && tBoard[x][y+1] == 0)
				movesAdjacent.add(hashIndex(x, y+1));
			if (x+1 <= size - 1 && y-1 >=0 && tBoard[x+1][y-1] == 0)
				movesAdjacent.add(hashIndex(x+1, y-1));
			if (x+1 <= size - 1 && tBoard[x+1][y] == 0)
				movesAdjacent.add(hashIndex(x+1, y));
			if (x+1 <= size - 1 && y+1 <= size - 1 && tBoard[x+1][y+1] == 0)
				movesAdjacent.add(hashIndex(x+1, y+1));
		}


		player = 3-player;
		return true;
	}
	
	
	
	//Check whether the game reach a draw or win.
	public boolean isGameOver () {
        return gameOver;
    }
	
	//Check who win the game
    public int getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("The game is not over yet.");
        }
        return winner;
    }
    
  //Check whose turn it is.
    public int getPlayer () {
        return player;
    }
    
    public double getScore1(){
    	return score1;
    }
    
    public double getScore2(){
    	return score2;
    }
    
    public int getWinningRowSize(){
    	return winningRowSize;
    }
    
    public int getSize(){
    	return size;
    }
    
    public int getMoveCount(){
    	return moveCount;
    }
    
    public boolean checkEmptyMove(int x,int y){
    	return tBoard[x][y] == 0;
    }
    
    public Board getDeepCopy(){
    	Board board = new Board(this.size, this.winningRowSize);	
        for (int i = 0; i < board.tBoard.length; i++) {
            board.tBoard[i] = this.tBoard[i].clone();
        }
        board.player = this.player;
        board.winner = this.winner;
        board.searchMode1 = this.searchMode1;
        board.searchMode2 = this.searchMode2;
        board.searchMode3 = this.searchMode3;
        board.movesAvailable    = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        if (searchMode2){
            board.movesHalfAvailable = new HashSet<>();
            board.movesHalfAvailable.addAll(this.movesHalfAvailable);
        }
        if (searchMode3){
            board.movesAdjacent    = new HashSet<>();
            board.movesAdjacent.addAll(this.movesAdjacent);
        }
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;
    	board.score1 = this.score1;
    	board.score2 = this.score2;
        return board;
    }
    
    public HashSet<Integer> getAvailableMoves () {
        return movesAvailable;
    }
    
    public HashSet<Integer> getHalfAvailableMoves () {
        return movesHalfAvailable;
    }
    
    public HashSet<Integer> getAdjacentMoves () {
        return movesAdjacent;
    }
    
    
    // to array of value 1 and -1.
    int [][] toArray () {
        int[][] result = new int [size][size];
        for (int i = 0; i<size; i++)
        {
        	for (int j = 0; j<size; j++){
        		if (tBoard[i][j] == 2){
        			result[i][j] = -1;
        		}else{
        			result[i][j] = tBoard[i][j];
        		}
        	}
        }
    	return result;
    }

    
  //Check gameover of current board based on the latest move.
  	public int CheckGameOver(int x_last, int y_last){
  		int count;
  		int i;
  		//check all possible row win
  		count = 1;
  		i=1;
  		while (count < winningRowSize && y_last-i >= 0 && tBoard[x_last][y_last-i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		i=1;
  		while (count < winningRowSize && y_last+i <= size - 1 && tBoard[x_last][y_last+i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		//check all possible column win
  		count =1;
  		i=1;
  		while (count < winningRowSize && x_last-i >=0 && tBoard[x_last-i][y_last] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		i=1;
  		while (count < winningRowSize && x_last+i <= size -1 && tBoard[x_last+i][y_last] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		//check all possible \ win
  		count = 1;
  		i=1;
  		while (count < winningRowSize && x_last-i>=0 && y_last-i >= 0 && tBoard[x_last-i][y_last-i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		i=1;
  		while (count < winningRowSize && x_last+i <= size - 1 && y_last+i <= size - 1 && tBoard[x_last+i][y_last+i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		//check all possible / win
  		count = 1;
  		i=1;
  		while (count < winningRowSize && x_last+i <= size - 1 && y_last-i >= 0 && tBoard[x_last+i][y_last-i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		i=1;
  		while (count < winningRowSize && x_last-i>=0 && y_last+i <= size - 1 && tBoard[x_last-i][y_last+i] == player){
  			count++;
  			i++;
  		}
  		if (count == winningRowSize){
  			return player;
  		}
  		
  		return 0;
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
  		
  		//Print the board with lite interface, primarily used.
  		public void PrintBoardLite(){
  			/*Print first line*/
  			System.out.print("x\\y|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.printf("%2d", i+1);
  			}
  			System.out.print("\n");
  			
  			/*Print second line*/
  			System.out.print("---|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.print("--");
  			}
  			System.out.print("\n");
  			
  			/*Print the rest*/
  			
  			for (int i = 0; i < size ; i++)
  		   	{
  				System.out.printf("%2d |",i+1);
  		   		for (int j = 0; j < size; j++)
  		   		{
  		   			System.out.print(" "+tokenLite[tBoard[i][j]]);
  		   		}
  		   		System.out.print("\n");
  		   	}
  		}
  		
  		public void PrintBoardLiteWithAvail(){
  			/*Print first line*/
  			System.out.print("x\\y|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.printf("%2d", i+1);
  			}
  			System.out.print("\n");
  			
  			/*Print second line*/
  			System.out.print("---|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.print("--");
  			}
  			System.out.print("\n");
  			
  			/*Print the rest*/
  			
  			for (int i = 0; i < size ; i++)
  		   	{
  				System.out.printf("%2d |",i+1);
  		   		for (int j = 0; j < size; j++)
  		   		{
  		   			if (movesAvailable.contains(hashIndex(i,j))){
  		   				System.out.print(" #");
  		   			}else{
  		   				System.out.print(" "+tokenLite[tBoard[i][j]]);
  		   			}
  		   		}
  		   		System.out.print("\n");
  		   	}
  		}
  		
  		public void PrintBoardLiteWithHalfAvail(){
  			/*Print first line*/
  			System.out.print("x\\y|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.printf("%2d", i+1);
  			}
  			System.out.print("\n");
  			
  			/*Print second line*/
  			System.out.print("---|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.print("--");
  			}
  			System.out.print("\n");
  			
  			/*Print the rest*/
  			
  			for (int i = 0; i < size ; i++)
  		   	{
  				System.out.printf("%2d |",i+1);
  		   		for (int j = 0; j < size; j++)
  		   		{
  		   			if (movesHalfAvailable.contains(hashIndex(i,j))){
  		   				System.out.print(" #");
  		   			}else{
  		   				System.out.print(" "+tokenLite[tBoard[i][j]]);
  		   			}
  		   		}
  		   		System.out.print("\n");
  		   	}
  		}
  		
  		public void PrintBoardLiteWithAdaj(){
  			/*Print first line*/
  			System.out.print("x\\y|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.printf("%2d", i+1);
  			}
  			System.out.print("\n");
  			
  			/*Print second line*/
  			System.out.print("---|");
  			for (int i = 0; i < size ; i++)
  			{
  				System.out.print("--");
  			}
  			System.out.print("\n");
  			
  			/*Print the rest*/
  			
  			for (int i = 0; i < size ; i++)
  		   	{
  				System.out.printf("%2d |",i+1);
  		   		for (int j = 0; j < size; j++)
  		   		{
  		   			if (movesAdjacent.contains(hashIndex(i,j))){
  		   				System.out.print(" #");
  		   			}else{
  		   				System.out.print(" "+tokenLite[tBoard[i][j]]);
  		   			}
  		   		}
  		   		System.out.print("\n");
  		   	}
  		}
  		
  //*********************************************************************Obselete********************************************************************//

	//Return 0 if no one win, 1 for player 1 win, 2 for player 2 win.
//	public int CheckGameOver(){
//		//0 is no winner (yet), 1 is player 1 win, 2 is player 2 win.
//		int result = 0;
//		int count = 0;
//		
//		//check all possible rows
//		for (int i = 0; i < size ; i++)
//	   	{
//	   		for (int j = 0; j < size-winningRowSize +1; j++)
//	   		{
//	   			count = 0;
//	   			for (int k = j; k < j+winningRowSize; k++)
//	   			{
//	   				if (tBoard[i][k] == 0)
//	   				{
//	   					count = 0;
//	   					break;
//	   				}
//	   				count += tBoard[i][k];   					
//	   			}
//	   			if (count == 2 * winningRowSize){
//	   				result = 2;
//	   				return result;
//	   			}
//	   			else if (count == winningRowSize){
//	   				result = 1;
//	   				return result;
//	   			}
//	   		}
//	   	}
//		//check all possible columns		
//		for (int j = 0; j < size ; j++)
//	   	{
//	   		for (int i = 0; i < size-winningRowSize +1; i++)
//	   		{
//	   			count = 0;
//	   			for (int k = i; k < i+winningRowSize; k++)
//	   			{
//	   				if (tBoard[k][j] == 0)
//	   				{
//	   					count = 0;
//	   					break;
//	   				}
//	   				count += tBoard[k][j];   					
//	   			}
//	   			if (count == 2 * winningRowSize){
//	   				result = 2;
//	   				return result;
//	   			}
//	   			else if (count == winningRowSize){
//	   				result = 1;
//	   				return result;
//	   			}
//	   		}
//	   	}
//		
//		//check all possible \ diagonal
//		for (int i = 0; i < size-winningRowSize +1 ; i++)
//	   	{
//	   		for (int j = 0; j < size-winningRowSize +1; j++)
//	   		{
//	   			count = 0;
//	   			for (int k = 0; k < winningRowSize; k++)
//	   			{
//	   				if (tBoard[i+k][j+k] == 0)
//	   				{
//	   					count = 0;
//	   					break;
//	   				}
//	   				count += tBoard[i+k][j+k];   					
//	   			}
//	   			if (count == 2 * winningRowSize){
//	   				result = 2;
//	   				return result;
//	   			}
//	   			else if (count == winningRowSize){
//	   				result = 1;
//	   				return result;
//	   			}
//	   		}
//	   	}
//		
//		//check all possible / diagonal
//		for (int i = winningRowSize - 1; i < size; i++)
//	   	{
//	   		for (int j = 0; j < size-winningRowSize +1; j++)
//	   		{
//	   			count = 0;
//	   			for (int k = 0; k < winningRowSize; k++)
//	   			{
//	   				if (tBoard[i-k][j+k] == 0)
//	   				{
//	   					count = 0;
//	   					break;
//	   				}
//	   				count += tBoard[i-k][j+k];   					
//	   			}
//	   			if (count == 2 * winningRowSize){
//	   				result = 2;
//	   				return result;
//	   			}
//	   			else if (count == winningRowSize){
//	   				result = 1;
//	   				return result;
//	   			}
//	   		}
//	   	}
//		
//		return result;
//	}
	
	
	//Check whether is last move, help to determine whether this is a draw.
//	public boolean CheckLastMove(){
//		return size*size == moveCount;
//	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board newboard = new Board(15, 5);
		//newboard.tBoard[0][0] = newboard.tBoard[0][1] = newboard.tBoard[0][2] = newboard.tBoard[0][3] = newboard.tBoard[0][4] = 2;
		//newboard.tBoard[0][0] = newboard.tBoard[1][1] = newboard.tBoard[2][2] = newboard.tBoard[3][3] = newboard.tBoard[4][4] = 2;
		//newboard.tBoard[3][3] = 1;
		//newboard.tBoard[0][0] = newboard.tBoard[1][0] = newboard.tBoard[2][0] = newboard.tBoard[3][0] = newboard.tBoard[4][0] = 2;
		//newboard.tBoard[0][4] = newboard.tBoard[1][3] = newboard.tBoard[2][2] = newboard.tBoard[3][1] = newboard.tBoard[4][0] = 2;
		newboard.move(7,7);
		//newboard.PrintBoardLiteWithHalfAvail();
		newboard.PrintBoardLiteWithAdaj();
		newboard.move(7,8);
		//newboard.PrintBoardLiteWithHalfAvail();
		newboard.PrintBoardLiteWithAdaj();
		
		//System.out.println(newboard.checkGameOver());
		//newboard.Player2Player();
		//newboard.Player2AI();
		//newboard.AI2AI();
		//newboard.AI2RANDOM();
		//Player to AI game


	}

}
