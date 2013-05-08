import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.gui.WorldFrame;
import info.gridworld.grid.Location;

public class Tetris
{
	private Tetrimino t;
	
	public Tetris()
	{
		BoundedGrid bG = new BoundedGrid(20,10);
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
					System.out.print("kdfjsdklfja;lsdkjfakl;fjsl;sdkfjal;fsdjklad;");
					t.moveDown();
				}
				return false;
			}
			
			public void step()
			{
				super.step();
				
				if(t.yallHitTheBottomBaby())
				{
					System.out.print("its no good on botttom");
					t = new Tetrimino((int)(Math.random()*7)+1, this);
				}
			}
		};
		t = new Tetrimino((int)(Math.random()*7)+1, world);
		
		
		world.show();
	}
	
	public static void main(String[] arg)
	{
		new Tetris();
	}
	
	private boolean youWinsMoneys(Grid g)
	{
		for(int r = 0; r < g.getNumRows(); r++)
		{
			boolean ColComplete = false;
			for(int c = 0; c < g.getNumCols(); r++)
			{
				if(g.get(new Location(r,c)) instanceof Square)
				{
					
				}
					
			}
		}
		return false;
	}
}
