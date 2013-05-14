package net.foxycorndog.tetris.board;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * Class that holds the information for the Pieces in the Tetris game, as well
 * as demonstrating the interactions of the Pieces.
 * 
 * @author Jeremiah Blackburn
 * @author Braden Steffaniak
 * @since May 6, 2013 at 3:31:08 PM
 * @since v0.1
 * @version May 6, 2013 at 3:31:08 PM
 * @version v0.1
 */
public class Board extends AbstractBoard
{
	private boolean						lost;

	private Piece						currentPiece;

	private Tetris						tetris;

	private int[]						deleteRows;

	private ArrayList<BoardListener>	events;

	/**
	 * Instantiate the image for the Board as well as other instantiations.
	 * 
	 * @param width
	 *            The number of horizontal grid spaces the Board will contain.
	 * @param height
	 *            The number of vertical grid spaces the Board will contain.
	 * @param gridSpaceSize
	 *            The size (in pixels) that each space on the Board will take
	 *            up. eg: passing 10 would create 10x10 grid spaces across the
	 *            board.
	 */
	public Board(int width, int height, int gridSpaceSize, Tetris tetris)
	{
		super(width, height, gridSpaceSize);

		this.tetris = tetris;

		events = new ArrayList<BoardListener>();
		deleteRows = new int[4];

		KeyListener listener = new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if (event.getCode() == Keyboard.KEY_LEFT)
				{
					movePiece(currentPiece, -1, 0);
				}
				if (event.getCode() == Keyboard.KEY_RIGHT)
				{
					movePiece(currentPiece, 1, 0);
				}
				if (event.getCode() == Keyboard.KEY_UP)
				{
					currentPiece.rotateClockwise();
				}
				if (event.getCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() * 4);
				}
			}

			public void keyReleased(KeyEvent event)
			{
				if (event.getCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() / 4);
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

		// setTicksPerSecond(8f);
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#tick() Moves a piece down
	 *      one space after half a second until the piece hits the bottom of the
	 *      board or another piece on the board.
	 */
	public void tick()
	{
		if (!getLost())
		{
			// if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			// {
			// currentPiece.move(-1, 0);
			// }
			// if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			// {
			// currentPiece.move(1, 0);
			// }

//			currentPiece.move(0, -1);
			boolean moved = movePiece(currentPiece, 0, -1);

			if (!moved)
			{
				currentPiece.kill();

				currentPiece = tetris.getSidebar().getNextPiece()
						.getNextPiece();
				tetris.getSidebar().getNextPiece().generateNextPiece();
				addPiece(currentPiece, 4, 18);

				if (currentPiece.yallHitTheBottomBaby())
				{
					lost = true;

					for (BoardListener listener : events)
					{
						listener.onGameLost(null);
					}
					
					Tetris.SOUND_LIBRARY.playSound("lose.wav");
				}
				else
				{
					Tetris.SOUND_LIBRARY.playSound("pop.wav");
				}

				clearRows();
			}
		}
	}
	
	private boolean movePiece(Piece piece, int dx, int dy)
	{
		boolean moved = piece.move(dx, dy);
		
		if (moved)
		{
			BoardEvent event = new BoardEvent(piece.getX(), piece.getY(), piece, 0);
			
			for (BoardListener listener : events)
			{
				listener.onPieceMove(event);
			}
		}
		
		return moved;
	}

	/**
	 * Sets lost to l.
	 */
	public void setLost(boolean l)
	{
		lost = l;
	}

	/**
	 * @return lost. Lost is either true or false. If lost is true, the piece is
	 *         still able to move.
	 */
	public boolean getLost()
	{
		return lost;
	}

	/**
	 * Coordinates use the Cartesian system.
	 * 
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int) checks
	 *      to see it the coordinate (x,y) is valid for the piece to move to.
	 */
	public boolean isValid(int x, int y)
	{
		return (x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight());
	}

	/**
	 * Coordinates use the Cartesian system.
	 * 
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int) checks
	 *      to see it the coordinate (x,y) is valid for the piece to move to.
	 */
	public boolean isValid(Location loc)
	{
		return isValid(loc.getX(), loc.getY());
	}

	/**
	 * Looks at one row of the board at a time and checks each location in that
	 * row to see if it has a square. If all of the locations in the row have a
	 * square the squares are deleted.
	 */
	public void clearRows()
	{
		// if (true)return;
		int counter = 0;
		
		int r       = 0;
		
		int lines   = 0;

		while (r < getHeight())
		{
			counter = 0;

			for (int c = 0; c < getWidth(); c++)
			{
				if (getPieces(new Location(c, r)).length > 0)
				{
					counter++;
				}

				if (counter == getWidth())
				{
					for (int dRow = 0; dRow < getWidth(); dRow++)
					{
						Location l = new Location(dRow, r);
						
						Piece pieces[] = getPieces(l);
						
						pieces[0].deleteSquare(l);
					}
					
					for (int y = r; y < getHeight(); y++)
					{
						for (int x = 0; x < getWidth(); x++)
						{
							Location l = new Location(x, y);
							
							Piece pieces[] = getPieces(l);
							
							if (pieces.length > 0)
							{
								pieces[0].moveSquare(l, new Location(0, -1));
							}
						}
					}
					
					lines++;
				}
			}
			
			if (counter < getWidth())
			{
				r++;
			}
		}
		
		if (lines > 0)
		{
			BoardEvent event = new BoardEvent(0, r, null, lines);
	
			for (BoardListener listener : events)
			{
				listener.onLineDeleted(event);
			}
			
			Tetris.SOUND_LIBRARY.playSound("lineremoved.wav");
		}
	}

	/**
	 * Adds a BoardListener to the ArrayList events.
	 * 
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
		currentPiece = Piece.getRandomPiece();

		addPiece(currentPiece, 4, 18);
		
		Tetris.SOUND_LIBRARY.playSound("pop.wav");
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#addPiece(net.foxycorndog.tetris.board.Piece,
	 *      int, int)
	 */
	@Override
	public void addPiece(Piece piece, int x, int y)
	{
		piece.setBoard(this);
		piece.setLocation(x, y);

		getPieces().add(piece);
	}
}
