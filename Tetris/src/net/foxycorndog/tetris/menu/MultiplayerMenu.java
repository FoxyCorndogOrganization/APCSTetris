package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.components.TextField;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.network.Client;
import net.foxycorndog.jfoxylib.network.NetworkException;
import net.foxycorndog.jfoxylib.network.Packet;
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
	private	boolean		startGame;
	
	private	String		status;
	
	private	Image		joinServerImage;
	
	private	Button		createServerButton, joinButton;
	
	private	TextField	ipField;
	
	private	Client		client;
	
	private	Tetris		tetris;
	
	/**
	 * Create a MultiplayerMenu instance.
	 */
	public MultiplayerMenu(final Tetris tetris)
	{
		this.tetris = tetris;
		
		ipField = new TextField(null);
//		ipField.setSize(300, 80);
		ipField.setScale(0.75f);
		ipField.setAlignment(TextField.CENTER, TextField.CENTER);
		ipField.setFont(Tetris.getFont());
		ipField.setLocation(0, 10);
		
		joinServerImage = new Image(null);
		joinServerImage.setAlignment(Image.CENTER, Image.CENTER);
		joinServerImage.setLocation(0, ipField.getHeight() + 130);
		
		joinButton = new Button(null);
		joinButton.setAlignment(Image.CENTER, Image.CENTER);
		joinButton.setLocation(0, -40);

		createServerButton = new Button(null);
		createServerButton.setAlignment(Image.CENTER, Image.CENTER);
		createServerButton.setLocation(0, -220);
		
		try
		{
			ipField.setBackgroundImage("res/images/GUI/TextField.png");
			joinServerImage.setImage("res/images/joinserver.png");
			createServerButton.setImage("res/images/createserver.png");
			joinButton.setImage("res/images/join.png");
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
				
				if (source == joinButton)
				{
					String ip = ipField.getText();
					int port  = 9482;
					
					client = new Client(ip, port)
					{
						public void onReceivedPacket(Packet packet)
						{
							tetris.addPacketToQueue(packet);
						}
					};
					
					new Thread()
					{
						public void run()
						{
							try
							{
								client.connect();
								
								if (client.isConnected())
								{
									startGame = true;
								}
							}
							catch (NetworkException e)
							{
								status = "Connection refused.";
							}
						}
					}.start();
				}
				else if (source == createServerButton)
				{
					int port = 9482;
					
					tetris.createServer(port);
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		};
		
		joinButton.addButtonListener(listener);
		createServerButton.addButtonListener(listener);
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#loop()
	 */
	public void loop()
	{
		if (startGame)
		{
			tetris.connectClient(client);
		}
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#render()
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			GL.scale(1.8f, 1.8f, 1);
			
			ipField.render();
			

			GL.scale(0.3f, 0.3f, 1);
			joinServerImage.render();
			createServerButton.render();
			joinButton.render();
			
			if (status != null)
			{
				Tetris.getFont().render(status, 0, 220, 0, 1, Font.CENTER, Font.CENTER, null);
			}
		}
		GL.popMatrix();
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#dispose()
	 */
	public void dispose()
	{
		joinServerImage.dispose();
		createServerButton.dispose();
		joinButton.dispose();
	}
}