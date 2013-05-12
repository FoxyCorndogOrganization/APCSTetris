package net.foxycorndog.tetris.board;

import java.util.ArrayList;

/**
* File:  			BunchOSquares.java
* Author:			Henry Thomas Rybolt
* Programming:	  	3rd Hour
* Last Modified: 	May 06, 2013
* Description:      BunchOSquares is a grid of squares. Full Rows of squares can be removed and the squares above are moved down.
*/
public class BunchOSquares 
{
	int width;
	int height;
	
	Square[][] map;
	
	public BunchOSquares(int w, int h)
	{
		this.width = w;
		this.height = h;
		map = new Square[width][height];
	}
	

	/**
	 * returns the rows that are complete if their are none it returns a 
	 * -1
	 */
	private ArrayList<Integer> completeRow() 
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int r = 0; r < height; r++) 
		{
			boolean completeRow = true;
			for (int c = 0; c < width; c++) 
			{
				Location loc = new Location(r, c);
				if (get(loc) == null) 
					completeRow = false;
			}
			if (completeRow) 
				a.add(r);
		}
		if (a.isEmpty())
			a.add(-1);
		return a;
	}

	/**
	 * clears the complete rows and moves the rows above downwards
	 */
	private int clearRows()
	{
		ArrayList<Integer> a = completeRow();
		if(a.get(a.size()-1).equals(-1))
			return -1;
		return deleteRows(a);
	}

	/**
	 * clears the rows in arraylist a and moves the rows above downwards
	 * @return 
	 */
	private int deleteRows(ArrayList<Integer> a)
	{
		int numRows = a.size();
		while(!a.isEmpty())
		{
			deleteRow(a.remove(a.size()-1));
			for(int index = 0; index < a.size(); index++)
			{
				a.set(index, a.get(index)+1);
			}
		}
		return numRows;
	}

	/**
	 * clears the row r and moves the rows above downwards
	 */
	private void deleteRow(int h)
	{
		for(int x = 0; x < width; x ++)
		{
			Location loc = new Location(x,h);
			Square a = get(loc);
			if(a != null)
				a.delete();
		}

		for (int r2 = h; r2 > -1; r2--) 
		{
			for (int c2 = 0; c2 < width; c2++) 
			{
				Location loc = new Location(r2, c2);
				Square b = get(loc);
				if(b != null)
					b.getPiece().move(Piece.DOWN);
			}
		}
	}
	
	/**
	 * returns a square if their is a square their else null
	 */
	public Square get(Location l)
	{
		return map[l.getX()][l.getY()];
	}
	
	/**
	 * moves a square to location l and removes any the square from its
	 * old location
	 */
	public boolean moveTo(Square s, Location l)
	{
		if(map[l.getX()][l.getY()] == null)
		{
			remove(s);
			map[l.getX()][l.getY()] = s;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * removes square s
	 */
	public void remove(Square s)
	{
		map[s.getLocation().getX()][s.getLocation().getY()] = null;
		s.setLocation(null);
	}

	/**
	 * adds a square to location s;
	 */
	public void add(Location l, Square s)
	{
		map[l.getX()][l.getY()] = s;
	}
	
	/**
	 * searches for square s and returns the location where it found it
	 * and if it cannot be found it returns null
	 */
	public Location search(Square s) 
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				Location l = new Location(x,y);
				if(l != null && get(l).equals(s))
					return l;
			}
		}
		return null;
	}
}
