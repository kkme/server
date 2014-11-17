package com.hifun.soul.gameserver.battle.gem;

/**
 * 宝石;
 * 
 * @author crazyjohn
 * 
 */
public class GemObject {
	private GemType type;
	private int column;
	private int row;
	private long id;

	public GemType getType() {
		return type;
	}

	public void setType(GemType type) {
		this.type = type;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void replaceOldGem(GemObject oldGem) {
		this.column = oldGem.column;
		this.row = oldGem.row;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("(").append(this.row).append(",")
				.append(this.column).append(" , type: ").append(this.type)
				.append(")").toString();
	}

}
