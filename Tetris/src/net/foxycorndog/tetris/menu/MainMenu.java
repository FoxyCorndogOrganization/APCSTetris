package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.jfoxyutil.Queue;
import net.foxycorndog.tetris.Tetris;

/**
 * Class that represents the first Menu that the user is faced with.
 * Also may be called the "Title menu." Has the basic functionality
 * needed to play the game, toggle options, view credits, or quit the
 * game.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 21, 2013 at 12:10:55 AM
 * @since	v0.1
 * @version	Apr 24, 2013 at 6:38:55 AM
 * @version	v0.1
 */
public class MainMenu extends Menu
{
	private boolean 		rUp, gUp, bUp;
	
	private int				r, g, b;
	private	int				circleX, circleY;
	
	private float			counter;
	
	private Texture			titleScreenTexture, menuBoxTexture,
							circleTexture;
	
	private Button			playButton, optionsButton, creditsButton,
							quitButton;
	
	private Bundle			bundle;
	
	private Tetris			tetris;
	
	private Queue<Integer>	queue;
	
	/**
	 * Create the MainMenu for the specified Tetris instance.
	 * 
	 * @param tetris The Tetris game to limk with this MainMenu.
	 */
	public MainMenu(final Tetris tetris)
	{
		this.tetris = tetris;
		
		bundle      = new Bundle(4 * 3, 2, true, false);
		
		queue       = new Queue<Integer>();
		
		r = 235;
		g = 235;
		b = 235;
		
		Texture playButtonTexture    = null;
		Texture optionsButtonTexture = null;
		Texture creditsButtonTexture = null;
		Texture quitButtonTexture    = null;
		
		try
		{
			titleScreenTexture   = new Texture("res/images/titlescreen.png");
			menuBoxTexture       = new Texture("res/images/menu.png");
			circleTexture        = new Texture("res/images/circle.png");
			
			playButtonTexture    = new Texture("res/images/play.png");
			optionsButtonTexture = new Texture("res/images/options.png");
			creditsButtonTexture = new Texture("res/images/credits.png");
			quitButtonTexture    = new Texture("res/images/quit.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		bundle.beginEditingVertices();
		{
			bundle.addVertices(GL.genRectVerts(0, 0, titleScreenTexture.getWidth(), titleScreenTexture.getHeight()));
			bundle.addVertices(GL.genRectVerts(0, 0, menuBoxTexture.getWidth(), menuBoxTexture.getHeight()));
			bundle.addVertices(GL.genRectVerts(0, 0, circleTexture.getWidth(), circleTexture.getHeight()));
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			bundle.addTextures(GL.genRectTextures(titleScreenTexture.getImageOffsets()));
			bundle.addTextures(GL.genRectTextures(menuBoxTexture.getImageOffsets()));
			bundle.addTextures(GL.genRectTextures(circleTexture.getImageOffsets()));
		}
		bundle.endEditingTextures();
		
		// Make sure that the circleTexture is ready for rendering by
		// rendering it during the creation of the MainMenu.
		circleTexture.bind();
		
		playButton = new Button(null);
		playButton.setImage(playButtonTexture);
		playButton.setLocation(86, 230 - 38 * 0);
		
		optionsButton = new Button(null);
		optionsButton.setImage(optionsButtonTexture);
		optionsButton.setLocation(86, 230 - 38 * 1);
		
		creditsButton = new Button(null);
		creditsButton.setImage(creditsButtonTexture);
		creditsButton.setLocation(86, 230 - 38 * 2);
		
		quitButton = new Button(null);
		quitButton.setImage(quitButtonTexture);
		quitButton.setLocation(86, 232 - 38 * 3);
		
		ButtonListener listener = new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				if (!queue.isEmpty())
				{
					queue.dequeue();
				}
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				if (event.getSource() == playButton)
				{
					tetris.playGame();
				}
				else if (event.getSource() == optionsButton)
				{
					
				}
				else if (event.getSource() == creditsButton)
				{
					
				}
				else if (event.getSource() == quitButton)
				{
//					System.exit(0);
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				Button button  = event.getSource();
				
				circleX = button.getX();
				circleY = button.getY() - 5;
				
				queue.enqueue(circleY);
			}
		};
		
		playButton.addButtonListener(listener);
		optionsButton.addButtonListener(listener);
		creditsButton.addButtonListener(listener);
		quitButton.addButtonListener(listener);
	}
	
	/**
	 * Method called each frame. Used to calculate logic and
	 * update the Color of the MainMenu.
	 */
	public void loop()
	{
		float delta = 60f / Frame.getFPS();
		
		if (!Float.isInfinite(delta) && !Float.isNaN(delta))
		{
			counter += delta;
		}
		
		updateColor();
	}
	
	/**
	 * Render the MainMenu and all of its components to the screen.
	 */
	public void render()
	{
		float width  = (float)titleScreenTexture.getWidth();
		float height = (float)titleScreenTexture.getHeight();
		
		float scaleX = Frame.getWidth()  / width;
		float scaleY = Frame.getHeight() / height;
		
		float min    = scaleX < scaleY ? scaleX : scaleY;
		
		float xOff   = (Frame.getWidth()  - (width  * min)) / 2f;
		float yOff   = (Frame.getHeight() - (height * min)) / 2f;
		
		GL.pushMatrix();
		{
			GL.pushAttrib(GL.TEXTURE_BIT);
			{
				GL.setTextureScaleMinMethod(GL.LINEAR);
				GL.setTextureScaleMagMethod(GL.LINEAR);
				
				GL.translate(xOff + getX(), yOff + getY(), 0);
				GL.scale(min, min, 1);
				
				GL.setColor(r / 255f, g / 255f, b / 255f, 1);
				bundle.render(GL.QUADS, 0, 4, titleScreenTexture);
				
				bundle.render(GL.QUADS, 4, 4, menuBoxTexture);
				
				renderButtons();
				
				Texture.unbind();
			}
			GL.popAttrib();
		}
		GL.popMatrix();
	}
	
	/**
	 * Set the color that the Menu will be rendered in.
	 */
	private void updateColor()
	{
		if (counter >= 1)
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
			
			counter %= 1;
		}
	}
	
	/**
	 * Render the Buttons to their correct place.
	 */
	private void renderButtons()
	{
		GL.setColor((r + 100) / 255f, (g + 100) / 255f, (b + 100) / 255f, 1);
		
		playButton.render();
		optionsButton.render();
		creditsButton.render();
		
		if (circleY != quitButton.getY() - 5 || queue.size() <= 0)
		 {
			quitButton.render();
		}
		
		if (queue.size() > 0)
		{
			GL.translate(circleX, circleY, 0);
			bundle.render(GL.QUADS, 4 * 2, 4, circleTexture);
		}
	}
}
