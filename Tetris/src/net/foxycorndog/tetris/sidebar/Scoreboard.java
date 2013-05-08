package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;

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
public class Scoreboard
{
	int    score;
	int x;
	int y;
	
	public Scoreboard(int x, int y)
	{
		this.x = x;
		this.y = y;
		score = 0;
	}
	
	public void render()
	{
		float scale = (float) 2;
		Tetris.getFont().render("score " + score, x, y + 250, (float)0.0, (float) (scale *.5), null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}
}