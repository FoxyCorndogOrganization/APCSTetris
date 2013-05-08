import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import java.awt.Color;


public class Tetrimino 
{
	private Grid     g;
	private Square[] squares;
	private int      x,y;
	private int[][]  shape;
	private Color    c;
	private World    w;
	private int      n;
	private int      d;
	
	public void Tetrimino(int[][] shape, Color c, int x, int y, World w)
	{
		this.c     = c;
		this.x     = x;
		this.y     = y;
		this.shape = shape;		
		squares    = new Square[4];
		g = w.getGrid();
		this.w = w;
		
		for(int i = 0; i < 4; i++)
		{
			boolean center = false;
			if(shape[i][0] == 0 && shape[i][1] == 0)
				center = true;
			squares[i] = new Square(c, this, center);
			Location l = convertLFromCToG(new Location(x + shape[i][0], y + shape[i][1]));
			w.add(l, squares[i]);
		}
	}
	
	public Tetrimino(int n, World w)
	{
		d      = 0;
		this.n = n;
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
	
	public void rotateCC()
	{
		if(n == 7)
			return;
		if(n == 1 && d == 0)
		{
			rotateC();
			return;
		}
		boolean ableToRotate = true;
		int[][] copy         = shape.clone();
		
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
	 * 
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
	
	public void rotateC()
	{
		if(n == 7)
			return;
		if(n == 1 && d == 1)
		{
			rotateCC();
			return;
		}
		
		boolean ableToRotate = true;
		int[][] copy         = shape.clone();
		 
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
		return new Location((g.getNumRows() - l.getCol()) - 1, l.getRow());
	}
	
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
}

