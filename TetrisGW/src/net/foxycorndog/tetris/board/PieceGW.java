package net.foxycorndog.tetris.board;
import java.util.ArrayList;
import java.util.List;

import info.gridworld.actor.*;
import info.gridworld.grid.*;

public class PieceGW 
{
	private int 				rotataion;
	private Location 			loc;
	private Location[][] 		locations;
	private PieceGW				pieces[];
	private ArrayList<Bug>	a = new ArrayList<Bug>();
	
	public PieceGW()
	{
		for(int i = 0; i < 4; i++)
		{
			Square s = new Square();

		}
		
		// Long Piece
		pieces[0] = new PieceGW();
		{
			
		}
	}
}
