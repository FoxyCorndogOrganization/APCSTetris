package net.foxycorndog.tetris.board;

public class Event
{
	private long when;
	
	public Event()
	{
		when = System.currentTimeMillis();
	}
	
	public long getWhen()
	{
		return when;
	}
}
