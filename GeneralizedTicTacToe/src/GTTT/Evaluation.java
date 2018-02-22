package GTTT;

public class Evaluation {
	
	private Evaluation(){}
	
    public static double score (Board board) {

        if (board.isGameOver() && board.getWinner() == 1) {
            return Double.POSITIVE_INFINITY;
        } else if (board.isGameOver() && board.getWinner() == 2) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return winningWindowsScore(board);  
        }
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
    		score += (i+1)*feature[i]*Math.pow(1.1,i);
    	}
    	return score;
    	
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
