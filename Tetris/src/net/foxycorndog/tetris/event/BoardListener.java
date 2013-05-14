package net.foxycorndog.tetris.event;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	May 13, 2013 at 4:48:58 PM
 * @since	v0.1
 * @version	May 13, 2013 at 4:48:58 PM
 * @version	v0.1
 */
public interface BoardListener
{
	public void onPieceMove(BoardEvent event);
	public void onLineDeleted(BoardEvent event);
	public void onGameLost(BoardEvent event);
}
