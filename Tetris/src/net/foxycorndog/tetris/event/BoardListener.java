package net.foxycorndog.tetris.event;

public interface BoardListener
{
	public void onPieceMove(BoardEvent event);
	public void onLineDeleted(BoardEvent event);
	public void onGameLost(BoardEvent event);
}
