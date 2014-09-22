package mmt.connect4;

public class Player {
	private String Name;
	private	int Score;
	private int CurrentGameMoveCount;
	private int LastGameMoveCount;
	private byte Cell;
	
	public Player(String name, byte cell)
	{
		this.Name = name;
		this.Cell = cell;
	}
	
	public String GetName()
	{
		return this.Name;
	}
	
	public int GetScore()
	{
		return this.Score;
	}
	
	public int GetLastGameMoveCount()
	{
		return this.LastGameMoveCount;
	}
	
	public int GetMoveCount()
	{
		return this.CurrentGameMoveCount;
	}
	
	public byte GetCell()
	{
		return this.Cell;
	}
}
