package com.hifun.soul.gameserver.battle.gem;

/**
 * 棋盘一次移动的抽象;
 * 
 * @author crazyjohn
 * 
 */
public class Move {
	private int row1;
	private int col1;
	private int row2;
	private int col2;

	public Move() {
	}

	public Move(int row1, int col1, int row2, int col2) {
		this.row1 = row1;
		this.col1 = col1;
		this.row2 = row2;
		this.col2 = col2;
	}

	public int getRow1() {
		return row1;
	}

	public void setRow1(int row1) {
		this.row1 = row1;
	}

	public int getCol1() {
		return col1;
	}

	public void setCol1(int col1) {
		this.col1 = col1;
	}

	public int getRow2() {
		return row2;
	}

	public void setRow2(int row2) {
		this.row2 = row2;
	}

	public int getCol2() {
		return col2;
	}

	public void setCol2(int col2) {
		this.col2 = col2;
	}

	@Override
	public String toString() {
		return String.format("(row1: %d, col1: %d)--(row2: %d, col2: %d)",
				row1, col1, row2, col2);
	}

	public boolean rightMove(int row1, int col1, int row2, int col2) {
		if (this.row1 == row1 && this.col1 == col1 && this.row2 == row2
				&& this.col2 == col2) {
			return true;
		}
		if (this.row1 == row2 && this.col1 == col2 && this.row2 == row1
				&& this.col2 == col1) {
			return true;
		}
		return false;
	}

}
