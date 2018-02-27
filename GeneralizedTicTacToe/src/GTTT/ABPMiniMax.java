package GTTT;
import java.util.*;

public class ABPMiniMax {
	
	//this is configured to show percentage.
	public static boolean showPercentage = true;
	private static int count;

	private ABPMiniMax(){}
	
	static void run(int activePlayer, Board board){
		if ((Algorithms.maxDepth[activePlayer-1]) < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }
        count = 0;
        alphaBetaPruning(activePlayer, board, -2000000, 2000000, 0, activePlayer);
	}
	
	private static double alphaBetaPruning(int currentPlayer, Board board, double alpha, double beta, int currentDepth, int hostPlayer){
		if (currentDepth++ == Algorithms.maxDepth[hostPlayer-1] || board.isGameOver()){
			return Evaluation.score(board,hostPlayer);
		}
		if (board.getPlayer()==1){
			return getMaxDeterminisitc(1, board, alpha, beta, currentDepth, hostPlayer);
		}else{
			return getMinDeterminisitc(2, board, alpha, beta, currentDepth, hostPlayer);
		}
	}
 
    private static double getMaxDeterminisitc (int player, Board board, double alpha, double beta, int currentDepth, int hostPlayer) {
        int indexOfBestMove = -1;

        if (showPercentage & currentDepth==1){
    		System.out.print("Calculated percentage:  0.0%%");
    	}
        
        for (Integer theMove : board.getAvailableMoves()) {
        	//print the percentage of traversal
        	if (showPercentage & currentDepth==1){
        		count++;
        		double percentage = (double)(count)/board.getAvailableMoves().size();
        		System.out.printf("\b\b\b\b\b     \b\b\b\b\b%4.1f%%%s",percentage*100,percentage==1?"\n":"");
        	}
        	
            Board newBoard = board.getDeepCopy();
            newBoard.move(theMove);
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth,hostPlayer);
            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;          
            }
            if (alpha >= beta) {
                break;
            }
        }
        // deterministic
        if (indexOfBestMove == -1 && currentDepth == 1)
        {
        	int a=0;
        	a++;
        }
        
        if (indexOfBestMove != -1) {
        	board.move(indexOfBestMove);
        }
        
        return alpha;
    }
    
    private static double getMinDeterminisitc (int player, Board board, double alpha, double beta, int currentDepth, int hostPlayer) {
        int indexOfBestMove = -1;

        if (showPercentage & currentDepth==1){
    		System.out.print("Calculated percentage:  0.0%%");
    	}
        
        for (Integer theMove : board.getAvailableMoves()) {
        	
        	//print the percentage of traversal
        	if (showPercentage & currentDepth==1){
        		count++;
        		double percentage = (double)(count)/board.getAvailableMoves().size();
        		System.out.printf("\b\b\b\b\b     \b\b\b\b\b%4.1f%%%s",percentage*100,percentage==1?"\n":"");
        	}

            Board newBoard = board.getDeepCopy();
            newBoard.move(theMove);
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth,hostPlayer);
            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;  
            }
            if (alpha >= beta) {
                break;
            }
        }
        
        if (indexOfBestMove == -1 && currentDepth == 1)
        {
        	int a=0;
        	a++;
        }
        // deterministic
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return beta;
    }
	
	
}
