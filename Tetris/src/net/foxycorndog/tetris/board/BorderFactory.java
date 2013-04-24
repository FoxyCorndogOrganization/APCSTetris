package net.foxycorndog.tetris.board;

import java.util.ArrayList;

public class BorderFactory
{
	public static Piece[] createBorder(int borderSize, int width, int height, boolean flat)
	{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		int size = Piece.getSegmentSize();
		
		if (width % size != 0)
		{
			width += size;
		}
		if (height % size != 0)
		{
			height += size;
		}
		
		int y = 0;
		int x = 0;
		
		while (y < height)
		{
			Piece piece = null;
			
			while (x < width)
			{
				piece = Piece.getRandomPiece();
				
				piece.setLocation(x, y);
				
				pieces.add(piece);
				
				x += piece.getWidth() * size;
			}
			
			x = 0;
			
			y += piece.getHeight() * size;
		}
		
		return pieces.toArray(new Piece[0]);
	}
}