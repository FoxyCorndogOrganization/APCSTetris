package net.foxycorndog.tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.FrameEvent;
import net.foxycorndog.jfoxylib.events.FrameListener;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.network.Client;
import net.foxycorndog.jfoxylib.network.Packet;
import net.foxycorndog.jfoxylib.openal.Sound;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.jfoxyutil.Queue;
import net.foxycorndog.tetris.board.AbstractBoard;
import net.foxycorndog.tetris.board.Board;
import net.foxycorndog.tetris.board.Color;
import net.foxycorndog.tetris.menu.MainMenu;
import net.foxycorndog.tetris.multiplayer.GamePacket;
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
	private boolean 					rUp, gUp, bUp;
	
	private int							r, g, b;
	
	private	float						scale;
	private float						counter;
	
	private	Button						backButton;
	
	private Board						board;
	private Sidebar						sidebar;
	
	private MainMenu					mainMenu;
	
	private	Queue<Packet>				packetQueue;
	
	private static Font					font, fancyFont;
	
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
		Frame.setResizable(true);
		Frame.setTitle("Tetris");
		Frame.setTargetFPS(60);
		
		Tetris game = new Tetris();
		
		game.start();
	}
	
	/**
	 * Tell the game to start.
	 */
	public void playGame()
	{
		createGame();
		
		board.newGame();
	}
	
	/**
	 * Create a game that is capable of being played, but don't play it
	 * yet.
	 */
	private void createGame()
	{
		mainMenu.dispose();
		mainMenu   = null;
		
		board      = new Board(10, 20, 10, this);
		board.setScale(3f);
		
		sidebar    = new Sidebar();
		
		backButton = new Button(null);
		
//		try
//		{
//			backButton.setImage("res/images/back.png");
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		
		backButton.setFont(fancyFont);
		backButton.setText("Back");
		
		board.addListener(sidebar.getScoreboard());
		board.addListener(sidebar.getLinesCompleted());
		
		arrangeComponents();
		
		backButton.addButtonListener(new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				Button source = event.getSource();
				
				if (source == backButton)
				{
					quitGame();
					openMainMenu();
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		});
	}
	
	/**
	 * Arrange all of the Components to fit in the Frame in the
	 * most optimal way.
	 */
	private void arrangeComponents()
	{
		int sidebarOffsetX = 30;
		
		float wid = board.getScaledWidth() + sidebar.getScaledWidth() + sidebarOffsetX;
		float hei = board.getScaledHeight();
		
		wid *= scale;
		hei *= scale;
		
		int boardX = Math.round(Frame.getWidth() / 2f - wid / 2f);
		int boardY = Math.round(Frame.getHeight() / 2f - hei / 2f);
		
		boardY = boardY < 0 ? 0 : boardY;
		
		board.setLocation(boardX, boardY);
		
		sidebar.setLocation(board.getX() + Math.round(board.getScaledWidth()) + sidebarOffsetX,
				board.getY() + Math.round(board.getScaledHeight() / 2));
		
		backButton.setLocation(board.getX() + Math.round(board.getScaledWidth()) + sidebarOffsetX,
				board.getY());
	}
	
	/**
	 * Quit the Tetris game.
	 */
	public void quitGame()
	{
		board.quitGame();
		board = null;
		
		backButton.dispose();
		backButton = null;
	}
	
	/**
	 * Get the scale of the tetris game.
	 * 
	 * @return The scale of the tetris game.
	 */
	public float getScale()
	{
		return scale;
	}
	
	/**
	 * The method to do the game's OpenGL initialization.
	 */
	public void init()
	{
		try
		{
			font = new Font("res/images/font.gif", 15, 6, new char[]
			{
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'A', 'a', 'b',
				'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'a', '&', '1', '2', '3',
				'4', '5', '6', '7', '8', '9', '0', '(', '$', ':', '.', ',', '!', '?', ')',
				';', '"', '\'', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
			});
			
			fancyFont = new Font("res/images/fancyFont2.png", 20, 4, new char[]
			{
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '&', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', '0', '(', '$', ':', '.', ',', '!', '?', ')', ';', '"', '\'', ' ', ' ', ' ', ' ', ' ', ' '
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		packetQueue = new Queue<Packet>();
		
		try
		{
			SOUND_LIBRARY.addSound("pop.wav", new Sound("res/sounds/pop.wav"));
			SOUND_LIBRARY.addSound("lineremoved.wav", new Sound("res/sounds/lineremoved.wav"));
			SOUND_LIBRARY.addSound("lose.wav", new Sound("res/sounds/lose.wav"));
			SOUND_LIBRARY.addSound("music.wav", new Sound("res/sounds/music.wav"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		r = 235;
		g = 235;
		b = 235;
		
		Frame.addFrameListener(new FrameListener()
		{
			public void frameResized(FrameEvent e)
			{
				if (board != null)
				{
					arrangeComponents();
				}
			}
		});
		
		openMainMenu();
	}
	
	/**
	 * Process the specified packet given. Does whatever is supposed to
	 * be done with the Packet.
	 * 
	 * @param packet The Packet to process.
	 */
	private void processPacket(Packet packet)
	{
		int id = packet.getId();
		
		if (id == GamePacket.LINES_COMPLETED)
		{
			int numLines = ((Integer)packet.getData()).intValue();
			
			board.addStraightLines(numLines);
		}
		else if (id == GamePacket.GAME_LOST)
		{
			System.out.println("you fn won!");
		}
	}
	
	/**
	 * Add the specified Packet to the Queue of Packets to be processed
	 * by the game.
	 * 
	 * @param packet The Packet to add to the Queue.
	 */
	public void addPacketToQueue(Packet packet)
	{
		packetQueue.enqueue(packet);
	}
	
	/**
	 * Open the MainMenu into the main view.
	 */
	public void openMainMenu()
	{
		board   = null;
		sidebar = null;
		
		mainMenu = new MainMenu(this);
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
		GL.setClearColor(getRf() / 2, getGf() / 2, getBf() / 2, 1);
		
		if (mainMenu != null)
		{
			mainMenu.render();
		}
		else
		{
			GL.pushMatrix();
			{
				float bWidth  = board.getScaledWidth() + 200;
				float bHeight = board.getScaledHeight();
				
				
				
				if (Frame.getWidth() < bWidth || Frame.getHeight() < bHeight)
				{
					float minDim = Math.min(Frame.getWidth(), Frame.getHeight());
				
					float scaleX = minDim / (bWidth);
					float scaleY = minDim / (bHeight);
					
					scale = Math.min(scaleX, scaleY);
					
					GL.scale(scale, scale, 1);
				}
				else
				{
					scale = 1;
				}
				
				arrangeComponents();
				
				board.render();
				sidebar.render();
				backButton.render();
			}
			GL.popMatrix();
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
		float delta = 60f / Frame.getFPS();
		
		if (!Float.isInfinite(delta) && !Float.isNaN(delta))
		{
			counter += delta;
		}
		
		if (counter >= 1)
		{
			updateColor();
			
			counter %= 1;
		}
		
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
		
		while (!packetQueue.isEmpty())
		{
			Packet packet = (Packet)packetQueue.dequeue();
			
			processPacket(packet);
		}
	}
	
	/**
	 * Set the color that the Menu will be rendered in.
	 */
	private void updateColor()
	{
		r = rUp ? r + 1 : r - 1;
		r = r >= 256 ? 255 : r;
		r = r <= 100 ? 100 : r;
		
		g = gUp ? g + 1 : g - 1;
		g = g >= 256 ? 255 : g;
		g = g <= 100 ? 100 : g;
		
		b = bUp ? b + 1 : b - 1;
		b = b >= 256 ? 255 : b;
		b = b <= 100 ? 100 : b;
		
		if ((int)(Math.random() * 100) == 0)
		{
			rUp = !rUp;
		}
		if ((int)(Math.random() * 100) == 0)
		{
			gUp = !gUp;
		}
		if ((int)(Math.random() * 100) == 0)
		{
			bUp = !bUp;
		}
	}
	
	/**
	 * Connect the Tetris game to the Client.
	 * 
	 * @param ip The IP of the Server to connect to.
	 * @param port The port of the Server to connect to.
	 */
	public void connectClient(String ip, int port)
	{
		mainMenu.dispose();
		
		createGame();
		
		board.connectClient(ip, port);
	}
	
	/**
	 * Connect the Tetris game to the Client.
	 * 
	 * @param client The Client to use.
	 */
	public void connectClient(Client client)
	{
		mainMenu.dispose();
		
		createGame();
		
		board.setClient(client);
	}
	
	/**
	 * Create a server for Clients to connect to.
	 * 
	 * @param port The port to create the Server on.
	 */
	public void createServer(int port)
	{
		mainMenu.dispose();
		
		createGame();
		
		board.createServer(port);
	}
	
	/**
	 * Get the Board that the Tetris game is using.
	 * 
	 * @return The Board that the Tetris game is using.
	 */
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Get the red component of the menu.
	 * 
	 * @return The red component of the menu.
	 */
	public int getR()
	{
		return r;
	}

	/**
	 * Get the green component of the menu.
	 * 
	 * @return The green component of the menu.
	 */
	public int getG()
	{
		return g;
	}

	/**
	 * Get the blue component of the menu.
	 * 
	 * @return The blue component of the menu.
	 */
	public int getB()
	{
		return b;
	}

	/**
	 * Get the red float component of the menu.
	 * 
	 * @return The red float component of the menu.
	 */
	public float getRf()
	{
		return r / 255f;
	}

	/**
	 * Get the green float component of the menu.
	 * 
	 * @return The green float component of the menu.
	 */
	public float getGf()
	{
		return g / 255f;
	}

	/**
	 * Get the blue float component of the menu.
	 * 
	 * @return The blue float component of the menu.
	 */
	public float getBf()
	{
		return b / 255f;
	}
	
	/**
	 * Get the Tetris game default Font instance.
	 * 
	 * @return The Tetris game default Font instance.
	 */
	public static Font getFont()
	{
		return fancyFont;
	}
	
	/**
	 * Create the final SoundLibrary instance.
	 * 
	 * @return The final SoundLibrary instance.
	 */
	private static final SoundLibrary createSoundLibrary()
	{
		SoundLibrary library = new SoundLibrary();
		
		return library;
	}
}
