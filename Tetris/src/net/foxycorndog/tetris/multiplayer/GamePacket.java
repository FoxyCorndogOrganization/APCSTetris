package net.foxycorndog.tetris.multiplayer;

import net.foxycorndog.jfoxylib.network.Packet;

/**
 * Class used to pass Tetris game data.
 * 
 * @author	Braden Steffaniak
 * @since	May 17, 2013 at 11:14:24 PM
 * @since	v1.0
 * @version	May 17, 2013 at 11:14:24 PM
 * @version	v1.1
 */
public class GamePacket extends Packet
{
	public static final int		LINES_COMPLETED		= 11;
	public static final int		GAME_LOST			= 12;
	
	public static final String	VERSION				= "1.1";
	
	private static final long	serialVersionUID	= 1588730414391768913L;
	
	/**
	 * Creates a new Packet. Packets are used for sending information
	 * across a network.
	 * 
	 * @param object The object that is being sent.
	 * @param id The id of the request that is being sent.
	 */
	public GamePacket(Object object, int id)
	{
		super(object, id);
	}
}
