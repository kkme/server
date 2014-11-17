package com.hifun.soul.gameserver.battle.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.ColNewGems;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.function.IFunctionGems;

/**
 * 棋盘快照;<br>
 * 包括两部分: 1. 需要消除的宝石; 2. 新生成的宝石;<br>
 * 
 * @author crazyjohn
 * 
 */
public class ChessBoardSnap {
	private List<GemPosition> erasableGems = new ArrayList<GemPosition>();;
	private List<ColNewGems> generatedGems = new ArrayList<ColNewGems>();
	/** 是否触发新回合 */
	private boolean triggerNewRound;
	/** 功能消除对列表 */
	private List<IFunctionGems> functionGemsList = new ArrayList<IFunctionGems>();
	/** 本次回合中消除的个数 */
	private int eraseCount;

	public GemPosition[] getErasableGems() {
		return erasableGems.toArray(new GemPosition[0]);
	}

	public void setErasableGems(GemPosition[] erasableGems) {
		for (GemPosition pos : erasableGems) {
			this.erasableGems.add(pos);
		}
	}

	public ColNewGems[] getGeneratedGems() {
		return generatedGems.toArray(new ColNewGems[0]);
	}

	public void setGeneratedGems(ColNewGems[] generatedGems) {
		for (ColNewGems col : generatedGems) {
			this.generatedGems.add(col);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nerasGems: ");
		for (GemPosition pos : erasableGems) {
			builder.append("( " + pos.getRow() + ", " + pos.getCol() + ") ");
		}
		builder.append("\n");
		builder.append("\ncolNewGems: \n");
		for (ColNewGems col : generatedGems) {
			builder.append("[ col: " + col.getCol() + ", gems: "
					+ Arrays.toString(col.getGems()) + "]");
			builder.append("\n");
		}
		return builder.toString();
	}

	public boolean getTriggerNewRound() {
		return triggerNewRound;
	}

	public void setTriggerNewRound(boolean result) {
		this.triggerNewRound = result;
	}

	public List<IFunctionGems> getFunctionGemsList() {
		return functionGemsList;
	}

	public void addFunctionGemsList(List<IFunctionGems> functionGemsList) {
		if (functionGemsList == null || functionGemsList.isEmpty()) {
			return;
		}
		this.functionGemsList.addAll(functionGemsList);
	}

	public void doFunctionGemsEffect(IBattleUnit unit) {
		for (IFunctionGems funcGems : functionGemsList) {
			funcGems.doEffect(unit, this);
		}
	}

	public int getEraseCount() {
		return eraseCount;
	}

	public void setEraseCount(int eraseCount) {
		this.eraseCount = eraseCount;
	}

}
