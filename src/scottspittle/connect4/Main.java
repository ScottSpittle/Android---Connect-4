package scottspittle.connect4;

public class Main {
	//Globals (no const in java, Final = const)
	private byte[][] board;
	public static final int BOARD_WIDTH = 7; //more optimised than using properties
	public static final int BOARD_HEIGHT = 6;
	
	public static final byte EMPTY_CELL = 0;
	public static final byte P1 = 1;
	public static final byte P2 = 2;
	public Player player1 = new Player("Scott Spittle", (byte) 1);
	public Player player2 = new Player("Josh Boyce", (byte) 2);
	
	private byte playerTurn = (byte) 1;
	private byte playerWinner = EMPTY_CELL;
	private static final int WIN_SCORE = 4;
	
	//Constructor
	public Main()
	{
		board = new byte[BOARD_WIDTH][BOARD_HEIGHT]; //Initialise 2D array
		
		//Initialise array
		for(int i = 0; i < BOARD_WIDTH; i++)
			for(int j = 0; j < BOARD_HEIGHT; j++)
				board[i][j] = EMPTY_CELL;
	}
	
	//returns the identifier for that slot.
	public int getSlotInfo(int x, int y)
	{
		return board[x][y];
	}
	
	public void DropCounter(int col)
	{
		boolean won = false;
		if(board[col][0] == EMPTY_CELL)
			for(int i = BOARD_HEIGHT - 1; i >= 0; i--)
				if(board[col][i] == EMPTY_CELL)
				{
					board[col][i] = playerTurn; //player;
					won = IsConnectFour(col, i);
					break; //break out of the for loop when done.
				}
		
		//change players turn
		if(won)
			playerWinner = playerTurn;
		else
			playerTurn = playerTurn == player1.getCell() ? player1.getCell() : player2.getCell();
		
		return;
	}
	
	public byte GetWinner()
	{
		return playerWinner;
	}
	
	public byte getWhosTurn()
	{
		return playerTurn;
	}
	
	//check for a connect four!
	private boolean IsConnectFour(int col, int row)
	{
		//block Coordinates
		byte color = board[col][row];
		
		if (WinCheckHorizontal(color, col, row))
			return true;
		else if (WinCheckVertical(color, col, row))
			return true;
		else if (WinCheckTopRight(color, col, row))
			return true;
		else if (WinCheckBottomRight(color, col, row))
			return true;
		
		return false;
	}

	//horizontal Checking
	private boolean WinCheckHorizontal(byte color, int col, int row)
	{
		int count = 1; //counter to start at 1 for the block they touch
		int sx = col-1; //col to manipulate
		
		
		while(sx > 0 && (board[sx][row] == color))
		{
			count++;
			sx--;
		}
		
		sx = col+1;
		
		//increment count for each coloured block on its right
		while(sx < BOARD_WIDTH && (board[sx][row] == color))
		{
			count++;
			sx++;
		}
		
		//check if win after the loop has finished.
		if(count >= WIN_SCORE)
			return true;
		
		return false;
	}

	//Vertical checking
	private boolean WinCheckVertical(byte color, int col, int row)
	{
		int count = 1; //counter to start at 1 for the block they touch
		int sy = row-1; //row to manipulate
		
		//get most top square by this play from the point they touched
		while(sy > 0 && (board[col][sy] == color))
		{
			sy--;
			count++;
		}
		
		sy = row+1;

		//increment count for each coloured block beneath it
		while(sy < BOARD_HEIGHT && (board[col][sy] == color))
		{
			count++;
			sy++;
		}
		
		//check if win after the loop has finished.
		if(count >= WIN_SCORE)
			return true;
		
		return false;
	}

	//vertical Bottom Left checking
	private boolean WinCheckTopRight(byte color, int col, int row)
	{
		int count = 1; //counter to start at 1 for the block they touch
		int sx = col; //col to manipulate
		int sy = row; //row to manipulate

		//get most bottom, left square by this play from the point they touched
		while(sx > 0 && sy < (BOARD_HEIGHT - 1) &&(board[sx-1][sy+1] == color))
		{
			count++;
			sx--;
			sy++;
		}
		
		sx = col+1;
		sy = row+1;

		//increment count for each coloured block beneath it to the left
		while(sx > 0 && sy < (BOARD_HEIGHT - 1) && (board[sx][sy] == color))
		{
			count++;
			sx--;
			sy++;
		}
		
		//check if win after the loop has finished.
		if(count >= WIN_SCORE)
			return true;
		
		return false;
	}

	//vertical Bottom Right checking
	private boolean WinCheckBottomRight(byte color, int col, int row)
	{
		int count = 1; //counter to start at 1 for the block they touch
		int sx = col; //col to manipulate
		int sy = row; //row to manipulate

		//get most bottom, right square by this play from the point they touched
		while(sx < (BOARD_WIDTH - 1) && sy < (BOARD_HEIGHT - 1) && (board[sx+1][sy+1] == color))
		{
			count++;
			sx++;
			sy++;
		}

		sx = col-1;
		sy = row-1;

		//increment count for each coloured block beneath it to the right
		while(sx < (BOARD_WIDTH - 1) && sy < (BOARD_HEIGHT - 1) && (board[sx][sy] == color))
		{
			count++;
			sx--;
			sy--;
		}
		
		//check if win after the loop has finished.
		if(count >= WIN_SCORE)
			return true;
		
		return false;
	}
}
