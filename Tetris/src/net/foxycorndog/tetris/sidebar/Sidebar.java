package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class that holds everything in the sidebar and renders it
 * too.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:43:29 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v1.0
 */
public class Sidebar
{
	private	int				x, y;
	private	int				width, height;
	
	private	float			scaledWidth, scaledHeight;
	
	private	NextPiece		nextPiece;
	private	Scoreboard		scoreBoard;
	private	LinesCompleted	linesCompleted;

	/**
	 * creates a sidebar
	 */
	public Sidebar()
	{
		nextPiece = new NextPiece();
		nextPiece.setLocation(0, 130);
		nextPiece.setScale(2);
		
		scoreBoard = new Scoreboard();
		scoreBoard.setLocation(0, 70);
		
		linesCompleted = new LinesCompleted();
		linesCompleted.setLocation(0, 0);
		
		width = nextPiece.getWidth();
	}
	
	/**
	 * Set the new location of the Sidebar.
	 * 
	 * @param x The new horizontal location of the Sidebar.
	 * @param y The new vertical location of the Sidebar.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the width of the Sidebar.
	 * 
	 * @return The width of the Sidebar.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Get the height of the Sidebar.
	 * 
	 * @return The height of the Sidebar.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Get the width of the Sidebar after scaling.
	 * 
	 * @return The width of the Sidebar after scaling.
	 */
	public float getScaledWidth()
	{
		return nextPiece.getScaledWidth();
	}

	/**
	 * Get the height of the Sidebar after scaling.
	 * 
	 * @return The height of the Sidebar after scaling.
	 */
	public float getScaledHeight()
	{
		return nextPiece.getScaledHeight();
	}

	/**
	 * Get the NextPiece part of the Sidebar.
	 * 
	 * @return The NextPiece part of the Sidebar.
	 */
	public NextPiece getNextPiece()
	{
		return nextPiece;
	}

	/**
	 * Get the Scoreboard part of the Sidebar.
	 * 
	 * @return The Scoreboard part of the Sidebar.
	 */
	public Scoreboard getScoreboard()
	{
		return scoreBoard;
	}

	/**
	 * Get the LinesCompleted part of the Sidebar.
	 * 
	 * @return The LinesCompleted part of the Sidebar.
	 */
	public LinesCompleted getLinesCompleted()
	{
		return linesCompleted;
	}
	
	/**
	 * renders the image of the Sidebar(displays lines completed, score, and the
	 * next piece to enter gameplay)
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			GL.translate(x, y, 0);
			
			scoreBoard.render();
			nextPiece.render();
			linesCompleted.render();
		}
		GL.popMatrix();
	}
}