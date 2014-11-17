package com.hifun.soul.gameserver.battle.gem;

/**
 * 棋盘的测试;
 * 
 * @author crazyjohn
 * 
 */
public class ChessBoardTest {
	static GemChessBoard board = new GemChessBoard(8, 8, null);

	public static void main(String[] args) {
		board.initChessBoard();
		System.out.println(board.getMoves());
	}

}
