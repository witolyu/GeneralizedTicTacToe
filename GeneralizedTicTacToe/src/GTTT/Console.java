package GTTT;
import java.util.Date;

public class Console {

	private Board board;
	
	public Console(int boardSize, int winningRowSize){
		board = new Board(boardSize, winningRowSize,1);
	}
	
	public void AI2AI(){
		Date date = new Date();
		System.out.println("Game start! AI 1 vs AI 2!");
		while(true)
		{
			System.out.printf("**********%s**********\n",date.toString());
			if (board.isGameOver())
			{
				if (board.getWinner() == 2){
					System.out.println("AI 2 win!");
				}else{
					System.out.println("This is a draw!");
				}
				break;
			}
			AIMove();
			board.PrintBoardLite();
			System.out.printf("**********%s**********\n",date.toString());
			if (board.isGameOver())
			{
				if (board.getWinner()==1)
				{
					System.out.println("AI 1 win!");
				}else{
					System.out.println("This is a draw!");
				}
				break;
			}
			AIMove();
			board.PrintBoardLite();

		}	
	}
	
	// Lack optimization (Exhaustive search, lacks depth limitation and heuristic value for larger size board)
	public void AIMove()
	{
		if (board.getMoveCount() == 0)
		{
			board.move(board.getSize()/2,board.getSize()/2);
		}else{
			Algorithms.ABPMiniMax(board);			
		}
	}
	
    public static void main(String[] args) {
        Console game = new Console(15,5);
        game.AI2AI();
    }
}
