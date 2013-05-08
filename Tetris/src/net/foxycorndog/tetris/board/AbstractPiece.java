package net.foxycorndog.tetris.board;

import java.io.IOException;

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
	private 		int				rotation;
	private			int				x, y;
	private			int				width, height;
	
	private			Board			board;
	
	private 		Bundle			bundle;
	
	private 		Color			matrix[];
	
	private	static	Texture			square;
	
	private static	Piece			pieces[];
	
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
		
		Color matrix[] = null;
		
		pieces    = new Piece[7];
		
		// Long Piece
		matrix = new Color[]
		{
			Color.MAGENTA,
			Color.MAGENTA,
			Color.MAGENTA,
			Color.MAGENTA,
		};
		
		pieces[0] = new Piece(matrix, 1);
		
		// Square Piece
		matrix = new Color[]
		{
			Color.ORANGE, Color.ORANGE,
			Color.ORANGE, Color.ORANGE,
		};
		
		pieces[1] = new Piece(matrix, 2);
		
		// L Piece
		matrix = new Color[]
		{
			Color.GREEN, null,
			Color.GREEN, null,
			Color.GREEN, Color.GREEN,
		};
		
		pieces[2] = new Piece(matrix, 2);
		
		// Backwards L Piece
		matrix = new Color[]
		{
			null,       Color.BLUE,
			null,       Color.BLUE,
			Color.BLUE, Color.BLUE,
		};
		
		pieces[3] = new Piece(matrix, 2);
		
		// S Piece
		matrix = new Color[]
		{
			null,         Color.YELLOW, Color.YELLOW,
			Color.YELLOW, Color.YELLOW, null,
		};
		
		pieces[4] = new Piece(matrix, 3);
		
		// Backwards S Piece
		matrix = new Color[]
		{
			Color.CYAN, Color.CYAN, null,
			null,       Color.CYAN, Color.CYAN,
		};
		
		pieces[5] = new Piece(matrix, 3);
		
		// T Piece
		matrix = new Color[]
		{
			Color.RED, Color.RED, Color.RED,
			null,      Color.RED, null,
		};
		
		pieces[6] = new Piece(matrix, 3);
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
	public AbstractPiece(Color matrix[], int width)
	{
		loadMatrix(matrix, width);
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
	
	/**
	 * Get the Color matrix that represents the Piece. The width of the
	 * matrix is defined by getWidth();
	 * 
	 * @return The Color matrix that represents the Piece.
	 */
	public Color[] getMatrix()
	{
		return matrix;
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
	public void setMatrix(Color matrix[], int width)
	{
		loadMatrix(matrix, width);
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
	private void loadMatrix(Color matrix[], int width)
	{
		this.width  = width;
		this.height = matrix.length / width;
		this.matrix = matrix;
		
		int height = matrix.length / width;
		
		int num = 0;
		
		for (int i = 0; i < matrix.length; i++)
		{
			if (matrix[i] != null)
			{
				num++;
			}
		}
		
		bundle = new Bundle(num * 4, 2, true, true);
		
		int wid = square.getWidth();
		int hei = square.getHeight();
		
		bundle.beginEditingVertices();
		{
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					if (matrix[x + (height - y - 1) * width] != null)
					{
						bundle.addVertices(GL.genRectVerts(x * wid, y * hei, wid, hei));
					}
				}
			}
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					if (matrix[x + (height - y - 1) * width] != null)
					{
						bundle.addTextures(GL.genRectTextures(square.getImageOffsets()));
					}
				}
			}
		}
		bundle.endEditingTextures();
		
		bundle.beginEditingColors();
		{
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					if (matrix[x + (height - y - 1) * width] != null)
					{
						Color color = matrix[x + (height - y - 1) * width];
						
						bundle.addColors(GL.genRectColors(color.getRedf(), color.getGreenf(), color.getBluef(), 1));
					}
				}
			}
		}
		bundle.endEditingColors();
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
	
	/**
	 * Get the array of Pieces. Contains all seven of the original Piece
	 * shapes.
	 * 
	 * @return The array of the seven original Pieces.
	 */
	public static AbstractPiece[] getPieces()
	{
		return pieces.clone();
	}
	
	/**
	 * Get a brand new instance of one of the seven original Pieces.
	 * 
	 * @return A brand new instance of one of the seven original Pieces.
	 */
	public static AbstractPiece getRandomPiece()
	{
		int index = (int)(Math.random() * pieces.length);
		
		return pieces[index].clone();
	}
}
