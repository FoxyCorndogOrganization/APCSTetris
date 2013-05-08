import info.gridworld.actor.Bug;

import java.awt.Color;


public class Square extends Bug
{
	private Tetrimino t;
	private int       x;
	private int       y;
	private boolean  center;
	
	public Square(Color c, Tetrimino t, boolean center) 
	{
		super(c);
		this.t      = t;
		this.center = center;
	}

	public void rotateC()
	{
		t.rotateC();
	}
	
	public void rotateCC()
	{
		t.rotateCC();
	}

	public Tetrimino getTetris() 
	{
		return t;
	}
	
	public void moveLeft()
	{
		t.moveLeft();
	}
	
	public void moveRight()
	{
		t.moveRight();
	}
	
	public void moveDown()
	{
		t.moveDown();
	}
	
	public void act()
	{
		//if(center == true)
			//t.rotateC();
		//turn();
		//turn();
		if(center == true)
			t.moveDown();
	}
}