package net.foxycorndog.tetris.event;

/**
 * Class used to describe what happened in a Board Listener.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 6:38:19 AM
 * @since	v0.1
 * @version	Apr 26, 2013 at 6:38:19 AM
 * @version	v0.1
 */
public class BoardEvent extends Event
{
 	private int col, row;
 	
	/**
	 * Create a BoardEvent at the specified column and row on the
	 * Board.
	 * 
	 * @param col The column that the Even has taken place.
	 * @param row The row that the Even has taken place.
	 */
	public BoardEvent(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
	
	/**
	 * Get the column that the Even has taken place.
	 */
	public int getCol()
	{
		return col;
	}

	/**
	 * Get the row that the Even has taken place.
	 */
	public int getRow()
	{
		return row;
	}
}
