package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * Class that keeps track of and displays the score of the
 * Tetris game.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:54:12 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v1.0
 */
public class Scoreboard implements BoardListener
{
	private	int	x, y;
	private int score;
	
	/**
	 * creates the scoreBoard and displays it in the sideBar
	 */
	public Scoreboard()
	{
		score = 0;
	}
	
	/**
	 * Set the new location of the Scoreboard.
	 * 
	 * @param x The new horizontal location of the Scoreboard.
	 * @param y The new vertical location of the Scoreboard.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * renders the image of the score
	 */
	public void render()
	{
		float scale = 0.5f;
		Tetris.getFont().render("Score: " + score, x, y, 0, scale, null);
		
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
		int lines = event.getLines();
		
		if (lines == 1)
		{
			score += 100;
		}
		else if (lines == 2)
		{
			score += 300;
		}
		else if (lines == 3)
		{
			score += 500;
		}
		else if (lines == 4)
		{
			score += 1200;
		}
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onGameLost(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onGameLost(BoardEvent event)
	{
		
	}
}