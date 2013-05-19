package net.foxycorndog.tetris.board;

import java.util.ArrayList;

/**
 * Class used to hold information for each Piece in the Tetris game. There are
 * also methods to manipulate the data and render the data to the screen.
 *
 * @author Henry Rybolt
 * @author Braden Steffaniak
 * @since May 6, 2013 at 3:36:36 PM
 * @since v0.1
 * @version May 6, 2013 at 3:36:36 PM
 * @version v0.1
 */
public class Piece extends AbstractPiece implements Cloneable
{
	private int						direction;
	private int						n;
	private Color					c;
	private boolean					dead;
	public static final Location	RIGHT	= new Location(1, 0);
	public static final Location	LEFT	= new Location(-1, 0);
	public static final Location	UP		= new Location(0, 1);
	public static final Location	DOWN	= new Location(0, -1);

	/**
	 * Create a Piece with the specified shapes and color.
	 *
	 * @param locations ArrayList of the locations of the square within.
	 * @param color The Color of the Piece.
	 */
	public Piece(ArrayList<Location> locations, Color color)
	{
		super(locations, color);
	}

	/**
	 * Create a Piece with the specified shapes and color.
	 *
	 * @param locations Array of the locations of the square within.
	 * @param color The Color of the Piece.
	 */
	public Piece(Location locations[], Color color)
	{
		super(locations, color);
	}

	/**
	 * creates a tetrimino at the top of the world w, a color that corresponds
	 * with its shape, its shape is defined by n the code is as follows n values
	 * are the integer the shape is the image: |_| |_||_| |_||_| 1 =
	 * |_||_||_||_|, 2 = |_||_||_|, 3 = |_||_| , 4 = |_||_|,
	 *
	 * |_||_||_| |_||_||_| |_||_| 5 = |_| , 6 = |_|, 7 = |_||_|
	 */
	public Piece(int n)
	{
		if (n > 7 || n < 1)
		{
			throw new IllegalArgumentException("n must be 1-7");
		}

		calculatePiece(n);
		
		this.n = n;
	}

	/**
	 * creates a tetrimino with its center at point (x,y), of color c, its
	 * square componenets defined by their x and y coordinates stored in the
	 * shape matrix, and in the world w
	 */
	public void loadPiece(int[][] temp, Color c)
	{
		direction = 0;
		dead      = false;
		this.c    = c;
		setColor(c);

		editShape(temp);
	}

	/**
	 * sets the shape using a 2 dimensional matrix
	 */
	public void editShape(int[][] s)
	{
		ArrayList<Location> locs = new ArrayList<Location>();

		for (int index = 0; index < s.length; index++)
		{
			Location loc = new Location(s[index][0], s[index][1]);
			locs.add(loc);
		}

		setShape(locs);
	}

	private void calculatePiece(int n)
	{
		this.n = n;
		// dead = true;
		int[][] temp;

		if (n == 1)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 2, 0 } };
			loadPiece(temp, Color.CYAN);
		}
		else if (n == 2)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, 1 } };
			loadPiece(temp, Color.MAGENTA);
		}
		else if (n == 3)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 1 } };
			loadPiece(temp, Color.GREEN);
		}
		else if (n == 4)
		{
			temp = new int[][] { { 0, 0 }, { -1, 1 }, { 1, 0 }, { 0, 1 } };
			loadPiece(temp, Color.RED);
		}
		else if (n == 5)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 1, 1 } };
			loadPiece(temp, Color.ORANGE);
		}
		else if (n == 6)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { -1, 1 } };
			loadPiece(temp, Color.BLUE);
		}
		else if (n == 7)
		{
			temp = new int[][] { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, 1 } };
			loadPiece(temp, Color.YELLOW);
		}
	}
	
	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public boolean rotateCC()
	{
		return rotate(false);
	}

	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public boolean rotateC()
	{
		return rotate(true);
	}
	
	private boolean rotate(boolean clockwise)
	{
		if (n == 7)
		{
			return true;
		}
		
		boolean ableToRotate     = true;
		ArrayList<Location> copy = new ArrayList<Location>();

		for (Location l : getShape())
		{
			copy.add(new Location(l.getX(), l.getY()));
		}

		for (int i = 0; i < copy.size(); i++)
		{
			Location loc = copy.get(i);
			
//			loc.subtract2(getCenter());
			
			if (clockwise)
			{
				loc.rotateC();
			}
			else
			{
				loc.rotateCC();
			}
			
//			loc.add2(new Location(getOffsetX(), getOffsetY()));
			
//			loc.add2(getCenter());
//			loc.add2(new Location(getCenter().getX(), 0));
//			loc.add2(UP);
//			if (getHeight() % 2 == 0)// && getWidth() % 2 == 0)
//			{
//				loc.add2(DOWN);
//			}
			
//			loc.add2(new Location(getHeight() % 2, getWidth() % 2));
//			loc.add2(new Location(1, 1));
			
			Location next = loc.add(getLocation());

			if (!spaceIsFree(next))
			{
				ableToRotate = false;

				break;
			}
		}

		if (ableToRotate)
		{
			setShape(copy);
		}
		
		return ableToRotate;
	}

	/**
	 * returns true if this tetrimino can not move any further
	 * down else false
	 */
	public boolean yallHitTheBottomBaby()
	{
		boolean ableToMove = true;

		for (int i = 0; i < getShape().size(); i++)
		{
			Location next = getShape().get(i).add(getLocation());

			if (!spaceIsFree(next))
			{
				ableToMove = false;
			}
		}

		return !ableToMove;
	}

	/**
	 * kills the tetrimino makes it outdated
	 */
	public void kill()
	{
		dead = true;
	}

	/**
	 * If a square in a given piece is in a certain location it is
	 * deleted from the board.
	 * @param loc
	 */
	public void deleteSquare(Location loc)
	{
		ArrayList<Location> shape = getShape();

		for (int i = shape.size() - 1; i >= 0; i--)
		{
			Location l = shape.get(i).add(getLocation());

			if (l.equals(loc))
			{
				shape.remove(i);

				break;
			}
		}

		setShape(shape);
	}

	/**
	 * Used to move a square to a new location given in the parameter.
	 * @param src
	 * @param dst
	 */
	public void moveSquare(Location src, Location dst)
	{
		ArrayList<Location> shape = getShape();

		for (int i = shape.size() - 1; i >= 0; i--)
		{
			Location l = shape.get(i).add(getLocation());

			if (l.equals(src))
			{
				shape.set(i, shape.get(i).add(dst));

				break;
			}
		}

		setShape(shape);
	}

	/**
	 * rotates the tetrimino clockwise
	 */
	public boolean rotateClockwise()
	{
		return rotateC();
	}

	/**
	 * rotates the tetrimino counter-clockwise
	 */
	public boolean rotateCounterClockwise()
	{
		return rotateCC();
	}

	/**
	 * Get a brand new instance of one of the seven original Pieces.
	 *
	 * @return A brand new instance of one of the seven original Pieces.
	 */
	public static Piece getRandomPiece()
	{
		int index = (int)(Math.random() * 7);

		Piece rand = new Piece(index + 1);

		return rand;
	}
}
