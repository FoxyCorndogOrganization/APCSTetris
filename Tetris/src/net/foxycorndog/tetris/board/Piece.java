package net.foxycorndog.tetris.board;

import java.awt.Color;
import java.util.ArrayList;

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
	private int      direction;
	private int      n;
	private ArrayList<Square> squares;
	private int      x,y;
	private ArrayList<int []>  shape;
	private Color    c;
	private boolean  dead;

	/**
	* creates a tetrimino with its center at point (x,y), of color c, its
	* square componenets defined by their x and y coordinates stored in
	* the shape matrix, and in the world w
	*/
	public void piece(ArrayList<int[]> shape, Color c, int x, int y)
	{
		direction  = 0;
		dead       = false;
		this.c     = c;
		this.x     = x;
		this.y     = y;
		this.shape = shape;
		squares    = new ArrayList<Square>();

		for(int i = 0; i < 4; i++)
		{
			boolean center = false;
			if(shape.get(i)[0] == 0 && shape.get(i)[1] == 0)
				center = true;
			squares.add(new Square(c, this, center));
			Location l = new Location(x + shape.get(i)[0], y + shape.get(i)[1]);
		}
	}

	public void editShape(int[][] s)
	{
		for(int index = 0; index < s.length; index++)
		{
			int[] temp = new int[2];
			temp[0] = s[index][0];
			temp[1] = s[index][1];
			shape.set(index, temp);
		}
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
		this.n = n;
		dead = true;
		ArrayList<int []> temp;
		if(n == 1)
		{
			temp = new ArrayList<int []>();
			int[] a = {0,0};
			temp.add(a);
			int[] a = {-1,0};
			temp.get(0)[0] = 0; temp.get(0)[1] = 0;
			temp.get(1)[]
			{{0,0},{-1,0},{1,0},{2,0}};
			piece(temp, Color.CYAN, 5, 19, w);
		}
		else if(n == 2)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{0,1}};
			piece(temp, new Color(255,0,255), 5, 18, w);
		}
		else if(n == 3)
		{
			temp = new int[][] {{0,0},{-1,0},{0,1},{1,1}};
			piece(temp, Color.GREEN, 5, 18, w);
		}
		else if(n == 4)
		{
			temp = new int[][] {{0,0},{-1,1},{1,0},{0,1}};
			piece(temp, Color.RED, 5, 18, w);
		}
		else if(n == 5)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{1,1}};
			piece(temp, Color.ORANGE, 5, 18, w);
		}
		else if(n == 6)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{-1,1}};
			piece(temp, Color.BLUE, 5, 18, w);
		}
		else if(n == 7)
		{
			temp = new int[][] {{0,0},{1,0},{1,1},{0,1}};
			piece(temp, Color.YELLOW, 5, 18, w);
		}
	}

	/**
	 * updates the image of the tetrimino in gridworld
	 */
	public void updateLocations()
	{
		for(int i = 0; i < 4; i++)
		{
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y));
			Square s = squares.get(i);
			if(s.getBoard() != null)
				s.moveTo(next);
			else
				getBoard().add(next, s);
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
		int[][] copy         = new int[4][2];
		for(int a = 0; a < 4; a++)
		{
			for(int b = 0; b < 2; b++)
			{
				copy[a][b] = shape[a][b];
			}
		}
		for(int i = 0; i < 4; i++)
		{
			int temp    = shape[i][0];
			shape[i][0] = -shape[i][1];
			shape[i][1] = temp;
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y));
			Square s = squares.get(i);
			//Square neighborInNext = (Square) s.getGrid().get(next);
			if(spaceIsFree(s, next) == false)
				ableToRotate = false;
			}
		if(!ableToRotate)
			shape = copy;
		updateLocations();
	}

	/**
	 * retruns true if the space is free of other tetriminos else false
	 */
	public boolean spaceIsFree(Square s, Location next)
	{
		if(!getBoard().isValid(next))
			return false;

		Square neighborInNext = (Square) getBoard().get(next);
		if(getBoard().isValid(next))
		{
			if(neighborInNext != null)
			{
				if(neighborInNext.getPiece() != this)
				{
					return false;
				}
				else
				{
					neighborInNext.removeSelfFromGrid();
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
		if(n == 7)
			return;
		if(n == 1 && direction == 0)
		{
			rotateCC();
			direction = 1;
			return;
		}
		boolean ableToRotate = true;
		int[][] copy         = new int[4][2];
		for(int a = 0; a < 4; a++)
		{
			for(int b = 0; b < 2; b++)
			{
				copy[a][b] = shape[a][b];
			}
		}
		for(int i = 0; i < 4; i++)
		{
			int temp    = shape[i][0];
			shape[i][0] = shape[i][1];
			shape[i][1] = -temp;
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y));
			Square s = squares.get(i);
			//Square neighborInNext = (Square) s.getGrid().get(next);
			if(spaceIsFree(s, next) == false)
				ableToRotate = false;
		}
		if(!ableToRotate)
			shape = copy;
		updateLocations();
	}

	/**
	 * converts a location from the cartesian grid(standard x and y axis) to GridWorlds's grid(with rows and columns)
	 */
	public Location convertLFromCToG(Location l)
	{
		return new Location((19-l.getCol()), l.getRow());
	}

	/**
	 * moves the tetrimino down if possible
	 */
	public void moveDown()
	{
		boolean ableToMove = true;
		for(int i = 0; i < 4; i++)
		{
			Square s = squares[i];
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y - 1));
			if(spaceIsFree(s, next) == false)
				ableToMove = false;
		}

		if(ableToMove == true)
			y--;
		updateLocations();
	}

	/**
	 * moves the tetrimino left if possible
	 */
	public void moveLeft()
	{
		boolean ableToMove = true;
		for(int i = 0; i < 4; i++)
		{
			Square s = squares.get(i);
			Location next = convertLFromCToG(new Location(shape[i][0] + x - 1, shape[i][1] + y));
			if(spaceIsFree(s, next) == false)
				ableToMove = false;
		}

		if(ableToMove == true)
			x--;
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
		for(int i = 0; i < 4; i++)
		{
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y));
			Square s = squares.get(i);
			if(s.getBoard() != null)
			{
				if(s.getBoard().get(next) != null)
					doubledUp = true;
			}
			else
			{
				if(w.getGrid().get(next) != null)
					doubledUp = true;
			}
		}
		return doubledUp;
	}

		/**
	 * returns true if this tetrimino can not move any further down else false
	 */
	public boolean yallHitTheBottomBaby()
	{
		boolean ableToMove = true;
		for(int i = 0; i < 4; i++)
		{
			Square s = squares.get(i);
			Location next = new Location(shape[i][0] + x, shape[i][1] + y - 1);
			if(spaceIsFree(s, next) == false)
				ableToMove = false;
		}
		updateLocations();
		return !ableToMove;
	}


	/**
	 * moves the tetrimino right if possible
	 */
	public void moveRight()
	{
		boolean ableToMove = true;
		for(int i = 0; i < 4; i++)
		{
			Square s = squares.get(i);
			Location next = new Location(shape[i][0] + x + 1, shape[i][1] + y);
			if(spaceIsFree(s, next) == false)
				ableToMove= false;
		}

		if(ableToMove == true)
			x++;
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
	public boolean isDead()
	{
		return dead;
	}

	public void rotateClockwise()
	{
		rotateC();
	}

	public void rotateCounterClockwise()
	{

	}
}
