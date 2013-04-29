package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.tetris.Tetris;

public class LinesCompleted
{
	int    lines;
	int x;
	int y;
	
	public LinesCompleted(int x, int y)
	{
		this.x = x;
		this.y = y;
		lines = 0;
	}
	
	public void render()
	{
		float scale = (float) 1;
		Tetris.getFont().render("lines " + lines, x, y + 175, (float)0.0, (float) (scale), null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}
}
