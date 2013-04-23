package net.foxycorndog.tetris.board;

import java.io.IOException;

import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * Class used to hold information for each Piece in the Tetris
 * game. There are also methods to manipulate the data and
 * render the data to the screen.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:17:15 PM
 * @since	v0.1
 * @version	Apr 22, 2013 at 11:17:15 PM
 * @version	v0.1
 */
public class Piece
{
	private 		int		rotation;
	private			int		x, y;
	
	private 		Bundle	bundle;
	
	private	static	Texture	square;
	
	static
	{
		try
		{
			square = new Texture("res/images/square.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a Tetris Piece given the specified matrix, and
	 * matrix width. The matrix is described with values
	 * that correspond to the rgb of the square located at the
	 * index.
	 * 
	 * eg:
	 * Color c = new
	 *         net.foxycorndog.tetris.board.Color(200, 0, 0);
	 * 
	 * new Piece([ c, c,
	 *             c, c ], 2);
	 * 
	 * would create a red square Piece, and;
	 * 
	 * new Piece([ c,
	 * 			   c,
	 *             c,
	 * 			   c ], 1);
	 * 
	 * would create a red long Piece.
	 * 
	 * @param matrix The array of Colors describing the Piece.
	 * @param width The width of the Piece.
	 */
	public Piece(Color matrix[], int width)
	{
		bundle = new Bundle(1, 2, true, false);
		
		int height = matrix.length / width;
		
		int num = 0;
		
		for (int i = 0; i < matrix.length; i++)
		{
			if (matrix[i] != null)
			{
				num++;
			}
		}
		
		bundle = new Bundle(num * 4, 2, true, false);
		
		int wid = square.getWidth();
		int hei = square.getHeight();
		
		bundle.beginEditingVertices();
		{
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					if (matrix[x + y * width] != null)
					{
						bundle.addVertices(GL.genRectVerts(x * wid, y * hei, wid, hei));
					}
				}
			}
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					if (matrix[x + y * width] != null)
					{
						bundle.addTextures(GL.genRectTextures(square.getImageOffsets()));
					}
				}
			}
		}
		bundle.endEditingTextures();
	}
	
	/**
	 * Set the location of this Piece. The location given through
	 * these parameters point to the bottom left of the Piece.
	 * 
	 * @param x The new horizontal location of the Piece.
	 * @param y The new vertical location of the Piece.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Translate the Piece the specified amount. A positive dx value
	 * moves the Piece to the right. A positive dy value moves the Piece
	 * upward.
	 * 
	 * @param dx The horizontal delta value to move the Piece.
	 * @param dy The vertical delta value to move the Piece.
	 */
	public void move(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	/**
	 * Set the texture magnification scale method to GL.NEAREST so that
	 * the textures look correct, then render the Piece.
	 */
	public void render()
	{
		GL.setTextureScaleMagMethod(GL.NEAREST);
		
		GL.pushMatrix();
		{
			GL.translate(x, y, 0);
			GL.rotate(0, 0, rotation);
			
			bundle.render(GL.QUADS, square);
		}
		GL.popMatrix();
	}
	
	/**
	 * Rotates the Piece 90 degrees clockwise.
	 */
	public void rotateClockwise()
	{
		rotation += 90;
		
		rotation %= 360;
	}
	
	/**
	 * Rotates the Piece 90 degrees counter-clockwise. The method for this
	 * is to rotate it clockwise three times. Three rights make a left...
	 */
	public void rotateCounterClockwise()
	{
		rotateClockwise();
		rotateClockwise();
		rotateClockwise();
	}
}
