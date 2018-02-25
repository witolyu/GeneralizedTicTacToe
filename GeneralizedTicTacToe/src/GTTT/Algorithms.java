package GTTT;


public class Algorithms {
	//public int size = 3;
	
	//total_player = 3 means both player 1 and 2 are AI, = 2 means player 2 only, =1 means player 1 only
	public static int total_player = 3;
	
	public static int maxDepth[] = new int[] {3,3};
	public static double player1Bonus[] = new double[] {1.0,1.0};
	public static double player2Bonus[] = new double[] {1.0,1.0};
	public static double connectBonus[] = new double[] {1.0,1.0};
	public static boolean randomBestMove[] = new boolean[] {true,true};	
	
	//0=exhaustive search, 1=make move within the winning row search, assume rival move within the winning row search 
	//2=within the winning half size of winning row, assume rival move within the winning row search 
	//3=
	public static int searchMode[] = new int[] {1,1};
	public static int rivalSearchMode[] = new int[] {1,1};
	
	public Algorithms(){
	}
	
	public static void setParameters(int total_player, int maxDepth_1,double player1Bonus_1, double player2Bonus_1, double connectBonus_1,int searchMode_1, int rivalSearchMode_1, boolean randomBestMove_1,
				int maxDepth_2,double player1Bonus_2, double player2Bonus_2, double connectBonus_2,int searchMode_2, int rivalSearchMode_2, boolean randomBestMove_2){
		Algorithms.total_player = total_player;
		
		Algorithms.maxDepth[0] = maxDepth_1;
		Algorithms.player1Bonus[0] = player1Bonus_1;
		Algorithms.player2Bonus[0] = player2Bonus_1;
		Algorithms.connectBonus[0] = connectBonus_1;
		Algorithms.searchMode[0] = searchMode_1;
		Algorithms.rivalSearchMode[0] = rivalSearchMode_1;
		Algorithms.randomBestMove[0] = randomBestMove_1;
		
		Algorithms.maxDepth[1] = maxDepth_2;
		Algorithms.player1Bonus[1] = player1Bonus_2;
		Algorithms.player2Bonus[1] = player2Bonus_2;
		Algorithms.connectBonus[1] = connectBonus_2;
		Algorithms.searchMode[1] = searchMode_2;
		Algorithms.rivalSearchMode[1] = rivalSearchMode_2;
		Algorithms.randomBestMove[1] = randomBestMove_2;
	}
}
