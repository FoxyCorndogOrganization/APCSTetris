package net.foxycorndog.tetris.sidebar;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.tetris.board.Piece;

/**
 * 
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:55:17 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 4:55:17 PM
 * @version	v0.1
 */
public class NextPiece
{
	ArrayList<Piece> stackOPieces;
	int x;
	int y;
	
	/**
	 * creates the next piece to enter the game and displays it in the sideBar
	 */
	public NextPiece(int x, int y)
	{
		this.x = x;
		this.y = y;
		stackOPieces = new ArrayList<Piece>();
		
		addNewPiece();
	}
	
	/**
	 * adds a new piece to the stackOfPieces(the queued pieces that will go next)
	 */
	public void addNewPiece()
	{
		Piece piece = Piece.getRandomPiece();
		stackOPieces.add(piece);
	}
	
	/**
	 * renders the image of the next piece
	 */
	public void render()
	{
		int xC = 70;
		int yC = 300;
		float scale = 3;
		GL.translate(xC + x, yC + y, 0);
		GL.scale(scale, scale, 1);
		
		stackOPieces.get(0).render();
		
		GL.scale(1/scale, 1/scale, 1);
		GL.translate(-x - xC, -y - yC, 0);
	}
}