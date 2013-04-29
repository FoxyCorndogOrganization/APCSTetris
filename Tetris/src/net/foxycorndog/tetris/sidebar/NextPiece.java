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
	
	public NextPiece()
	{
		stackOPieces = new ArrayList<Piece>();
		
		addNewPiece();
	}
	
	public void addNewPiece()
	{
		Piece piece = Piece.getRandomPiece();
		stackOPieces.add(piece);
	}
	
	public void render()
	{
		int x = 400;
		int y = 500;
		float scale = 3;
		GL.translate(x, y, 0);
		GL.scale(scale, scale, 1);
		
		stackOPieces.get(0).render();
		
		GL.scale(1/scale, 1/scale, 1);
		GL.translate(-x, -y, 0);
	}
}