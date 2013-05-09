package net.foxycorndog.tetris.board;

public class Location 
{
	private int x;
	private int y;
	
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public boolean equals(Location l)
	{
		return(x == l.getX() && y == l.getY());
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
