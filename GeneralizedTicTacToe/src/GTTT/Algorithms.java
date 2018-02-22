package GTTT;

public class Algorithms {
	private static int maxDepth = 4;
	
	private Algorithms(){}
	
	public static void ABPMiniMax(Board board){
		ABPMiniMax.run(board.getPlayer(), board, maxDepth);
		
	}

}
