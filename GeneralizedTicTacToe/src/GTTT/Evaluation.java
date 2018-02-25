package GTTT;

public class Evaluation {
	
	//at current implementation adversary Bonus cannot be other than 1.0.

	
	
	private Evaluation(){}
	
    public static double score (Board board, int activePlayer) {

            if (activePlayer == 1)
            	return board.getScore1();  
            else
            	return board.getScore2();  

    }
    
    //Evaluate using the method in https://web.stanford.edu/class/cs221/2017/restricted/p-final/xiaotihu/final.pdf
    public static double winningWindowsScore(Board board){
    	int winningRowSize = board.getWinningRowSize();
    	int size = board.getSize();
    	double score = 0;
    	int sum;
    	int [][] sBoard = board.toArray();
    	
    	int[] feature = new int[winningRowSize-1];
    	
    	//check all possible rows
		for (int i = 0; i < size ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			sum = 0;
	   			for (int k = j; k < j+winningRowSize; k++)
	   			{
	   				if (sBoard[i][k] == 0)
	   				{
	   					continue;
	   				}
	   				else if (sum * sBoard[i][k] >=0){
	   					sum += sBoard[i][k];  
	   				}
	   				else {
	   					sum = 0;
	   					break;
	   				}
	   			}
	   			if (sum == 0){
	   			}else if (sum>0){
	   				feature[sum-1]++;
	   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
	   			}else{
	   				feature[-sum-1]--;
	   			}
	   		}
	   	}
		//check all possible columns		
		for (int j = 0; j < size ; j++)
	   	{
	   		for (int i = 0; i < size-winningRowSize +1; i++)
	   		{
	   			sum = 0;
	   			for (int k = i; k < i+winningRowSize; k++)
	   			{
	   				if (sBoard[k][j] == 0)
	   				{
	   					continue;
	   				}
	   				else if (sum * sBoard[k][j] >=0){
	   					sum += sBoard[k][j];  
	   				}
	   				else {
	   					sum = 0;
	   					break;
	   				}   					
	   			}
	   			
	   			if (sum == 0){
	   			}else if (sum>0){
	   				feature[sum-1]++;
	   				//System.out.printf("columns starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
	   			}else{
	   				feature[-sum-1]--;
	   			}
	   		}
	   	}
		
		//check all possible \ diagonal
		for (int i = 0; i < size-winningRowSize +1 ; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			sum = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (sBoard[i+k][j+k] == 0)
	   				{
	   					continue;
	   				}
	   				else if (sum * sBoard[i+k][j+k] >=0){
	   					sum += sBoard[i+k][j+k];  
	   				}
	   				else {
	   					sum = 0;
	   					break;
	   				} 					
	   			}
	   			if (sum == 0){
	   			}else if (sum>0){
	   				feature[sum-1]++;
	   				//System.out.printf("\\digonal starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
	   			}else{
	   				feature[-sum-1]--;
	   			}
	   		}
	   	}
		
		//check all possible / diagonal
		for (int i = winningRowSize - 1; i < size; i++)
	   	{
	   		for (int j = 0; j < size-winningRowSize +1; j++)
	   		{
	   			sum = 0;
	   			for (int k = 0; k < winningRowSize; k++)
	   			{
	   				if (sBoard[i-k][j+k] == 0)
	   				{
	   					continue;
	   				}
	   				else if (sum * sBoard[i-k][j+k] >=0){
	   					sum += sBoard[i-k][j+k];  
	   				}
	   				else {
	   					sum = 0;
	   					break;
	   				}   					
	   			}
	   			if (sum == 0){
	   			}else if (sum>0){
	   				feature[sum-1]++;
	   				//System.out.printf("/diagonal starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
	   			}else{
	   				feature[-sum-1]--;
	   			}
	   		}
	   	}
    	
    	for (int i = 0;i<feature.length;i++){
    		//System.out.printf("%d:%d, ", i+1 , feature[i]);
    		score += (i+1)*feature[i]*Math.pow(2,i);
    	}
    	return score;
    	
    }
    
    public static double evaluateIncrementScore(int[][] board, int x, int y, int winningRowSize,
    											int size, int hostPlayer){
    	
    	double incrementScore = 0;
    	int n = Math.min(x, winningRowSize-1);
    	int s = Math.min(size-winningRowSize-x, 0);
    	int w = Math.min(y, winningRowSize-1);
    	int e = Math.min(size-winningRowSize-y, 0);
    	
    	int nw = Math.min(winningRowSize-1,Math.min(x,y));
    	int se = Math.min(0,Math.min(size-winningRowSize-x, size-winningRowSize-y));
    	
    	int ne = Math.min(winningRowSize-1,Math.min(x, size-winningRowSize-y));
    	int sw = Math.min(0,Math.min(size-winningRowSize-x, y-winningRowSize+1));
    	
    	//feature of player 1.
    	int[] feature1 = new int[winningRowSize-1];
    	//feature of player 2.
    	int[] feature2 = new int[winningRowSize-1];
    	int sum;
    	
    	//check score after change
    	//check row
    	for (int j = y-w; j<= y+e; j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[x][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[x][j+k] >=0){
   					sum += board[x][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}	
    	
    	//check column		
    	for (int i = x-n; i<= x+s; i++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][y] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][y] >=0){
   					sum += board[i+k][y];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}
    	
    	//check \ diagonal
    	for (int i = x-nw, j = y-nw; i <= x+se;i++,j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j+k] >=0){
   					sum += board[i+k][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}

    	//check / diagonal
    	for (int i=x-ne,j=y+ne;i<= x+sw;i++,j--){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j-k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j-k] >=0){
   					sum += board[i+k][j-k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]++;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]--;
   			}
    	}
    	
    	//check original score after change
    	board[x][y] = 0;
    	//check row
    	for (int j = y-w; j<= y+e; j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[x][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[x][j+k] >=0){
   					sum += board[x][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}	
    	
    	//check column		
    	for (int i = x-n; i<= x+s; i++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][y] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][y] >=0){
   					sum += board[i+k][y];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}
    	
    	//check \ diagonal
    	for (int i = x-nw, j = y-nw; i <= x+se;i++,j++){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j+k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j+k] >=0){
   					sum += board[i+k][j+k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}

    	//check / diagonal
    	for (int i=x-ne,j=y+ne;i<= x+sw;i++,j--){
    		sum = 0;
    		for (int k=0; k<winningRowSize;k++){
    			if (board[i+k][j-k] == 0)
   				{
   					continue;
   				}
   				else if (sum * board[i+k][j-k] >=0){
   					sum += board[i+k][j-k];  
   				}
   				else {
   					sum = 0;
   					break;
   				}
    		}
    		if (sum>0){
   				feature1[sum-1]--;
   				//System.out.printf("row starts at (%d,%d):%d ",i+1 ,j+1 ,sum);
   			}else if (sum<0){
   				feature2[-sum-1]++;
   			}
    	}
	
    	for (int i = 0;i<feature1.length;i++){
    		//System.out.printf("%d:%d, ", i+1 , feature[i]);
    		incrementScore += (i+1)*feature1[i]*Math.pow(Algorithms.connectBonus[hostPlayer-1],i)*Algorithms.player1Bonus[hostPlayer-1]
    							+ (i+1)*feature2[i]*Math.pow(Algorithms.connectBonus[hostPlayer-1],i)*Algorithms.player2Bonus[hostPlayer-1];
    	} 	
    	return incrementScore;
    }

    
    static int gomoku_01(Board board){
    	return 0;
    }
    
    static int g18_8_01(Board board){
    	return 0;
    }
    
    static int g15_7_01(Board board){
    	return 0;
    }
    
    static int g12_6_01(Board board){
    	return 0;
    }
    
    public static void main(String[] args) {
        Board board = new Board(6, 5);
        board.move(1,2);
        board.move(3,1);
        board.move(2,2);
        board.move(4,1);
        board.move(4,2);
        board.move(5,1);
        board.move(5,2);
        board.move(3,4);
        board.move(3,3);
        board.PrintBoardLite();
        //System.out.printf("The score is %d", score(board));
    }

}
