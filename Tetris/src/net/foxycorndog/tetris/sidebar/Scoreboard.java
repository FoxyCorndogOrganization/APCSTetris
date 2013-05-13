package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * 
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:54:12 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v0.1
 */
public class Scoreboard implements BoardListener
{
	private int score;
	private int x, y;
	
	/**
	 * creates the scoreBoard and displays it in the sideBar
	 */
	public Scoreboard(int x, int y)
	{
		this.x = x;
		this.y = y;
		score = 0;
	}
	
	/**
	 * renders the image of the score
	 */
	public void render()
	{
		int xC = 0;
		int yC = 250;
		float scale = 0.5f;
		Tetris.getFont().render("score " + score, x + xC, y + yC, 0, scale, null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}

	public void onGameLost(BoardEvent event)
	{
		
	}

	public void onLineDeleted(BoardEvent event)
	{
		score += 5;
	}

	public void onPieceMove(BoardEvent event)
	{
		
	}
}