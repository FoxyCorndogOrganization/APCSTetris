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
public class SideBar
{
	NextPiece      nP;
	Scoreboard     sB;
	LinesCompleted lC;
	
	public SideBar()
	{
		int x = 300;
		int y = 300;
		nP = new NextPiece(x,y);
		sB = new Scoreboard(x,y);
		lC = new LinesCompleted(x,y);
	}
	
	public void render()
	{
		sB.render();
		nP.render();
		lC.render();
	}
}