package net.foxycorndog.tetris.board;

import java.util.ArrayList;

/**
 * Class used to hold information for each Piece in the Tetris
 * game. There are also methods to manipulate the data and
 * render the data to the screen.
 *
 * @author	Braden Steffaniak, Henry Rybolt
 * @since	May 6, 2013 at 3:36:36 PM
 * @since	v0.1
 * @version	May 6, 2013 at 3:36:36 PM
 * @version	v0.1
 */
public class Piece extends AbstractPiece implements Cloneable
{
	private int                   direction;
	private int                   n;
	private ArrayList<Square>     squares;
	private Location              place;
	private Color                 c;
	private boolean               dead;
	private static	Piece		  pieces[];
	public static final Location  RIGHT = new Location(1,0);
	public static final Location  LEFT  = new Location(-1,0);
	public static final Location  UP    = new Location(0,1);
	public static final Location  DOWN  = new Location(0,-1);
	
	static
	{
		pieces = new Piece[7];
	}
	
	/**
	* creates a tetrimino with its center at point (x,y), of color c, its
	* square componenets defined by their x and y coordinates stored in
	* the shape matrix, and in the world w
	*/
	public ArrayList<Location> piece(int[][] temp, Color c, int x, int y)
	{
		direction  = 0;
		dead     = false;
		this.c     = c;
		place      = new Location(x,y);
		squares    = new ArrayList<Square>();
		
		setColor(c);
		
		return editShape(temp);
	}

	/**
	 * sets the shape using a 2 dimensional matrix
	 */
	public ArrayList<Location> editShape(int[][] s)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		
		for(int index = 0; index < s.length; index++)
		{
			Location loc = new Location(s[index][0], s[index][1]);
			locs.add(loc);
			squares.add(new Square(c, this, locs.get(index).getX() == 0 && locs.get(index).getY() == 0, loc));
			squares.get(index).setLocation(place.add(loc));
		}
		
		return locs;
	}
	
	/**
	 * deletes a square
	 */
	public void delete(Square s)
	{
		squares.remove(s);
	}
	
	public Piece(ArrayList<Location> locations, Color color)
	{
		super(locations, color);
	}
	
	/**
	 * creates a tetrimino at the top of the world w, a color that
	 * cooresponds with its shape, its shape is defined by n the code is
	 * as follows n values are the integer the shape is the image:
	 *                          |_|            |_||_|    |_||_|
	 * 1 = |_||_||_||_|, 2 = |_||_||_|, 3 = |_||_|  , 4 =   |_||_|,
	 *
	 *     |_||_||_|      |_||_||_|      |_||_|
	 * 5 = |_|      , 6 =       |_|, 7 = |_||_|
	 */
	public Piece(int n)
	{
		if (pieces[n] == null)
		{
			pieces[n] = calculatePiece(n);
		}
		
		setShape((ArrayList<Location>)pieces[n].getShape().clone());
		
		this.n = n;
	}
	
	private Piece calculatePiece(int n)
	{
		this.n = n;
		//dead = true;
		int[][] temp;
		if(n == 1)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{2,0}};
			return new Piece(piece(temp, Color.CYAN, 5, 19), Color.CYAN);
		}
		else if(n == 2)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{0,1}};
			return new Piece(piece(temp, Color.MAGENTA, 5, 18), Color.MAGENTA);
		}
		else if(n == 3)
		{
			temp = new int[][] {{0,0},{-1,0},{0,1},{1,1}};
			return new Piece(piece(temp, Color.GREEN, 5, 18), Color.GREEN);
		}
		else if(n == 4)
		{
			temp = new int[][] {{0,0},{-1,1},{1,0},{0,1}};
			return new Piece(piece(temp, Color.RED, 5, 18), Color.RED);
		}
		else if(n == 5)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{1,1}};
			return new Piece(piece(temp, Color.ORANGE, 5, 18), Color.ORANGE);
		}
		else if(n == 6)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{-1,1}};
			return new Piece(piece(temp, Color.BLUE, 5, 18), Color.BLUE);
		}
		else if(n == 7)
		{
			temp = new int[][] {{0,0},{1,0},{1,1},{0,1}};
			return new Piece(piece(temp, Color.YELLOW, 5, 18), Color.YELLOW);
		}
		
		return null;
	}

	/**
	 * updates the image of the tetrimino in gridworld
	 */
	public void updateLocations()
	{
		for(int i = 0; i < getShape().size(); i++)
		{
			Location next = getShape().get(i).add(place);
			Square s = squares.get(i);
			s.setLocation(next);
			if(s.getBoard() != null)
				s.moveTo(next);
			else
				getBoard().getBoss().add(next, s);
			s.setLocation(next);
		}
	}

	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public void rotateCC()
	{
		if(n == 1 && direction == 1)
		{
			rotateC();
			direction = 0;
			return;
		}
		if(n == 7)
			return;
		boolean ableToRotate = true;
		ArrayList<Location> copy = (ArrayList<Location>) getShape().clone();
		for(int i = 0; i < getShape().size(); i++)
		{
			getShape().get(i).rotateCC();
			Location next = getShape().get(i).add(place);
			Square s      = squares.get(i);
			if(spaceIsFree(s, next) == false)
				ableToRotate = false;
			}
		if(!ableToRotate)
			setShape(copy);
		updateLocations();
	}

	/**
	 * retruns true if the space is free of other tetriminos else false
	 */
	public boolean spaceIsFree(Square s, Location next)
	{
		if(!getBoard().isValid(next.getX(), next.getY()))
			return false;

		Square neighborInNext = (Square) getBoard().getBoss().get(next);
		if(getBoard().isValid(next.getX(), next.getY()))
		{
			if(neighborInNext != null)
			{
				if(neighborInNext.getPiece() != this)
				{
					return false;
				}
				else
				{
					neighborInNext.removeFromBoard();
					return true;
				}
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * rotates the tetrimino 90 degrees counter-clockwise if possible
	 */
	public void rotateC()
	{
		if(n == 1 && direction == 1)
		{
			rotateCC();
			direction = 0;
			return;
		}
		if(n == 7)
			return;
		boolean ableToRotate = true;
		ArrayList<Location> copy = (ArrayList<Location>) getShape().clone();
		for(int i = 0; i < getShape().size(); i++)
		{
			getShape().get(i).rotateC();
			Location next = getShape().get(i).add(place);
			Square s      = squares.get(i);
			if(spaceIsFree(s, next) == false)
				ableToRotate = false;
		}
		if(!ableToRotate)
			setShape(copy);
		updateLocations();
	}

	/**
	 * precondition: the tetrimino this method is being used on has just
	 * been constructed
	 * returns true if their are two or more tetriminos occupying the same
	 * space false if their is only one tetrimino returns false
	 */
	public boolean doubledUp()
	{
		boolean doubledUp = false;
		for(int i = 0; i < getShape().size(); i++)
		{
			Location next = getShape().get(i).add(place);
			Square s = squares.get(i);
			if(s.getBoard() != null)
			{
				if(s.getBoard().getBoss().get(next) != null)
					doubledUp = true;
			}
			else
			{
				if(getBoard().getBoss().get(next) != null)
					doubledUp = true;
			}
		}
		return doubledUp;
	}

	/**
	 * returns true if this tetrimino can not move any further down else 
	 * false
	 */
	public boolean yallHitTheBottomBaby()
	{
		boolean ableToMove = true;
		for(int i = 0; i < getShape().size(); i++)
		{
			Square s = squares.get(i);
			Location next = getShape().get(i).add(place).add(DOWN);
			if(spaceIsFree(s, next) == false)
				ableToMove = false;
		}
		updateLocations();
		return !ableToMove;
	}
	
	/**
	 * moves the peice according to the positional vector added
	 */
	public void move(Location l)
	{
		boolean ableToMove = true;
		for(int i = 0; i < getShape().size(); i++)
		{
			Square s = squares.get(i);
			Location next = getShape().get(i).add(place).add(l);
			if(spaceIsFree(s, next) == false)
				ableToMove= false;
		}

		if(ableToMove == true)
			place.add(l);
		updateLocations();
	}

	/**
	 * kills the tetrimino makes it outdated
	 */
	public void kill()
	{
		dead = true;
	}

	/**
	 * returns whether the tetrimino was klled
	 */
	//public boolean isDead()
	//{
	//	return dead;
	//}

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
	 * Get the array of Pieces. Contains all seven of the original Piece
	 * shapes.
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
		
		if (pieces[index] == null)
		{
			new Piece(index);
		}

		return pieces[index].clone();
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
