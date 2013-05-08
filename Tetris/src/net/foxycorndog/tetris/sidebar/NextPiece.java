package net.foxycorndog.tetris.sidebar;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.board.AbstractPiece;

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
	ArrayList<AbstractPiece> stackOPieces;
	int x;
	int y;
	
	public NextPiece(int x, int y)
	{
		this.x = x;
		this.y = y;
		stackOPieces = new ArrayList<AbstractPiece>();
		
		addNewPiece();
	}
	
	public void addNewPiece()
	{
		AbstractPiece piece = AbstractPiece.getRandomPiece();
		stackOPieces.add(piece);
	}
	
	public void render()
	{
		int xC = 50;
		int yC = 50;
		float scale = 3;
		GL.translate(xC + x, yC + y, 0);
		GL.scale(scale, scale, 1);
		
		stackOPieces.get(0).render();
		
		GL.scale(1/scale, 1/scale, 1);
		GL.translate(-x - xC, -y - yC, 0);
	}
}