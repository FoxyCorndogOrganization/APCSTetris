package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.tetris.Tetris;

public class LinesCompleted
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
		int yC = 180;
		float scale = 0.5f;
		Tetris.getFont().render("lines " + lines, x + xC, y + yC, 0, scale, null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}
}
