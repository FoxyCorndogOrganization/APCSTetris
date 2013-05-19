package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.components.TextField;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;

/**
 * Class that is used to edit the multiplayer settings.
 * 
 * @author	Braden Steffaniak
 * @since	May 18, 2013 at 11:56:21 PM
 * @since	v1.0
 * @version	May 18, 2013 at 11:56:21 PM
 * @version	v1.1
 */
public class MultiplayerMenu extends Menu
{
	private	TextField ipField;
	
	/**
	 * Create a MultiplayerMenu instance.
	 */
	public MultiplayerMenu()
	{
		ipField = new TextField(null);
//		ipField.setSize(300, 80);
		ipField.setAlignment(TextField.CENTER, TextField.CENTER);
		ipField.setFont(Tetris.getFont());
		
		try
		{
			ipField.setBackgroundImage("res/images/GUI/TextField.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#loop()
	 */
	public void loop()
	{
		
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#render()
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			ipField.render();
		}
		GL.popMatrix();
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#dispose()
	 */
	public void dispose()
	{
		
	}
}