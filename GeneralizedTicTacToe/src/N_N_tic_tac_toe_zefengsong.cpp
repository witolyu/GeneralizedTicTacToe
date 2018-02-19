#include <iostream>
#include<vector>
using namespace std;

int N = 0;

/* Check if the current player wins */
int checkWin(vector<vector<int>>& gameBoard, int row, int col, int player, int M) {
	// Check vertical
	int count_vertical = 1;
	int row_up = row - 1;
	while (row_up >= 0 && gameBoard[row_up][col] == player) count_vertical++, row_up--;
	int row_down = row + 1;
	while (row_down < N && gameBoard[row_down][col] == player) count_vertical++, row_down++;

	// Check horizontal
	int count_horizontal = 1;
	int col_left = col - 1;
	while (col_left >= 0 && gameBoard[row][col_left] == player) count_horizontal++, col_left--;
	int col_right = col + 1;
	while (col_right < N && gameBoard[row][col_right] == player) count_horizontal++, col_right++;

	// Check diagonal
	int count_diagonal_left = 1;
	int row_left_up = row - 1, col_left_up = col - 1;
	while (row_left_up >= 0 && col_left_up >= 0 && gameBoard[row_left_up][col_left_up] == player) count_diagonal_left++, row_left_up--, col_left_up--;
	int row_right_down = row + 1, col_right_down = col + 1;
	while (row_right_down < N && col_right_down < N && gameBoard[row_right_down][col_right_down] == player) count_diagonal_left++, row_right_down++, col_right_down++;


	int count_diagonal_right = 1;
	int row_left_down = row + 1, col_left_down = col - 1;
	while (row_left_down < N && col_left_down >= 0 && gameBoard[row_left_down][col_left_down] == player) count_diagonal_right++, row_left_down++, col_left_down--;
	int row_right_up = row - 1, col_right_up = col + 1;
	while (row_right_up >= 0 && col_right_up < N && gameBoard[row_right_up][col_right_up] == player) count_diagonal_right++, row_right_up--, col_right_up++;

	return (count_vertical >= M || count_horizontal >= M || count_diagonal_left >= M || count_diagonal_right >= M) ? player : 0;
}

/* Make a move and check for wins */
int move(vector<vector<int>>& gameBoard, int row, int col, int player, int M) {
	gameBoard[row][col] = player;
	return checkWin(gameBoard, row, col, player, M);
}

/* Print the game board */
void printBoard(vector<vector<int>>& board) {
	for (auto x : board) {
		for (auto y : x) cout << (y ? y == 1 ? 'X' : 'O' : '#') << " ";
		cout << endl;
	}
}

/* 
 * Let's play!
 *  1 represents for player 1 
 * -1 represents for player 2
 * Each player makes a move and leave the next round to the opponent
 *
 * return: 0 - draw
 *         1 - player 1 wins
 *        -1 - player 2 wins
 */
int solve(vector<vector<int>>& gameBoard, vector<vector<vector<int>>>& record, int player, int count, int M) {
	//cout << "round=" << count << endl;
	record[count] = gameBoard;
	if (count == N * N) return 0;
	count++;

	// If win right away, then return that result!
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			if (!gameBoard[i][j]) {
				int result = move(gameBoard, i, j, player, M);
				gameBoard[i][j] = 0;
				if (result) return player;
			}
		}
	}

	// Try to make a move
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			if (!gameBoard[i][j]) {
				// Make a move and recursively call as the opponent, get the final result
				move(gameBoard, i, j, player, M);
				int finalResult = solve(gameBoard, record, -player, count, M);
				// If the opponent wins, that's a 'bad' move, undo move and continue to find a better move
				if (finalResult == -player) {
					gameBoard[i][j] = 0;
				}
				// If current player wins, return that result!
				else if (finalResult == player) {
					gameBoard[i][j] = 0;
					return player;
				}
				// Draw
				else return 0;
			}
		}
	}
	// The current player has no move to make, the opponent wins
	return -player;
}


int main() {
	// cout << "Choose a start position 1 ~ "<< N * N << ":" << endl;
	// int pos = 0;
	// cin >> pos;
	// gameBoard[(pos - 1) / N][(pos - 1) % N] = 1;
	// record[1] = gameBoard;

	int M;
	cout << "Enter board size N:" << endl;
	cin >> N;
	cout << "Enter win condition M:" << endl;
	cin >> M;

	vector<vector<int>>gameBoard(N, vector<int>(N, 0));
	vector<vector<vector<int>>>record(N * N + 1, vector<vector<int>>(N, vector<int>(N, 0)));

	solve(gameBoard, record, 1, 0, M);
	for (int i = 0; i < N * N + 1; i++) {
		cout << "Round " << i << ":" << endl;
		printBoard(record[i]);
	}
	cout << "Draw!" << endl;
	system("pause");
}
