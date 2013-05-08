import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import info.gridworld.grid.Location;

public class Tetris
{
	private boolean   tetris;
	private int       points;
	private Tetrimino t;
	
	public Tetris()
	{
		points = 0;
		tetris = false;
		final BoundedGrid bG = new BoundedGrid(20,10);
		ActorWorld world = new ActorWorld(bG)
		{
			public boolean keyPressed(String des, Location loc)
			{
				if(des.equals("UP"))
				{
					t.rotateC();
				}
				if(des.equals("DOWN"))
				{
					t.rotateCC();
				}
				if(des.equals("LEFT"))
				{
					t.moveLeft();
				}
				if(des.equals("RIGHT"))
				{
					t.moveRight();
				}
				if(des.equals("1"))
				{
					t.moveDown();
				}
				return false;
			}
			
			public void step()
			{
				super.step();
				lose(t);
				int linesCompleted = clearRows(bG);
				addScore(linesCompleted);
				if(t.yallHitTheBottomBaby())
				{
					try {
						t = new Tetrimino((int)(Math.random()*7)+1, this);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		try {
			t = new Tetrimino((int)(Math.random()*7)+1, world);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		world.show();
	}
	
	public static void main(String[] arg)
	{
		new Tetris();
	}

	
	/**
	 * declares the loser and their score
	 */
	private void lose(Tetrimino t)
	{
		if(t.doubledUp())
			System.out.print("You lose Score: " + getPoints());
	}
	
	/**
	 * adds the score based upon the number of lines cleared at once
	 */
	private void addScore(int numLines)
	{
		if(numLines == 1)
		{
			addPoints(100);
			setTetris(false);
		}
		if(numLines == 2)
		{
			addPoints(300);
			setTetris(false);
		}
		if(numLines == 3)
		{
			addPoints(500);
			setTetris(false);
		}
		if(numLines == 4 && !getTetris())
		{
			setTetris(true);
			addPoints(800);
		}
		if(numLines == 4 && getTetris())
		{
			setTetris(false);
			addPoints(1600);
		}
	}
	
	/**
	 * adds addition to points
	 */
	public void addPoints(int addition)
	{
		points =+ addition;
	}
	
	/**
	 * returns tetris
	 */
	public boolean getTetris()
	{
		return tetris;
	}
	
	/**
	 * sets tetris to t
	 */
	public void setTetris(boolean t)
	{
		tetris = t;
	}
	
	/**
	 * returns the points 
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * returns the rows that are complete if their are none it returns a -1
	 */
	private ArrayList<Integer> completeRow(Grid g) 
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int r = 0; r < g.getNumRows(); r++) 
		{
			boolean completeRow = true;
			for (int c = 0; c < g.getNumCols(); c++) 
			{
				Location loc = new Location(r, c);
				if (g.get(loc) == null) 
				{
					completeRow = false;
				}
			}
			if (completeRow) 
			{
				a.add(r);
			}
		}
		
		if (a.isEmpty())
			a.add(-1);
		return a;
	}
	
	/**
	 * clears the complete rows and moves the rows above downwards
	 */
	private int clearRows(Grid g)
	{
		ArrayList<Integer> a = completeRow(g);
		if(a.get(a.size()-1).equals(-1))
			return -1;
		return deleteRows(a, g);
	}
	
	/**
	 * clears the rows in arraylist a and moves the rows above downwards
	 * @return 
	 */
	private int deleteRows(ArrayList<Integer> a, Grid g)
	{
		int numRows = a.size();
		while(!a.isEmpty())
		{
			deleteRow(a.remove(a.size()-1), g);
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
	private void deleteRow(int r, Grid g)
	{
		for(int c = 0; c < g.getNumCols(); c ++)
		{
			Location loc = new Location(r,c);
			Actor a = (Actor) g.get(loc);
			if(a != null)
				a.removeSelfFromGrid();
		}
		
		for (int r2 = r; r2 > -1; r2--) 
		{
			for (int c2 = 0; c2 < g.getNumCols(); c2++) 
			{
				Location loc = new Location(r2, c2);
				Actor b = (Actor) g.get(loc);
				if(b != null)
				{
					Location next = new Location(r2 + 1, c2);
					b.moveTo(next);
				}
			}
		}
	}

}
