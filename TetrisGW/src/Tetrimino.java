
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import java.awt.Color;

/**
* File:  			Tetrimino.java
* Author:			Henry Thomas Rybolt
* Programming:	  	3rd Hour
* Last Modified: 	May 06, 2013
* Description:      A Tetrimino can rotate and move in the world it was
*                   created by moving its four squares in the world
*/
public class Tetrimino 
{
	private int      direction;
	private int      n;
	private Grid     g;
	private Square[] squares;
	private int      x,y;
	private int[][]  shape;
	private Color    c;
	private World    w;
	private boolean  dead;
	
	/**
	 * creates a tetrimino with its center at point (x,y), of color c, its
	 * square componenets defined by their x and y coordinates stored in
	 * the shape matrix, and in the world w
	 */
	public void Tetrimino(int[][] shape, Color c, int x, int y, World w)
	{
		direction  = 0;
		dead       = false;
		this.c     = c;
		this.x     = x;
		this.y     = y;
		this.shape = shape;		
		squares    = new Square[4];
		g = w.getGrid();
		
		for(int i = 0; i < 4; i++)
		{
			boolean center = false;
			if(shape[i][0] == 0 && shape[i][1] == 0)
				center = true;
			squares[i] = new Square(c, this, center);
			Location l = convertLFromCToG(new Location(x + shape[i][0], y + shape[i][1]));
			//w.add(l, squares[i]);
		}
		this.w = w;
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
	public Tetrimino(int n, World w)
	{
		this.n = n;
		dead = true;
		int[][] temp;
		if(n == 1)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{2,0}};
			Tetrimino(temp, Color.CYAN, 5, 19, w);
		}
		else if(n == 2)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{0,1}};
			Tetrimino(temp, new Color(255,0,255), 5, 18, w);
		}
		else if(n == 3)
		{
			temp = new int[][] {{0,0},{-1,0},{0,1},{1,1}};
			Tetrimino(temp, Color.GREEN, 5, 18, w);
		}
		else if(n == 4)
		{
			temp = new int[][] {{0,0},{-1,1},{1,0},{0,1}};
			Tetrimino(temp, Color.RED, 5, 18, w);
		}
		else if(n == 5)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{1,1}};
			Tetrimino(temp, Color.ORANGE, 5, 18, w);
		}
		else if(n == 6)
		{
			temp = new int[][] {{0,0},{-1,0},{1,0},{-1,1}};
			Tetrimino(temp, Color.BLUE, 5, 18, w);
		}
		else if(n == 7)
		{
			temp = new int[][] {{0,0},{1,0},{1,1},{0,1}};
			Tetrimino(temp, Color.YELLOW, 5, 18, w);
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
			Square s = squares[i]; 
			if(s.getGrid() != null)
				s.moveTo(next);
			else
				w.add(next, s);
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
			Square s = squares[i]; 
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
		if(!g.isValid(next))
			return false;
			
		Square neighborInNext = (Square) g.get(next);
		if(g.isValid(next))
		{
			if(neighborInNext != null)
			{
				if(neighborInNext.getTetris() != this)
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
			Square s = squares[i]; 
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
		return new Location((g.getNumRows() -l.getCol() - 1), l.getRow());
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
			Square s = squares[i]; 
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
			Square s = squares[i]; 
			if(s.getGrid() != null)
			{
				if(s.getGrid().get(next) != null)
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
			Square s = squares[i]; 
			Location next = convertLFromCToG(new Location(shape[i][0] + x, shape[i][1] + y - 1));
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
			Square s = squares[i]; 
			Location next = convertLFromCToG(new Location(shape[i][0] + x + 1, shape[i][1] + y));
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
}

