package net.foxycorndog.tetris.board;

/**
 * Class that holds the information for the Pieces in the Tetris game,
 * as well as demonstrating the interactions of the Pieces.
 * 
 * @author	Jeremiah Blackburn
 * @author	Braden Steffaniak
 * @since	May 6, 2013 at 3:31:08 PM
 * @since	v0.1
 * @version	May 6, 2013 at 3:31:08 PM
 * @version	v0.1
 */
public class Board extends AbstractBoard
{
	/**
	 * Instantiate the image for the Board as well as other
	 * instantiations.
	 * 
	 * @param width The number of horizontal grid spaces the Board will
	 * 		contain.
	 * @param height The number of vertical grid spaces the Board will
	 * 		contain.
	 * @param gridSpaceSize The size (in pixels) that each space on the
	 * 		Board will take up. eg: passing 10 would create 10x10 grid
	 * 		spaces across the board.
	 */
	public Board(int width, int height, int gridSpaceSize)
	{
		super(width, height, gridSpaceSize);
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#tick()
	 */
	public void tick()
	{
		
	}
<<<<<<< HEAD

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int)
	 */
	public boolean isValid(int x, int y)
	{
		
		
		return false;
	}
}
=======
}
>>>>>>> 64924c2cd448a93cac457c4713474c71341024d5
