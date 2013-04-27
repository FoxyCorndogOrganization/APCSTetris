package net.foxycorndog.tetris.sidebar;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:43:29 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 4:43:29 PM
 * @version	v0.1
 */
public class Sidebar
{
	NextPiece nP;
	ScoreBoard sB;
	public Sidebar()
	{
		nP = new NextPiece();
		sB = new ScoreBoard();
	}
	
	public void render()
	{
		nP.render();
	}
}