package com.hifun.soul.gameserver.legionmine.msg.info;

public class LegionMineInfo {
	private int mineIndex;
	private boolean canBattle;
	private boolean canMove;
	private boolean canDisturb;
	private int joinLegionType;
	private int surroundState;
	private int occupyNum;
	private boolean occupyNumVisible;
	private boolean isDouble;
	private boolean isRedMine;
	private boolean isSelf;
	private int mineType;

	public int getMineIndex() {
		return mineIndex;
	}

	public void setMineIndex(int mineIndex) {
		this.mineIndex = mineIndex;
	}

	public boolean getCanBattle() {
		return canBattle;
	}

	public void setCanBattle(boolean canBattle) {
		this.canBattle = canBattle;
	}

	public boolean getCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public int getJoinLegionType() {
		return joinLegionType;
	}

	public void setJoinLegionType(int joinLegionType) {
		this.joinLegionType = joinLegionType;
	}

	public int getSurroundState() {
		return surroundState;
	}

	public void setSurroundState(int surroundState) {
		this.surroundState = surroundState;
	}

	public int getOccupyNum() {
		return occupyNum;
	}

	public void setOccupyNum(int occupyNum) {
		this.occupyNum = occupyNum;
	}

	public boolean getOccupyNumVisible() {
		return occupyNumVisible;
	}

	public void setOccupyNumVisible(boolean occupyNumVisible) {
		this.occupyNumVisible = occupyNumVisible;
	}

	public boolean getIsDouble() {
		return isDouble;
	}

	public void setIsDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}

	public boolean getIsRedMine() {
		return isRedMine;
	}

	public void setIsRedMine(boolean isRedMine) {
		this.isRedMine = isRedMine;
	}

	public boolean getCanDisturb() {
		return canDisturb;
	}

	public void setCanDisturb(boolean canDisturb) {
		this.canDisturb = canDisturb;
	}

	public boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public int getMineType() {
		return mineType;
	}

	public void setMineType(int mineType) {
		this.mineType = mineType;
	}

}
