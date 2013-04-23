package net.foxycorndog.tetris.menu;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:13:05 PM
 * @since	v0.1
 * @version	Apr 20, 2013 at 11:13:05 PM
 * @version	v0.1
 */
public abstract class Menu
{
	private int	x, y;
	
	/**
	 * 
	 */
	public Menu()
	{
		
	}
	
	/**
	 * Get the x position of the Menu.
	 * 
	 * @return The x position of the Menu.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Get the y position of the Menu.
	 * 
	 * @return The y position of the Menu.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Set the x and y location of the Menu.
	 * 
	 * @param x The new x location of the Menu.
	 * @param y The new y location of the Menu.
	 */
	public void setLocation(int x, int y)
	{
		this.x = y;
		this.y = y;
	}
	
	/**
	 * 
	 */
	public abstract void loop();
	
	/**
	 * 
	 */
	public abstract void render();
}