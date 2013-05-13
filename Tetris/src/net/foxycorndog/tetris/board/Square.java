package net.foxycorndog.tetris.board;

import net.foxycorndog.tetris.board.Piece;

/**
* File:  			Square.java
* Author:			Henry Thomas Rybolt
* Programming:	  	3rd Hour
* Last Modified: 	May 06, 2013
* Description:      A Square is the elementary component of a Tetrimino it
*                   can rotate and move its tetrimino if its a center
*                   tetrimino than when it acts it moves it tetrimino down
*/
public class Square
{
	private Piece     p;
	private int       x;
	private int       y;
	private boolean   center;
	//private boolean   dead;
	private Color     c;
	private Location  location;

	public void setLocation(Location l)
	{
		location = l;
	}
	/**
	 * deletes this square removing it from its peice
	 */
	public void delete()
	{
		getPiece().delete(this);
	}
	
	/**
	 * creates a square with its color the same as its tetrimino and
	 * records its tetrimino and if its the center of the tetrimino
	 */
	public Square(Color c, Piece p, boolean center, Location l) 
	{
		location    = l;
		this.c      = c;
		this.p      = p;
		this.center = center;
	}

	/**
	 * rotates the tetrimino that this square belongs to clockwise if 
	 * possible
	 */
	public void rotateC()
	{
		p.rotateC();
	}

	/**
	 * rotates the tetrimino that this square belongs to 
	 * counter-clockwise if possible
	 */
	public void rotateCC()
	{
		p.rotateCC();
	}


	/**
	 * moves the tetrimino that this square belongs to left if possible
	 */
	public void moveLeft()
	{
		p.move(Piece.LEFT);
	}

	/**
	 * moves the tetrimino that this square belongs to right if possible
	 */
	public void moveRight()
	{
		p.move(Piece.RIGHT);
	}

	/**
	 * moves the tetrimino that this square belongs to down if possible
	 */
	public void moveDown()
	{
		p.move(Piece.DOWN);
	}

	/**
	 * moves the tetrimino that this square belongs to down if possible
	 * and if this square is the center of a tetrimino this condition 
	 * prevents all of the four squares a tetrimino has to all move the
	 * tetrimino down without this condition the tetrimino would move
	 * four squares down every step
	 */
	public void act()
	{
		if(center == true)//&& ! getPiece().isDead())
		{
			p.move(Piece.DOWN);
		}
	}

	/**
	 * returns the board that this square belongs to
	 */
	public Board getBoard() 
	{
		return p.getBoard();
	}
	
	/**
	 * moves the square to location next on the board
	 */
	public void moveTo(Location next) 
	{
		getBoard().getBoss().moveTo(this, next);
	}

	/**
	 * returns the peice that this square belongs to
	 */
	public Piece getPiece() 
	{
		return p;
	}

	/**
	 * removes this piece from the board is temporary unlike deletion
	 * which is permanent
	 */
	public void removeFromBoard() 
	{
		getBoard().getBoss().remove(this);
	}
	
	public Location getLocation()
	{
		return location;
	}
}
