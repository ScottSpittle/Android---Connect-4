package scottspittle.connect4;

public class Player {

	//globals
	private static String Name;
	private static byte Cell;
	
	public Player(String playerName, byte playerCell)
	{
		Name = playerName;
		Cell = playerCell;
	}
	
	public byte getCell()
	{
		return Cell;
	}
	
	public String getName()
	{
		return Name;
	}
}
