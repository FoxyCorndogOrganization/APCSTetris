package net.foxycorndog.tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.openal.Sound;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.tetris.board.AbstractBoard;
import net.foxycorndog.tetris.board.Board;
import net.foxycorndog.tetris.board.Color;
import net.foxycorndog.tetris.menu.MainMenu;
import net.foxycorndog.tetris.sidebar.Sidebar;
import net.foxycorndog.tetris.sound.SoundLibrary;

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
	private float						bgColor;
	
	private Board						board;
	private Sidebar						sidebar;
	
	private MainMenu					mainMenu;
	
	private static Font					font;
	
	public static final SoundLibrary	SOUND_LIBRARY = createSoundLibrary();
	
	/**
	 * Main method that creates a Frame and starts up the Tetris
	 * game.
	 * 
	 * @param args The unused command line arguments.
	 */
	public static void main(String args[])
	{
		Frame.create((int)(390 * 1.4), (int)(510 * 1.4));
		Frame.setResizable(false);
		Frame.setTitle("Tetris");
		Frame.setTargetFPS(60);
		
		Tetris game = new Tetris();
		
		game.start();
	}
	
	public void playGame()
	{
		mainMenu.dispose();
		mainMenu = null;
		
		board    = new Board(10, 20, 10, this);
		sidebar  = new Sidebar();
		
		board.addListener(sidebar.getScoreboard());
		board.addListener(sidebar.getLinesCompleted());
		
		board.newGame();
		board.move(50, 50);
		
//		board.addPiece(Piece.getRandomPiece(), 5, 5);
	}
	
	/**
	 * The method to do the game's OpenGL initialization.
	 */
	public void init()
	{
		font = new Font("res/images/font.gif", 15, 6, new char[]
		{
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'A', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'a', '&', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '(', '$', ':', '.', ',', '!', '?', ')',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		});
		
		bgColor  = 0;
		
		mainMenu = new MainMenu(this);
		
		try
		{
			SOUND_LIBRARY.addSound("pop.wav", new Sound("res/sounds/pop.wav"));
			SOUND_LIBRARY.addSound("lineremoved.wav", new Sound("res/sounds/lineremoved.wav"));
			SOUND_LIBRARY.addSound("lose.wav", new Sound("res/sounds/lose.wav"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the Sidebar of the Tetris game.
	 * 
	 * @return The Sidebar of the Tetris game.
	 */
	public Sidebar getSidebar()
	{
		return sidebar;
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
		
		int x = Mouse.getDisplayX() - Frame.getX();
		int y = Mouse.getDisplayY() - Frame.getY();
		
		int closeX = Frame.getWidth() - 50;
		int closeY = -20;
		
		int closeWidth  = 82;
		int closeHeight = 50;
		
		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		
		if (x > closeX && x < closeX + closeWidth && y > closeY && y < closeY + closeHeight)
		{
//			Frame.setLocation(Frame.getX() + dx, Frame.getY() + dy);
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
	
	private static final SoundLibrary createSoundLibrary()
	{
		SoundLibrary library = new SoundLibrary();
		
		return library;
	}
}
