package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;

/**
 * Class used to choose whether to play singleplayer or
 * multi-player.
 * 
 * @author	Braden Steffaniak
 * @since	May 18, 2013 at 11:57:20 PM
 * @since	v1.0
 * @version	May 18, 2013 at 11:57:20 PM
 * @version	v1.1
 */
public class PlayMenu extends Menu
{
	private	Button	singleplayerButton, multiplayerButton;
	
	private	Menu	multiplayerMenu;
	
	private	Tetris	tetris;
	
	/**
	 * 
	 * @param tetris
	 */
	public PlayMenu(final Tetris tetris)
	{
		this.tetris = tetris;
		
		singleplayerButton = new Button(null);
		singleplayerButton.setAlignment(Button.CENTER, Button.CENTER);
		singleplayerButton.setLocation(0, 60);
		
		multiplayerButton = new Button(null);
		multiplayerButton.setAlignment(Button.CENTER, Button.CENTER);
		multiplayerButton.setLocation(0, -60);
		
		try
		{
			singleplayerButton.setTexture("res/images/singleplayer.png");
			multiplayerButton.setTexture("res/images/multiplayer.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		ButtonListener listener = new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				Button source = event.getSource();
				
				if (source == singleplayerButton)
				{
					tetris.playGame();
				}
				else if (source == multiplayerButton)
				{
					openMultiplayerMenu();
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}

			public void buttonUp(ButtonEvent event)
			{
				
			}

			public void buttonDown(ButtonEvent event)
			{
				
			}
		};
		
		singleplayerButton.addButtonListener(listener);
		multiplayerButton.addButtonListener(listener);
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#loop()
	 */
	public void loop()
	{
		if (multiplayerMenu != null)
		{
			multiplayerMenu.loop();
		}
	}
	
	/**
	 * Open the MultiplayerMenu into the main view.
	 */
	private void openMultiplayerMenu()
	{
		multiplayerMenu = new MultiplayerMenu(tetris);
		
		leavePlayMenuFocus();
	}

	/**
	 * Close the options menu and return to the PlayMenu.
	 */
	public void closeMultiplayerMenu()
	{
		multiplayerMenu.dispose();
		multiplayerMenu = null;
		
		returnToPlayMenu();
	}

	/**
	 * Return to the PlayMenu and restore its functionality.
	 */
	private void returnToPlayMenu()
	{
		singleplayerButton.setEnabled(true);
		multiplayerButton.setEnabled(true);
	}
	
	/**
	 * Take away the focus from the PlayMenu.
	 */
	private void leavePlayMenuFocus()
	{
		singleplayerButton.setEnabled(false);
		multiplayerButton.setEnabled(false);
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#render()
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			if (multiplayerMenu != null)
			{
				multiplayerMenu.render();
			}
			else
			{
				GL.scale(0.5f, 0.5f, 1);
				
				singleplayerButton.render();
				multiplayerButton.render();
			}
		}
		GL.popMatrix();
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#dispose()
	 */
	public void dispose()
	{
		singleplayerButton.dispose();
		multiplayerButton.dispose();
		
		if (multiplayerMenu != null)
		{
			multiplayerMenu.dispose();
		}
	}
}