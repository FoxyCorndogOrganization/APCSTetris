package net.foxycorndog.tetris;

import java.io.File;
import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.tetris.board.Board;
import net.foxycorndog.tetris.board.Color;
import net.foxycorndog.tetris.board.Piece;
import net.foxycorndog.tetris.menu.MainMenu;
import net.foxycorndog.tetris.sidebar.SideBar;

/**
 * Main class and entry point for the Tetris game.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:17:19 PM
 * @since	v0.1
 * @version	Apr 22, 2013 at 11:17:19 PM
 * @version	v0.1
 */
public class Tetris extends GameStarter
{
	private float		bgColor;
	
	private Board		board;
	private SideBar		sidebar;
	
	private MainMenu	mainMenu;
	
	private static Font	font;
	
	/**
	 * Main method that creates a Frame and starts up the Tetris
	 * game.
	 * 
	 * @param args The unused command line arguments.
	 */
	public static void main(String args[])
	{
		Frame.create((int)(390 * 1.4), (int)(510 * 1.4));
		
		Tetris game = new Tetris();
		
		game.start();
	}
	
	public void playGame()
	{
		mainMenu = null;
		
		board    = new Board(10, 20, 10);
		sidebar  = new SideBar();
		
		board.newGame();
		
//		board.addPiece(Piece.getRandomPiece(), 5, 5);
	}
	
	/**
	 * The method to do the game's OpenGL initialization.
	 */
	public void init()
	{
		Frame.setTitle("Tetris");
		Frame.setTargetFPS(60);
		Frame.setResizable(true);
		
		font = new Font("res/images/font.gif", 15, 6, new char[]
		{
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '\\', '\\', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '\\', '\\', '&', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '(', '$', '\\', '.', ',', '!', '?', ')',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		});
		
		bgColor  = 0;
		
		mainMenu = new MainMenu(this);
	}

	/**
	 * Method used to render the 2D shapes to the screen each frame.
	 */
	public void render2D()
	{
		if (mainMenu != null)
		{
			mainMenu.render();
		}
		else
		{
			board.render();
			sidebar.render();
		}
	}

	/**
	 * Method to render the 3D shapes to the screen each frame.
	 */
	public void render3D()
	{
		
	}

	/**
	 * Method to calculate logic each frame.
	 */
	public void loop()
	{
		if (mainMenu != null)
		{
			mainMenu.loop();
		}
		else
		{
			board.loop();
		}
	}
	
	/**
	 * Get the Tetris game default Font instance.
	 * 
	 * @return The Tetris game default Font instance.
	 */
	public static Font getFont()
	{
		return font;
	}
}
