package net.foxycorndog.tetris.sidebar;

/**
 * 
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:43:29 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v0.1
 */
public class Sidebar
{
	NextPiece      nP;
	Scoreboard     sB;
	LinesCompleted lC;
	
	/**
	 * creates a sidebar
	 */
	public Sidebar()
	{
		int x = 310;
		int y = 290;
		nP = new NextPiece(x,y);
		sB = new Scoreboard(x,y);
		lC = new LinesCompleted(x,y);
	}
	
	/**
	 * renders the image of the sidebar(displays lines completed, score,
	 * and the next piece to enter gameplay)
	 */
	public void render()
	{
		sB.render();
		nP.render();
		lC.render();
	}
}