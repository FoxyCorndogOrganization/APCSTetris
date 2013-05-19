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
	private			int				unsetX, unsetY;
	private			int				offsetX, offsetY;
	
	private			Color			color;
	
	private			Location		center;

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
	
	/**
	 * Create an Abstract Piece from the specified shape and Color.
	 * 
	 * @param shape The ArrayList of locations describing the shape.
	 * @param color The Color instance describing the Color of the Piece.
	 */
	public AbstractPiece(ArrayList<Location> shape, Color color)
	{
		setColor(color);
		setShape(shape);
	}
	
	/**
	 * Create an Abstract Piece from the specified shape and Color.
	 * 
	 * @param shape The Array of locations describing the shape.
	 * @param color The Color instance describing the Color of the Piece.
	 */
	public AbstractPiece(Location shape[], Color color)
	{
		setColor(color);
		
		ArrayList<Location> locs = new ArrayList<Location>();
		
		for (int i = 0; i < shape.length; i++)
		{
			locs.add(shape[i]);
		}
		
		setShape(locs);
	}
	
	/**
	 * Calculate the center of the Piece.
	 */
	public void calculateCenter()
	{
		center = new Location(width / 2, height / 2);
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
		
		update();
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
		
		bundle = new Bundle(amountOfSquares * 4, 2, true, false);

		if (amountOfSquares <= 0)
		{
			return;
		}
		
		int avgX = 0;
		int avgY = 0;
		
//		for (int i = 0; i < shape.size(); i++)
//		{
//			Location loc = shape.get(i);
//			shape.set(i, new Location(loc.getX() - minX, loc.getY() - minY));
//			
//			loc = shape.get(i);
//			
//			avgX += loc.getX() + 1;
//			avgY += loc.getY() + 1;
////			loc.setX(loc.getX() - minX);
////			loc.setY(loc.getY() - minY);
//		}
//		
//		unsetX = minX;
//		unsetY = minY;
		
		offsetX = -minX;
		offsetY = -minY;
		
		maxX = maxX - minX;
		maxY = maxY - minY;
		minX = 0;
		minY = 0;
		
		this.width  = maxX - minX + 1;
		this.height = maxY - minY + 1;
		this.shape  = shape;
		
		avgX /= shape.size();
		avgY /= shape.size();
		
		avgX -= 1;
		avgY -= 1;
		
//		avgX = width  / 2;
//		avgY = height / 2;
		
//		avgX -= width % 2 == 0 ? 1 : 0;
//		avgY -= height % 2 == 0 ? 1 : 0;
		
//		System.out.println(avgX + ", " + avgY);
		
//		calculateCenter();
		center = new Location(avgX, avgY);
		
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
		
		update();
	}
	
	/**
	 * Get the horizontal offset of the shape matrix.
	 * 
	 * @return The horizontal offset of the shape matrix.
	 */
	public int getOffsetX()
	{
		return offsetX;
	}
	
	/**
	 * Get the vertical offset of the shape matrix.
	 * 
	 * @return The vertical offset of the shape matrix.
	 */
	public int getOffsetY()
	{
		return offsetY;
	}
	
	/**
	 * Update the location of the Board is not null.
	 */
	private void update()
	{
		if (board != null)
		{
			move(unsetX, unsetY, true);
			
			unsetX = 0;
			unsetY = 0;
		}
	}
	
	/**
	 * Get the offset for the center of the Piece.
	 * 
	 * @return The offset for the center of the Piece.
	 */
	public Location getCenter()
	{
		return center;
	}
	
	/**
	 * Get the offset for the center of the Piece, but opposite.
	 * (y, x)
	 * 
	 * @return The opposite offset for the center of the Piece. (y, x)
	 */
	public Location getCenterOpposite()
	{
		return new Location(center.getY(), -center.getX());
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
	public boolean move(int cols, int rows)
	{
		return move(cols, rows, false);
	}
	
	/**
	 * Move the Piece according to the positional vector added.
	 * 
	 * @param cols The horizontal amount to move the Piece.
	 * @param rows The vertical amount to move the Piece.
	 * @param force Whether or not to force the move.
	 * @return Whether the Piece was successfully moved or not.
	 */
	private boolean move(int cols, int rows, boolean force)
	{
		return move(new Location(cols, rows), force);
	}
	
	/**
	 * moves the piece according to the positional vector added
	 */
	public boolean move(Location l)
	{
		return move(l, false);
	}
	
	/**
	 * Move the Piece according to the positional vector added.
	 * 
	 * @param l The vector to be added.
	 * @param force Whether or not to force the move.
	 * @return Whether the Piece was successfully moved or not.
	 */
	private boolean move(Location l, boolean force)
	{
		if (force)
		{
			setLocation(getX() + l.getX(), getY() + l.getY());
			
			return true;
		}
		
		boolean ableToMove = true;

		for (int i = 0; i < getShape().size(); i++)
		{
			Location next = getShape().get(i).add(getLocation()).add(l);

			if (!spaceIsFree(next))
			{
				ableToMove = false;
			}
		}

		if (ableToMove)
		{
			setLocation(getX() + l.getX(), getY() + l.getY());
		}
		
		return ableToMove;
	}
	
	/**
	 * retruns true if the space is free of other tetriminos else false
	 */
	public boolean spaceIsFree(Location next)
	{
		if (!board.isValid(next.getX(), next.getY()))
		{
			return false;
		}
		
		Piece ps[] = board.getPieces(next);
		
		for (int i = 0; i < ps.length; i++)
		{
			Piece p = ps[i];
			
			if (p != this)
			{
				return false;
			}
		}

		return true;
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
			GL.setColor(color.getRedf(), color.getGreenf(), color.getBluef(), 1);
			
			bundle.render(GL.QUADS, square);
		}
		GL.popMatrix();
	}

	/**
	 * Get the Color instance of the Piece.
	 * 
	 * @return The Color instance of the Piece.
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Set the new Color instance of the Piece
	 * 
	 * @param color The Color new instance of the Piece.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	/**
	 * Rotates the Piece 90 degrees clockwise.
	 * 
	 * @return Whether the Piece rotated successfully.
	 */
	public abstract boolean rotateClockwise();

	/**
	 * Rotates the Piece 90 degrees counter-clockwise. The method for this
	 * is to rotate it clockwise three times. Three rights make a left...
	 * 
	 * @return Whether the Piece rotated successfully.
	 */
	public abstract boolean rotateCounterClockwise();

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
