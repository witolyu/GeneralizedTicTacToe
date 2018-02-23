package GTTT;
import java.util.*;

public class ABPMiniMax {
	
	public static boolean showPercentage = true;
	public static boolean randomBestMove = true;
	
	private static int maxDepth;
	private static int count;
	
	private static Random rand = new Random();
	
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
			return randomBestMove?getMax(1, board, alpha, beta, currentDepth):getMaxDeterminisitc(1, board, alpha, beta, currentDepth);
		}else{
			return randomBestMove?getMin(2, board, alpha, beta, currentDepth):getMinDeterminisitc(2, board, alpha, beta, currentDepth);
		}
	}
	
    private static int getMax (int player, Board board, double alpha, double beta, int currentDepth) {
        ArrayList<Integer> indiceOfBestMoves=new ArrayList<Integer>();  

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
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score > alpha) {
                alpha = score;
                indiceOfBestMoves.clear();
                indiceOfBestMoves.add(theMove);             
            }else if(score == alpha){	
            	indiceOfBestMoves.add(theMove);
            }
            if (alpha >= beta) {
                break;
            }
        }
        // randomized
        if (!indiceOfBestMoves.isEmpty()) {
            int randomMoveIndex = rand.nextInt(indiceOfBestMoves.size());
            board.move(indiceOfBestMoves.get(randomMoveIndex));
        }

        return (int)alpha;
    }
    
    private static int getMin (int player, Board board, double alpha, double beta, int currentDepth) {
        ArrayList<Integer> indiceOfBestMoves=new ArrayList<Integer>();  

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
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score < beta) {
                beta = score;
                indiceOfBestMoves.clear();
                indiceOfBestMoves.add(theMove);   
            }else if(score == beta){	
            	indiceOfBestMoves.add(theMove);
            }
            if (alpha >= beta) {
                break;
            }
        }
        // randomized
        if (!indiceOfBestMoves.isEmpty()) {
            int randomMoveIndex = rand.nextInt(indiceOfBestMoves.size());
        	board.move(indiceOfBestMoves.get(randomMoveIndex));
        }

        return (int)beta;
    }
    
    private static int getMaxDeterminisitc (int player, Board board, double alpha, double beta, int currentDepth) {
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
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;          
            }
            if (alpha >= beta) {
                break;
            }
        }
        // deterministic
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        
        return (int)alpha;
    }
    
    private static int getMinDeterminisitc (int player, Board board, double alpha, double beta, int currentDepth) {
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
            double score = alphaBetaPruning(player, newBoard, alpha, beta, currentDepth);
            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;  
            }
            if (alpha >= beta) {
                break;
            }
        }
        // deterministic
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return (int)beta;
    }
	
	
}
