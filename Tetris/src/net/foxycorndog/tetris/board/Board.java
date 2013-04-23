package net.foxycorndog.tetris.board;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

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
public class Board
{
	private int					x, y;
	private int					width, height;
	private int					gridSpaceSize;
	
	private float 				counter;
	
	private	Image				boardImage;
	
	private ArrayList<Piece>	pieces;
	
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
	public Board(int width, int height, int gridSpaceSize)
	{
		this.width         = width;
		this.height        = height;
		this.gridSpaceSize = gridSpaceSize;
		
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
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	public void addPiece(int x, int y, Piece piece)
	{
		piece.setLocation(x * gridSpaceSize, y * gridSpaceSize);
		
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
			
			// Render the board image to the screen.
			boardImage.render();
			
			// A for each loop that renders all of the Pieces to the screen.
			for (Piece piece : pieces)
			{
				piece.render();
			}
		}
		// Return the the previous matrix formation.
		GL.popMatrix();
	}
	
	/**
	 * The loop that is called each frame. Most of the game logic and
	 * movement will be done in this method.
	 */
	public void loop()
	{
		// Variable created in order to keep the tempo of the game
		// constant despite any variations  in FPS (Frames per second)
		float delta = Frame.getFPS() / 60f;
		
		// Update the counter each frame.
		counter += delta;
		
		// The amount of 'ticks' it takes to do a loop.
		int tickTime = 30;
		
		if (counter >= tickTime)
		{
			// Do all of the game movement and stuff here.
			
			
			
			// Keep the leftover time too...
			counter %= tickTime;
		}
	}
}