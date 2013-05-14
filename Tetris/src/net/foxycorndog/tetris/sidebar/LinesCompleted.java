package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

public class LinesCompleted implements BoardListener
{
	int lines;
	int x;
	int y;
	
	/**
	 * creates a linesCompleted and displays it in the sideBar
	 */
	public LinesCompleted(int x, int y)
	{
		this.x = x;
		this.y = y;
		lines = 0;
	}
	
	/**
	 * renders the amount of lines completed
	 */
	public void render()
	{
		int xC = 0;
		int yC = 150;
		float scale = 0.5f;
		Tetris.getFont().render("lines " + lines, x + xC, y + yC, 0, scale, null);
		
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
	 * @see net.foxycorndog.tetris.event.BoardListener#onLineDeleted(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onLineDeleted(BoardEvent event)
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
