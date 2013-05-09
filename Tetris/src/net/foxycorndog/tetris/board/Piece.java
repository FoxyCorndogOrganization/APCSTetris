package net.foxycorndog.tetris.board;

/**
 * Class used to hold information for each Piece in the Tetris
 * game. There are also methods to manipulate the data and
 * render the data to the screen.
 * 
 * @author	Braden Steffaniak
 * @since	May 6, 2013 at 3:36:36 PM
 * @since	v0.1
 * @version	May 6, 2013 at 3:36:36 PM
 * @version	v0.1
 */
public class Piece extends AbstractPiece
{
	/**
	 * Create a Tetris Piece given the specified matrix, and
	 * matrix width. The matrix is described with values
	 * that correspond to the rgb of the square located at the
	 * index.
	 * 
	 * eg:
	 * Color c = new
	 *         net.foxycorndog.tetris.board.Color(200, 0, 0);<br>
	 * 
	 * new Piece([ c, c,<br>
	 *             c, c ], 2);<br>
	 * <br>
	 * would create a red square Piece, and:<br>
	 * 
	 * new Piece([ c,<br>
	 * 			   c,<br>
	 *             c,<br>
	 * 			   c ], 1);<br>
	 * <br>
	 * would create a red long Piece.
	 * 
	 * @param matrix The array of Colors describing the Piece.
	 * @param width The width of the Piece.
	 */
	public Piece(Color[] matrix, int width)
	{
		super(matrix, width);
	}
	
	/**
	 * @see net.foxycorndog.tetris.board.AbstractPiece#rotateClockwise()
	 */
	public void rotateClockwise()
	{
		
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractPiece#rotateCounterClockwise()
	 */
	public void rotateCounterClockwise()
	{
		
	}
}
