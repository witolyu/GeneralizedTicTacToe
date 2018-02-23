package GTTT;
import java.util.Date;
import java.util.Scanner;

public class Console {

	private Board board;
	
	public Console(int boardSize, int winningRowSize){
		board = new Board(boardSize, winningRowSize,1);
	}
	
	public void AIvsAI(){
		Date date = new Date();
		System.out.println("Game start! AI 1 vs AI 2!");
		board.PrintBoardLite();
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
	
	public void AIvsPlayer(){
		Date date = new Date();
		System.out.println("Game start! AI 1 vs AI 2!");
		board.PrintBoardLite();
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
			PlayerMove();
			board.PrintBoardLite();
		}	
	}
	
	public void PlayervsAI(){
		Date date = new Date();
		System.out.println("Game start! AI 1 vs AI 2!");
		board.PrintBoardLite();
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
			PlayerMove();
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
	
	public void PlayerMove(){
		int player = board.getPlayer();
		int x,y;
		String[] temp;
		
		// loop error check for the input, whether it is a solid move?
		while (true)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.printf("Player%d, please indicate your move by row,colomn, for example 5,7 means row 5 and column 7:\n", player);
			String input = keyboard.nextLine();
			temp = input.split(",");
			x = Integer.parseInt(temp[0])-1;
			y = Integer.parseInt(temp[1])-1;
			
			if (x < board.getSize() && y < board.getSize() && board.checkEmptyMove(x, y))
				break;
			System.out.printf("Player%d, that isn't a valid move, please choose again.\n", player);
		}
		
		board.move(x, y);
		
	}
	
    public static void main(String[] args) {
        Console game = new Console(15,5);
        //game.AIvsAI();
        game.PlayervsAI();
    }
}
