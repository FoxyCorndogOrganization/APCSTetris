package net.foxycorndog.tetris.board;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.tetris.Tetris;
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
	private boolean						lost;
	
	private	Piece						currentPiece;
	
	private BunchOSquares				boss;
	
	private	Tetris						tetris;
	
	private int[]						deleteRows;
	
	private ArrayList<BoardListener>	events;
	
	
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
	public Board(int width, int height, int gridSpaceSize, Tetris tetris)
	{
		super(width, height, gridSpaceSize);
		
		this.tetris = tetris;
		
		events = new ArrayList<BoardListener>();
		deleteRows = new int[4];
		boss = new BunchOSquares(width,height);
		
		KeyListener listener = new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if (event.getCode() == Keyboard.KEY_LEFT)
				{
					currentPiece.move(-1, 0);
				}
				if (event.getCode() == Keyboard.KEY_RIGHT)
				{
					currentPiece.move(1, 0);
				}
				if (event.getCode() == Keyboard.KEY_UP)
				{
					currentPiece.rotateClockwise();
				}
				if (event.getCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() * 2);
				}
			}

			public void keyReleased(KeyEvent event)
			{
				if (event.getCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() / 2);
				}
			}

			public void keyTyped(KeyEvent event)
			{
				
			}
					
			public void keyDown(KeyEvent event)
			{
	
			}
		};
		
		Keyboard.addKeyListener(listener);
		
//		setTicksPerSecond(8f);
	}

	public BunchOSquares getBoss()
	{
		return boss;
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
			currentPiece.move(0, -1);
			
			if(yallHitTheBottomBaby())
			{
				System.out.println("asdf");
				
				currentPiece.move(0, 1);
				
				if (currentPiece.getY() < 0)
				{
					currentPiece.move(0, 1);
				}
				
				currentPiece.kill();
				
				currentPiece = tetris.getSidebar().getNextPiece().getNextPiece();
				tetris.getSidebar().getNextPiece().generateNextPiece();
				addPiece(currentPiece, 4, 18);
				
				if(!isValid(currentPiece.getX(), currentPiece.getY()))
				{
					if(currentPiece.getY() >= super.getHeight())
					{
						for (BoardListener listener : events)
						{
							System.out.println("lost");
							listener.onGameLost(null);
							
							lost = true;
						}
					}
				}
				clearRows();
			}
		}
	}
	
	/**
	 * Checks to see if the piece can move down or not. If it can move
	 * lost returns true, otherwise lost returns false.
	 * @return lost. Lost is either true or false.
	 */
	public boolean yallHitTheBottomBaby()
	{
		return !isValid(currentPiece.getX(), currentPiece.getY());
	}
	
	/**
	 * Sets lost to l.
	 */
	public void setLost(boolean l)
	{
		lost = l;
	}
	
	/**
	 * @return lost. Lost is either true or false. If lost is true, the
	 * piece is still able to move.
	 */
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
	
	/**
	 * Looks at one row of the board at a time and checks each location in
	 * that row to see if it has a square. If all of the locations in the
	 * row have a square the squares are deleted.
	 */
	public void clearRows()
	{
		int counter = 0;
		for(int r = 0; r < super.getHeight()-1;r++)
			for(int c = 0; c < super.getWidth()-1;c++)
			{
				if(!isValid(r,c))
				{					
					counter++;
				}
				if(counter==getWidth())
				{
					for(int dRow =0; dRow < super.getHeight()-1; dRow++)
					{
						Location l = new Location(dRow,c);
						
//						getPiece(l).deleteSquare(l);
					}
					
					counter = 0;
				}
			}
	}
	
	/**
	 * Adds a BoardListener to the ArrayList events.
	 * @param b
	 */
	public void addListener(BoardListener b)
	{
		events.add(b);
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#newGame()
	 */
	public void newGame()
	{
		currentPiece = new Piece(1);
		
		addPiece(currentPiece, 4, 18);
	}
}
