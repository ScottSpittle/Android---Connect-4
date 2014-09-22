package mmt.connect4;

public class GameInfo {
	// This class will contain our game logic and data structures
	public static final int SLOTS_ACROSS = 7;
	public static final int SLOTS_DOWN = 6;

	// Constants to define what each slots represents
	public static final byte EMPTY_CELL = 0;
	public static final byte PLAYING = 1;
	public static final byte WON = 2;
	public static final byte DRAW = 3;
	public static final byte GAMESTATEMASK = 0x7;
	
	public static Player Player1 = new Player("Scott Spittle", (byte)8);
	public static Player Player2 = new Player("Josh Boyce", (byte)16);
	

	// 2D array to store our board information
	private byte[][] board;

	// Whose turn is it - uses the P1_CELL and P2_CELL constants for player 1
	// and player 2
	private byte whoseturn;

	// Has anyone won? - uses the P1_CELL, P2_CELL and EMPTY_CELL constants
	private byte winner;
	
	private int counter = 0;

	public GameInfo() {
		// create memory for 2D array and assign each slot the empty constant
		board = new byte[SLOTS_ACROSS][SLOTS_DOWN];
		for (int i = 0; i < SLOTS_ACROSS; i++)
			for (int j = 0; j < SLOTS_DOWN; j++)
				board[i][j] = EMPTY_CELL;

		// initially set player 1 who starts first
		whoseturn = Player1.GetCell();
		// set no-one to be the winner at this stage
		winner = EMPTY_CELL;
	}

	// Returns the identifier for a given position in the grid
	public int GetSlotInfo(int x, int y) {
		// we could check that x and y are in range here, but meh...
		return board[x][y];
	}

	// Expose the winner variable to the outside world
	public byte GetGameState() {
		byte result = 0;
		
		if(winner == EMPTY_CELL)
		{
			if(counter == (SLOTS_ACROSS * SLOTS_DOWN))
				result = DRAW;
			else
				result = (byte) (whoseturn | PLAYING);
		}
		else
			result = (byte) (winner | WON);
		
		return result;
	}

	// Expose the whosturn variable to the outside world
	public byte GetWhoseTurn() {
		return whoseturn;
	}

	// Attempts to drop a counter into the column provided using the held
	// player's turn info
	public int DropCounter(int column) {
		int res = -1;
		// is there space to actually put a counter in the column? look to see
		// if the top row in that column is empty
		if (column < SLOTS_ACROSS && board[column][0] == EMPTY_CELL) {
			boolean won = false;
			// There is space but need to find the first empty slot to put the
			// counter starting from
			// the bottom of the 2d array
			for (int j = SLOTS_DOWN - 1; j >= 0; --j) {
				++counter;
				if (board[column][j] == EMPTY_CELL) {
					// first empty slot found to set it to hold the current
					// player's counter piece
					board[column][j] = whoseturn;
					// Check if we have a connect 4
					won = IsConnect4(column, j);
					res = j;

					j = 0; // exit out of the for loop - could also use break;
							// here too
				}
			}

			// if the game has been won then update the won variable else switch
			// turns
			if (won)
				winner = whoseturn;
			else {
				// swap around who's turn it is
				whoseturn = (whoseturn == Player1.GetCell() ? Player2.GetCell() : Player1.GetCell());
			}
		}
		return res;
	}

	// Function to checks if there are 4 in a row around the counter just
	// entered at (col, row)
	private boolean IsConnect4(int col, int row) {
		byte colour = board[col][row];

		// check the horizontal
		int sx = col;
		int count = 1;
		while (sx > 0 && board[sx - 1][row] == colour) {
			--sx;
			++count;
		}

		sx = col + 1;
		while (sx < SLOTS_ACROSS && board[sx][row] == colour) {
			++count;
			++sx;
		}

		if (count >= 4)
			return true;

		// Check the vertical
		int sy = row;
		count = 1;
		while (sy > 0 && board[col][sy - 1] == colour) {
			--sy;
			++count;
		}

		sy = row + 1;
		while (sy < SLOTS_DOWN && board[col][sy] == colour) {
			++count;
			++sy;
		}

		if (count >= 4)
			return true;

		// Check Diagonal - two directions: up an left; up and right
		sx = col;
		sy = row;
		count = 1;
		while (sx > 0 && sy > 0 && board[sx - 1][sy - 1] == colour) {
			--sx;
			--sy;
			++count;
		}

		sx = col + 1;
		sy = row + 1;
		while (sx < SLOTS_ACROSS && sy < SLOTS_DOWN && board[sx][sy] == colour) {
			++sx;
			++sy;
			++count;
		}
		if (count >= 4)
			return true;

		// other diagonal
		sx = col;
		sy = row;
		count = 1;
		while (sx > 0 && sy < SLOTS_DOWN - 1 && board[sx - 1][sy + 1] == colour) {
			--sx;
			++sy;
			++count;
		}

		sx = col + 1;
		sy = row - 1;
		while (sx < SLOTS_ACROSS && sy >= 0 && board[sx][sy] == colour) {
			++sx;
			--sy;
			++count;
		}
		if (count >= 4)
			return true;

		return false;
	}
}
