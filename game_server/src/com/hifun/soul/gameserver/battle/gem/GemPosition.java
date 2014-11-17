package com.hifun.soul.gameserver.battle.gem;

/**
 * 宝石位置;
 * 
 * @author crazyjohn
 * 
 */
public class GemPosition {
	public static final GemPosition NULL_OBJECT = new GemPosition();
	private int row;
	private int col;
	private int type;

	public GemPosition() {
	}

	public GemPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static GemPosition createPosition(int selectedRow, int selectedCol) {
		return new GemPosition(selectedRow, selectedCol);
	}
}
