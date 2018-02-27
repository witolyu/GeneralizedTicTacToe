package GTTT;

import java.util.Set;

public class main {

	public static void main(String[] args) {

		//AIvsAI
		//Set up parameters
		int total_player=3;
		
		int maxDepth_1=3;
		double player1Bonus_1=1.0;
		double player2Bonus_1=1.2;
		double connectBonus_1=2;
		int searchMode_1=1;
		int rivalSearchMode_1=1;
		boolean randomBestMove_1 = false;
		
		int maxDepth_2=3;
		double player1Bonus_2=1.2;
		double player2Bonus_2=1.0;
		double connectBonus_2=2;
		int searchMode_2=1;
		int rivalSearchMode_2=1;
		boolean randomBestMove_2 = false;

		Algorithms.setParameters(total_player, maxDepth_1, player1Bonus_1, player2Bonus_1, connectBonus_1, searchMode_1,rivalSearchMode_1, randomBestMove_1, 
				maxDepth_2, player1Bonus_2, player2Bonus_2, connectBonus_2, searchMode_2,rivalSearchMode_2, randomBestMove_2);
		
		//Set up games
	    Console game = new Console(15,5,Algorithms.searchMode,Algorithms.rivalSearchMode);
	    game.AIvsAI();

	}

}
