package GTTT;
import java.util.*;

public class ABPMiniMax {
	
	public static boolean showPercentage = false;
	
	private static int maxDepth;
	private static int count;
	
	private ABPMiniMax(){}
	
	static void run(int player, Board board, int maxDepth){
		if (maxDepth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }
        ABPMiniMax.maxDepth = maxDepth;
        count = 0;
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
	}
	
	private static double alphaBetaPruning(int currentPlayer, Board board, double alpha, double beta, int currentDepth){
		if (currentDepth++ == maxDepth || board.isGameOver()){
			return Evaluation.score(board);
		}
		if (board.getPlayer()==1){
			return getMax(1, board, alpha, beta, currentDepth);
		}else{
			return getMin(2, board, alpha, beta, currentDepth);
		}
	}
	
    private static int getMax (int player, Board board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;
        ArrayList<Integer> indiceOfBestMoves=new ArrayList<Integer>();  

        for (Integer theMove : board.getAvailableMoves()) {
        	//print the percentage of traversal
        	if (showPercentage & currentDepth==1){
        		count++;
        		double percentage = (double)(count)/board.getAvailableMoves().size();
        		System.out.printf("Calculated percentage: %3.1f%%\n",percentage*100);
        	}
        	
            Board newBoard = board.getDeepCopy();
            newBoard.move(theMove);
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;
                indiceOfBestMoves.clear();
                indiceOfBestMoves.add(theMove);             
            }else if(score == alpha){	
            	indiceOfBestMoves.add(theMove);
            }
            if (alpha >= beta) {
                break;
            }
        }
        // deterministic
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        // randomized
        //***
        //***
        //***
        //***

        return (int)alpha;
    }
    
    private static int getMin (int player, Board board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;
        ArrayList<Integer> indiceOfBestMoves=new ArrayList<Integer>();  

        for (Integer theMove : board.getAvailableMoves()) {
        	
        	//print the percentage of traversal
        	if (showPercentage & currentDepth==1){
        		count++;
        		double percentage = (double)(count)/board.getAvailableMoves().size();
        		System.out.printf("Calculated percentage: %3.1f%%\n",percentage*100);
        	}

            Board newBoard = board.getDeepCopy();
            newBoard.move(theMove);
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;
                indiceOfBestMoves.clear();
                indiceOfBestMoves.add(theMove);   
            }else if(score == alpha){	
            	indiceOfBestMoves.add(theMove);
            }
            if (alpha >= beta) {
                break;
            }
        }
        // deterministic
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        // randomized
        //***
        //***
        //***
        //***

        return (int)beta;
    }
	
	
}
