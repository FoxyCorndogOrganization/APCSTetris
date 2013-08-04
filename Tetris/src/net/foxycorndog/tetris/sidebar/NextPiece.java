package net.foxycorndog.tetris.sidebar;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.board.AbstractPiece;
import net.foxycorndog.tetris.board.Piece;

/**
 * Class that displays and keeps track of the next piece.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:55:17 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 4:55:17 PM
 * @version	v1.0
 */
public class NextPiece
{
	private	int		x, y;
	private	int		width, height;
	
	private	float	scale;
	
	private	Piece	nextPiece;
	
	private	Image	bgImage;
	
	/**
	 * creates the next piece to enter the game and displays it in the
	 * sideBar
	 */
	public NextPiece()
	{
		bgImage = new Image(null);
		
		generateNextPiece();
		
		try
		{
			bgImage.setTexture(new Texture("res/images/nextpiece.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width  = Math.round(bgImage.getScaledWidth());
		height = Math.round(bgImage.getScaledHeight());
	}
	
	/**
	 * Set the new location of the NextPiece.
	 * 
	 * @param x The new horizontal location of the NextPiece.
	 * @param y The new vertical location of the NextPiece.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the width of the NextPiece.
	 * 
	 * @return The width of the NextPiece.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Get the height of the NextPiece.
	 * 
	 * @return The height of the NextPiece.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Get the width of the NextPiece after scaling.
	 * 
	 * @return The width of the NextPiece after scaling.
	 */
	public float getScaledWidth()
	{
		return width * scale;
	}

	/**
	 * Get the height of the NextPiece after scaling.
	 * 
	 * @return The height of the NextPiece after scaling.
	 */
	public float getScaledHeight()
	{
		return height * scale;
	}
	
	/**
	 * Get the Piece that will be used next in the Tetris game.
	 * 
	 * @return The Piece that will be used next in the Tetris game.
	 */
	public Piece getNextPiece()
	{
		return nextPiece;
	}
	
	/**
	 * Get the scale that the NextPiece is rendered in.
	 * 
	 * @return The scale that the NextPiece is rendered in.
	 */
	public float getScale()
	{
		return scale;
	}

	/**
	 * Set the scale that the NextPiece will be rendered in.
	 * 
	 * @param scale The scale that the NextPiece will be rendered in.
	 */
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	/**
	 * Tells this instance to generate the next random Piece.
	 */
	public void generateNextPiece()
	{
		nextPiece = Piece.getRandomPiece();
	}
	
	/**
	 * renders the image of the next piece
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			GL.translate(x, y, 0);
			GL.scale(scale, scale, 1);
			
			bgImage.render();
			
			GL.translate(30 / scale, 20 / scale, 0);
			nextPiece.render();

			GL.translate(-30 / scale + 20 / scale, 60 / scale, 0);
			Tetris.getFont().render("Next:", 0, 0, 0, 0.25f, null);
		}
		GL.popMatrix();
	}
}