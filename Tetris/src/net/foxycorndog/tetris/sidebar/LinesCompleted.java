package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * Class that displays and keeps track of how many
 * lines have been completed.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	May 14, 2013 at 9:50:40 AM
 * @since	v0.1
 * @version	May 14, 2013 at 9:50:40 AM
 * @version	v1.0
 */
public class LinesCompleted implements BoardListener
{
	private	int	x, y;
	private	int lines;
	
	/**
	 * creates a linesCompleted and displays it in the sideBar
	 */
	public LinesCompleted()
	{
		lines = 0;
	}
	
	/**
	 * Set the new location of the LinesCompleted.
	 * 
	 * @param x The new horizontal location of the LinesCompleted.
	 * @param y The new vertical location of the LinesCompleted.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * renders the amount of lines completed
	 */
	public void render()
	{
		float scale = 0.5f;
		Tetris.getFont().render("lines " + lines, x, y, 0, scale, null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onPieceMove(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onPieceMove(BoardEvent event)
	{
		
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onLineCompleted(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onLineCompleted(BoardEvent event)
	{
		lines += event.getLines();
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onGameLost(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onGameLost(BoardEvent event)
	{
		
	}
}
