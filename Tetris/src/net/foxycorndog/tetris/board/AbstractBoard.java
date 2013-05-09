package net.foxycorndog.tetris.board;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;

/**
 * Class that holds the information for the Pieces in the Tetris game,
 * as well as demonstrating the interactions of the Pieces.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 3:39:45 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 3:39:45 PM
 * @version	v0.1
 */
public abstract class AbstractBoard
{
	private int							x, y;
	private int							width, height;
	private int							gridSpaceSize;
	
	private float 						counter;
	
	private	Image						boardImage;
	
	private	Piece						border[];
	
	private ArrayList<Piece>			pieces;
	
	/**
	 * Instantiate the image for the Board as well as other
	 * instantiations.
	 * 
	 * @param width The number of horizontal grid spaces the Board will
	 * 		contain.
	 * @param height The number of vertical grid spaces the Board will
	 * 		contain.
	 * @param gridSpaceSize The size (in pixels) that each space on the
	 * 		Board will take up. eg: passing 10 would create 10x10 grid
	 * 		spaces across the board.
	 */
	public AbstractBoard(int width, int height, int gridSpaceSize)
	{
		this.width         = width;
		this.height        = height;
		this.gridSpaceSize = gridSpaceSize;
		
		border = BorderFactory.createBorder(3, width * gridSpaceSize, height * gridSpaceSize, true);
		
		boardImage = new Image(null);
		
		try
		{
			boardImage.setImage(new Texture("res/images/board.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pieces = new ArrayList<Piece>();
	}
	
	/**
	 * Get the number of columns in the Grid.
	 * 
	 * @return The number of columns in the Grid.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Get the number of rows in the Grid.
	 * 
	 * @return The number of rows in the Grid.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Get the size (in pixels) that each space on the
	 * Board will take up. eg: passing 10 would create 10x10 grid
	 * spaces across the board.
	 * 
	 * @return The size (in pixels) that each space on the Board will
	 * 		take up.
	 */
	public int getGridSpaceSize()
	{
		return gridSpaceSize;
	}
	
	/**
	 * Set the location that the Board will be rendered to.
	 * 
	 * @param x The horizontal location to render the Board to.
	 * @param y The vertical location to render the Board to.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Translate the Board the specified amount. A positive dx value
	 * moves the Board to the right. A positive dy value moves the Board
	 * upward.
	 * 
	 * @param dx The horizontal delta value to move the Board.
	 * @param dy The vertical delta value to move the Board.
	 */
	public void move(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	/**
	 * Add the specified Piece to the Board at the the specified
	 * grid space location.
	 * 
	 * @param piece The Tetris Piece to add to the Board.
	 * @param x The column to add the Piece to.
	 * @param y The row to add the Piece to.
	 */
	public void addPiece(Piece piece, int x, int y)
	{
		piece.setLocation(x, y);
		
		// Risky... but safe to assume.
		piece.setBoard((Board)this);
		
		pieces.add(piece);
	}
	
	/**
	 * Render the Board's background image as well as all of the Pieces
	 * to the screen.
	 */
	public void render()
	{
		// Save the current matrix until after the transformations have
		// been made.
		GL.pushMatrix();
		{
			// Translate everything that will be rendered after this call
			// by (x, y) pixels.
			GL.translate(x, y, 0);
			
			// A for each loop that renders all of the Pieces to the screen.
			for (AbstractPiece piece : pieces)
			{
				piece.render();
			}
			
			renderBorder();
		}
		// Return the the previous matrix formation.
		GL.popMatrix();
	}
	
	/**
	 * Method called whenever a new game is started.
	 */
	public void newGame()
	{
		
	}
	
	/**
	 * Render the Border around the grid that the Tetris game is
	 * played inside.
	 */
	private void renderBorder()
	{
		// A for each loop that renders all of the Pieces of the Border
		// to the screen.
		for (Piece piece : border)
		{
			piece.render();
		}
	}
	
	/**
	 * The loop that is called each frame. Most of the game logic and
	 * movement will be done in this method.
	 */
	public void loop()
	{
		// Variable created in order to keep the tempo of the game
		// constant despite any variations  in FPS (Frames per second)
		float delta = 60f / Frame.getFPS();
		
		if (!Float.isInfinite(delta) && !Float.isNaN(delta))
		{
			// Update the counter each frame.
			counter += delta;
		}
		
		// The amount of 'ticks' it takes to do a loop.
		int tickTime = 30;
		
		if (counter >= tickTime)
		{
			// Do all of the game movement and stuff here.
			
			tick();
			
			// Keep the leftover time too...
			counter %= tickTime;
		}
	}
	
	/**
	 * Method that calculates the game logic for the Tetris game each
	 * tick.
	 */
	public abstract void tick();
	
	/**
	 * Get whether the specified location (x, y) is a valid
	 * location on the Board. That is, the location is within
	 * the bounds of the Grid.
	 * 
	 * @param x The horizontal location (column) to check.
	 * @param y The vertical location (row) to check.
	 * @return Whether or not the specified location is within
	 *		the bounds of the Grid.
	 */
	public abstract boolean isValid(int x, int y);
}
