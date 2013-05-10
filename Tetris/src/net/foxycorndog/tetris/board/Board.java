package net.foxycorndog.tetris.board;

import java.util.ArrayList;

import net.foxycorndog.tetris.event.BoardListener;


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
	private	Piece	currentPiece;
	
	private boolean lost;
	private int[] 	deleteRows;
	private ArrayList<BoardListener> events;
	
	
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
		
		events = new ArrayList<BoardListener>();
		deleteRows = new int[4];
		
		KeyListener listener = new KeyListener()
		{
			
		};
		
		Keyboard.addKeyListener(listener);
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#tick()
	 * Moves a piece down one space after half a second until the piece
	 * hits the bottom of the board or another piece on the board.
	 */
	public void tick()
	{
		if(!getLost())
		{
			if(yallHitTheBottomBaby())
			{
				currentPiece.kill();
				currentPiece = new Piece( new Color[5],(int)Math.random()*7);
				if(!isValid(currentPiece.getX(),currentPiece.getY()))
				{
					if(currentPiece.getX()==super.getHeight())
					{
						for (BoardListener listener : events)
						{
							listener.onGameLost(null);
						}
					}
				}
				clearRows();
			}
		}
	}
	
	public boolean yallHitTheBottomBaby()
	{
		if(isValid(currentPiece.getX(), currentPiece.getY()))
			lost = false;
		else 
			lost = true;
		
		return lost;
	}
	
	public void setLost(boolean l)
	{
		lost = l;
	}
	
	public boolean getLost()
	{
		return lost;
	}
	
	/**
	 * Coordinates use the Cartesian system.
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int)
	 * checks to see it the coordinate (x,y) is valid for the piece to
	 * move to.
	 */
	public boolean isValid(int x, int y)
	{
		
		if((x >= 0 && x < super.getWidth())&& y >=0 && y < super.getHeight())
		{
			return true;
		}
		
		return false;
	}
	
	public void clearRows()
	{
		int counter = 0;
		for(int r = 0; r < super.getHeight()-1;r++)
			for(int c = 0; c < super.getWidth()-1;c++)
			{
				if(!isValid(r,c))
				{
					//TODO when is counter reset?
					
					counter++;
				}
				if(counter==getWidth())
				{
					for(int dRow =0; dRow < super.getHeight()-1; dRow++)
					{
						Location l = new Location(dRow,c);
						
						get(l).deleteSquare(l);
					}
				}
			}
	}
	
	public void addListener(BoardListener b)
	{
		events.add(b);
	}
}
