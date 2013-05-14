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
	private ArrayList<Square>		squares;
	private Location				place;
	private Color					c;
	private boolean					dead;
	private static Piece			pieces[];
	public static final Location	RIGHT	= new Location(1, 0);
	public static final Location	LEFT	= new Location(-1, 0);
	public static final Location	UP		= new Location(0, 1);
	public static final Location	DOWN	= new Location(0, -1);

	static
	{
		pieces = new Piece[7];

		for (int i = 0; i < pieces.length; i++)
		{
			pieces[i] = new Piece(i + 1);
		}
	}

	/**
	 * Create a Piece with the specified shapes and color.
	 * 
	 * @param locations
	 *            ArrayList of the locations of the square within.
	 * @param color
	 *            The Color of the Piece.
	 */
	public Piece(ArrayList<Location> locations, Color color)
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
		
		squares = new ArrayList<Square>();

		calculatePiece(n);
	}

	/**
	 * creates a tetrimino with its center at point (x,y), of color c, its
	 * square componenets defined by their x and y coordinates stored in the
	 * shape matrix, and in the world w
	 */
	public void piece(int[][] temp, Color c, int x, int y)
	{
		direction = 0;
		dead      = false;
		this.c    = c;
		place     = new Location(x, y);
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

		locs = getShape();
		
		for (int i = 0; i < locs.size(); i++)
		{
			Location loc = locs.get(i);
			
			//TODO fix center w/ max and min
			boolean center = loc.getX() == getWidth() / 2 && loc.getY() == getHeight() / 2;
			
			Square square = new Square(c, this, center, loc);
			square.setLocation(place.add(loc));
			
			squares.add(square);
		}
	}

	/**
	 * deletes a square
	 */
	public void delete(Square s)
	{
		squares.remove(s);
	}

	private void calculatePiece(int n)
	{
		this.n = n;
		// dead = true;
		int[][] temp;

		if (n == 1)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 2, 0 } };
			piece(temp, Color.CYAN, 5, 17);
		}
		else if (n == 2)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, 1 } };
			piece(temp, Color.MAGENTA, 5, 18);
		}
		else if (n == 3)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 1 } };
			piece(temp, Color.GREEN, 5, 18);
		}
		else if (n == 4)
		{
			temp = new int[][] { { 0, 0 }, { -1, 1 }, { 1, 0 }, { 0, 1 } };
			piece(temp, Color.RED, 5, 18);
		}
		else if (n == 5)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 1, 1 } };
			piece(temp, Color.ORANGE, 5, 18);
		}
		else if (n == 6)
		{
			temp = new int[][] { { 0, 0 }, { -1, 0 }, { 1, 0 }, { -1, 1 } };
			piece(temp, Color.BLUE, 5, 18);
		}
		else if (n == 7)
		{
			temp = new int[][] { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, 1 } };
			piece(temp, Color.YELLOW, 5, 18);
		}
	}

	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public void rotateCC()
	{
		rotateC();
		rotateC();
		rotateC();
		
//		if (n == 1 && direction == 1)
//		{
//			rotateC();
//			direction = 0;
//
//			return;
//		}
//		if (n == 7)
//		{
//			return;
//		}
//
//		boolean ableToRotate = true;
//		ArrayList<Location> copy = (ArrayList<Location>)getShape().clone();
//
//		for (int i = 0; i < getShape().size(); i++)
//		{
//			getShape().get(i).rotateCC();
//			Location next = getShape().get(i).add(place);
//
//			if (spaceIsFree(next) == false)
//			{
//				ableToRotate = false;
//			}
//		}
//
//		if (!ableToRotate)
//		{
//			setShape(copy);
//		}
//
//		updateLocations();
	}

	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public void rotateC()
	{
//		if (n == 1 && direction == 1)
//		{
//			rotateCC();
//			direction = 0;
//
//			return;
//		}
//
//		if (n == 7)
//		{
//			return;
//		}

		boolean ableToRotate     = true;
		ArrayList<Location> copy = new ArrayList<Location>();
		
		for (Location l : getShape())
		{
			copy.add(new Location(l.getX(), l.getY()));
		}
		
		for (int i = 0; i < copy.size(); i++)
		{
			copy.get(i).rotateC();
			Location next = copy.get(i).add(getLocation());

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
	}

	/**
	 * precondition: the tetrimino this method is being used on has just been
	 * constructed returns true if their are two or more tetriminos occupying
	 * the same space false if their is only one tetrimino returns false
	 */
	public boolean doubledUp()
	{
		boolean doubledUp = false;

		for (int i = 0; i < getShape().size(); i++)
		{
			Location next = getShape().get(i).add(place);
			Square s = squares.get(i);

			if (s.getBoard() != null)
			{
				if (s.getBoard().getBoss().get(next) != null)
				{
					doubledUp = true;
				}
			}
			else
			{
				if (getBoard().getBoss().get(next) != null)
				{
					doubledUp = true;
				}
			}
		}

		return doubledUp;
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
	 * returns whether the tetrimino was klled
	 */
	// public boolean isDead()
	// {
	// return dead;
	// }

	/**
	 * rotates the tetrimino clockwise
	 */
	public void rotateClockwise()
	{
		rotateC();
	}

	/**
	 * rotates the tetrimino counter-clockwise
	 */
	public void rotateCounterClockwise()
	{
		rotateCC();
	}

	/**
	 * Get the array of Pieces. Contains all seven of the original Piece shapes.
	 * 
	 * @return The array of the seven original Pieces.
	 */
	public static AbstractPiece[] getPieces()
	{
		return pieces.clone();
	}

	/**
	 * Get a brand new instance of one of the seven original Pieces.
	 * 
	 * @return A brand new instance of one of the seven original Pieces.
	 */
	public static Piece getRandomPiece()
	{
		int index = (int)(Math.random() * pieces.length);
		
		Piece rand = null;
		
//		if (pieces[index] == null)
//		{
			rand = new Piece(index + 1);
//		}
//		else
//		{
//			rand = pieces[index].clone();
//		}
		
		return rand;
	}

	/**
	 * Return a clone of the specified Piece instance.
	 * 
	 * @return Another instance of a Piece exactly like the preceding one.
	 */
	public Piece clone()
	{
		Piece piece = null;

		piece = (Piece)super.clone();

		return piece;
	}
}
