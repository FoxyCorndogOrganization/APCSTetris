package net.foxycorndog.tetris;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.tetris.board.Board;
import net.foxycorndog.tetris.board.Piece;
import net.foxycorndog.tetris.menu.MainMenu;

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
	
	private MainMenu	mainMenu;
	
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
	
	/**
	 * The method to do the game's OpenGL initialization.
	 */
	public void init()
	{
		Frame.setTitle("Tetris");
		Frame.setTargetFPS(60);
		Frame.setResizable(true);
		
		bgColor  = 0;
		
		board    = new Board(10, 20, 10);
		
		mainMenu = new MainMenu();
	}

	/**
	 * Method used to render the 2D shapes to the screen each frame.
	 */
	public void render2D()
	{
		mainMenu.render();
//			GL.scale(3, 3, 1);
			
//			longPiece.render();
		
		//editing the rendr2d method!!
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
		mainMenu.loop();
		
		board.loop();
	}
}
