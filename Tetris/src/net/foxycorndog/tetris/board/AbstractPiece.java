package net.foxycorndog.tetris.board;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;

/**
 * Class used to hold information for each Piece in the Tetris
 * game. There are also methods to manipulate the data and
 * render the data to the screen.
 *
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:17:15 PM
 * @since	v0.1
 * @version	Apr 25, 2013 at 9:55:15 PM
 * @version	v0.1
 */
public abstract class AbstractPiece implements Cloneable
{
	private			int				x, y;
	private			int				width, height;

	private			Color			color;

	private			Board			board;

	private 		Bundle			bundle;

	private	static	Texture			square;

	private			ArrayList<Location>	shape;

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
	 * Default constructor.
	 */
	public AbstractPiece()
	{
		
	}
	
	/**
	 * Create a Tetris Piece given the specified matrix, and
	 * matrix width. The matrix is described with values
	 * that correspond to the rgb of the square located at the
	 * index.
	 *
	 * eg:
	 * Color c = new
	 *         net.foxycorndog.tetris.board.Color(200, 0, 0);<br>
	 *
	 * new Piece([ c, c,<br>
	 *             c, c ], 2);<br>
	 * <br>
	 * would create a red square Piece, and:<br>
	 *
	 * new Piece([ c,<br>
	 * 			   c,<br>
	 *             c,<br>
	 * 			   c ], 1);<br>
	 * <br>
	 * would create a red long Piece.
	 *
	 * @param matrix The array of Colors describing the Piece.
	 * @param width The width of the Piece.
	 */
	public AbstractPiece(ArrayList<Location> shape)
	{
		setShape(shape);
	}
	public AbstractPiece(ArrayList<Location> shape, Color color)
	{
		setColor(color);
		setShape(shape);
	}

	/**
	 * Get the Board that the Piece is located on.
	 *
	 * @return The Board instance that the Piece is located on.
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * Set the Board that the Piece will be located on.
	 *
	 * @param board The Board instance that the Piece will be located on.
	 */
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public ArrayList<Location> getShape()
	{
		return shape;
	}

	/**
	 * Set the Color matrix and width that represents the Piece.
	 * The matrix is described with values that correspond to the
	 * rgb of the square located at the index.
	 *
	 * eg:
	 * Color c = new
	 *         net.foxycorndog.tetris.board.Color(200, 0, 0);<br>
	 *
	 * setMatrix([ c, c,<br>
	 *             c, c ], 2);<br>
	 * <br>
	 * would set the Piece as a red square Piece, and:<br>
	 *
	 * setMatrix([ c,<br>
	 * 			   c,<br>
	 *             c,<br>
	 * 			   c ], 1);<br>
	 * <br>
	 * would set the Piece as a red long Piece.
	 *
	 * @param matrix The array of Colors describing the Piece.
	 * @param width The width of the Piece.
	 */
	public void setShape(ArrayList<Location> shape)
	{
		loadMatrix(shape);
	}

	/**
	 * Set the Color matrix and width that represents the Piece.
	 * The matrix is described with values that correspond to the
	 * rgb of the square located at the index.
	 *
	 * eg:
	 * Color c = new
	 *         net.foxycorndog.tetris.board.Color(200, 0, 0);<br>
	 *
	 * setMatrix([ c, c,<br>
	 *             c, c ], 2);<br>
	 * <br>
	 * would set the Piece as a red square Piece, and:<br>
	 *
	 * setMatrix([ c,<br>
	 * 			   c,<br>
	 *             c,<br>
	 * 			   c ], 1);<br>
	 * <br>
	 * would set the Piece as a red long Piece.
	 *
	 * @param matrix The array of Colors describing the Piece.
	 * @param width The width of the Piece.
	 */
	private void loadMatrix(ArrayList<Location> shape)
	{
		int amountOfSquares = shape.size();

		int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

		for (int i = 0; i < amountOfSquares; i++)
		{
			int x = shape.get(i).getX();
			int y = shape.get(i).getY();

			if (x < minX)
			{
				minX = x;
			}
			if (x > maxX)
			{
				maxX = x;
			}
			if (y < minY)
			{
				minY = y;
			}
			if (y > maxY)
			{
				maxY = y;
			}
		}
		
		for (int i = 0; i < shape.size(); i++)
		{
			Location loc = shape.get(i);
			
			loc.setX(loc.getX() + Math.abs(minX));
			loc.setY(loc.getY() + Math.abs(minY));
		}

		this.width  = maxX - minX;
		this.height = maxY - minY;
		this.shape  = shape;

		bundle = new Bundle(amountOfSquares * 4, 2, true, true);

		if (amountOfSquares <= 0)
		{
			return;
		}
		
		int wid = square.getWidth();
		int hei = square.getHeight();

		bundle.beginEditingVertices();
		{
			for (int i = 0; i < amountOfSquares; i++)
			{
//				 + Math.abs(minX)
				bundle.addVertices(GL.genRectVerts((shape.get(i).getX()) * wid, (shape.get(i).getY()) * hei, wid, hei));
			}
		}
		bundle.endEditingVertices();

		bundle.beginEditingTextures();
		{
			for (int i = 0; i < amountOfSquares; i++)
			{
				bundle.addTextures(GL.genRectTextures(square.getImageOffsets()));
			}
		}
		bundle.endEditingTextures();

		updateColor();
	}
	
	/**
	 * Get the Location of the Piece on the Board relative to the grid.
	 * 
	 * @return The Location of the Piece on the Board relative to the
	 * 		grid.
	 */
	public Location getLocation()
	{
		return new Location(x / board.getGridSpaceSize(), y / board.getGridSpaceSize());
	}

	/**
	 * Get the horizontal location of the Piece in pixels.
	 *
	 * @return The horizontal location of the Piece in pixels.
	 */
	public int getPixelX()
	{
		return x;
	}

	/**
	 * Get the vertical location of the Piece in pixels.
	 *
	 * @return The vertical location of the Piece in pixels.
	 */
	public int getPixelY()
	{
		return y;
	}

	/**
	 * Get the horizontal location of the Piece on the Grid.
	 *
	 * @return The horizontal location of the Piece on the Grid.
	 */
	public int getX()
	{
		if (board == null)
		{
			throw new RuntimeException("You must specify a Board to set its location on.");
		}

		return x / board.getGridSpaceSize();
	}

	/**
	 * Get the vertical location of the Piece on the Grids.
	 *
	 * @return The vertical location of the Piece on the Grid.
	 */
	public int getY()
	{
		if (board == null)
		{
			throw new RuntimeException("You must specify a Board to set its location on.");
		}

		return y / board.getGridSpaceSize();
	}

	/**
	 * Get the amount of segments the Piece is across.
	 *
	 * @return The amount of segments the Piece is across.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Get the amount of segments the Piece is vertically.
	 *
	 * @return The amount of segments the Piece is vertically.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Set the location of this Piece. The location given through
	 * these parameters point to the bottom left of the Piece.
	 *
	 * @param x The new horizontal location of the Piece.
	 * @param y The new vertical location of the Piece.
	 */
	public void setPixelLocation(int x, int y)
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
	public void movePixel(int dx, int dy)
	{
		setPixelLocation(x + dx, y + dy);
	}

	/**
	 * Set the location of the Piece in relation to the Grid spaces.
	 * The origin of the Piece is the bottom left corner.
	 *
	 * For example:<br>
	 * _________<br>
	 * |-------|<br>
	 * |-------|<br>
	 * |-------|<br>
	 * |--P----|<br>
	 * |--P----|<br>
	 * |--P----|<br>
	 * |--P----|<br>
	 * |-------|<br>
	 * |_______|<br>
	 *
	 * The long piece is located at (2, 1)
	 *
	 * @param col The column to set the Piece in.
	 * @param row The row to set the Piece in.
	 */
	public void setLocation(int col, int row)
	{
		if (board == null)
		{
			throw new RuntimeException("You must specify a Board to set its location on.");
		}

		setPixelLocation(col * board.getGridSpaceSize(), row * board.getGridSpaceSize());
	}

	/**
	 * Move the Piece the specified amount of columns and rows relative
	 * to the current location.
	 *
	 * @param cols The number of columns to shift the Piece.
	 * @param rows The number of rows to shift the Piece.
	 */
	public void move(int cols, int rows)
	{
		setLocation(getX() + cols, getY() + rows);
	}

	/**
	 * Move the Piece the specified amount of columns and rows relative
	 * to the current location.
	 *
	 * @param cols The number of columns to shift the Piece.
	 * @param rows The number of rows to shift the Piece.
	 */
	public void moveTo(Location l)
	{
		setLocation(l.getX(), l.getY());
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
			
			bundle.render(GL.QUADS, square);
		}
		GL.popMatrix();
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;

		if (bundle != null)
		{
			updateColor();
		}
	}

	private void updateColor()
	{
		bundle.beginEditingColors();
		{
			for (int i = 0; i < shape.size(); i++)
			{
				bundle.setColors(4 * i, GL.genRectColors(color.getRedf(), color.getGreenf(), color.getBluef(), 1));
			}
		}
		bundle.endEditingColors();
	}

	/**
	 * Rotates the Piece 90 degrees clockwise.
	 */
	public abstract void rotateClockwise();

	/**
	 * Rotates the Piece 90 degrees counter-clockwise. The method for this
	 * is to rotate it clockwise three times. Three rights make a left...
	 */
	public abstract void rotateCounterClockwise();

	/**
	 * Return a clone of the specified Piece instance.
	 *
	 * @return Another instance of a Piece exactly like the preceding one.
	 */
	public AbstractPiece clone()
	{
		AbstractPiece piece = null;

		try
		{
			piece = (AbstractPiece)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		return piece;
	}

	/**
	 * Get the size of each segment of a Piece. (The square texture size)
	 *
	 * @return The size of a segment of a Piece.
	 */
	public static int getSegmentSize()
	{
		return square.getWidth();
	}
}
