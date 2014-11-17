package com.hifun.soul.gameserver.legionmine;

import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.enums.MineSurroundState;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.manager.LegionMineWarTemplateManager;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRichRateTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineSurroundStateTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineTypeTemplate;

/**
 * 军团战矿堆信息
 * 
 * @author yandajun
 * 
 */
public class LegionMine {
	private int mineIndex;
	private boolean isRedMine;
	private int mineType;
	private String surroundIndexes;
	private String canMoveOrBattleIndexes;

	private GlobalLegionMineWarManager globaleLegionMineManager = GameServerAssist
			.getGlobalLegionMineWarManager();
	private LegionMineWarTemplateManager templateManager = GameServerAssist
			.getLegionMineWarTemplateManager();

	/**
	 * 获取占领当前矿位人数
	 */
	public int getOccupyNum() {
		return globaleLegionMineManager.getMembersOnMine(mineIndex).size();
	}

	/**
	 * 获取当前矿位占领人数上限
	 */
	public int getMaxOccupyNum() {
		int mineType = templateManager.getLegionMineTemplate(mineIndex)
				.getMineType();
		LegionMineTypeTemplate mineTypeTemplate = templateManager
				.getMineTypeTemplate(mineType);
		int totalNum = globaleLegionMineManager.getOnlineLegionMineMembers()
				.size();
		int maxNum = totalNum * mineTypeTemplate.getPeopleBaseNum()
				/ templateManager.getConstantsTemplate().getMineMaxNumRate();
		if (totalNum * mineTypeTemplate.getPeopleBaseNum()
				% templateManager.getConstantsTemplate().getMineMaxNumRate() != 0) {
			maxNum++;
		}
		return maxNum;
	}

	/**
	 * 获取当前占领军团类型
	 */
	public JoinLegionType getOccupyLegionType() {
		List<LegionMineMember> memberListOnMine = globaleLegionMineManager
				.getMembersOnMine(mineIndex);
		if (memberListOnMine.size() == 0) {
			return JoinLegionType.NO_LEGION;
		} else {
			return memberListOnMine.get(0).getLegionType();
		}

	}

	/**
	 * 获取当前包围状态
	 */
	public MineSurroundState getSurroundState() {
		JoinLegionType selfLegionType = getOccupyLegionType();
		if (selfLegionType == JoinLegionType.NO_LEGION) {
			return MineSurroundState.NOT_SURROUNDED;
		}
		// 被包围的矿位
		int surroundNum = 0;
		for (String surroundIndex : surroundIndexes.split(",")) {
			LegionMine mine = globaleLegionMineManager.getLegionMine(Integer
					.parseInt(surroundIndex));
			if (mine.getOccupyLegionType() != selfLegionType
					&& mine.getOccupyLegionType() != JoinLegionType.NO_LEGION) {
				surroundNum++;
			}
		}
		double surroundNumDouble = surroundNum;
		double totalNum = surroundIndexes.split(",").length;
		double surroundRate = surroundNumDouble / totalNum;
		int surroundState = 0;
		LegionMineSurroundStateTemplate template = templateManager
				.getSurroundStateTemplate(surroundRate);
		if (template != null) {
			surroundState = template.getId();
		}
		return MineSurroundState.indexOf(surroundState);
	}

	/**
	 * 获取当前包围加成
	 */
	public int getSurroundAmend() {
		// 被包围的矿位
		int surroundNum = 0;
		for (String surroundIndex : surroundIndexes.split(",")) {
			LegionMine mine = globaleLegionMineManager.getLegionMine(Integer
					.parseInt(surroundIndex));
			if (mine.getOccupyLegionType() != mine.getOccupyLegionType()) {
				surroundNum++;
			}
		}
		double surroundNumDouble = surroundNum;
		double totalNum = surroundIndexes.split(",").length;
		double surroundRate = surroundNumDouble / totalNum;
		int surroundAmend = 0;
		LegionMineSurroundStateTemplate template = templateManager
				.getSurroundStateTemplate(surroundRate);
		if (template != null) {
			surroundAmend = template.getEffect();
		}
		return surroundAmend;
	}

	/**
	 * 获取收益
	 */
	public int getMineRevenue() {
		int mineType = templateManager.getLegionMineTemplate(mineIndex)
				.getMineType();
		LegionMineTypeTemplate mineTypeTemplate = templateManager
				.getMineTypeTemplate(mineType);
		int revenue = mineTypeTemplate.getSingleRevenue();
		// 根据富裕度算出收益比例
		float revenueRate = 1.0f;
		LegionMineRichRateTemplate richRateTempalte = getRichRateTemplate();
		if (richRateTempalte != null) {
			revenueRate = richRateTempalte.getRevenueRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		}
		revenue = (int) (revenue * revenueRate);
		if (isDouble()) {
			revenue *= 2;
		}
		return revenue;
	}

	/**
	 * 获取矿位富裕度模板
	 */
	public LegionMineRichRateTemplate getRichRateTemplate() {
		float occupyNum = getOccupyNum();
		float maxOccupyNum = getMaxOccupyNum();
		float richRate = occupyNum / maxOccupyNum;
		for (int id : templateManager.getRichRateTemplates().keySet()) {
			LegionMineRichRateTemplate richRateTempalte = templateManager
					.getRichRateTemplates().get(id);
			if (richRateTempalte.getHighest() == 0) {
				if (richRate > richRateTempalte.getLowest()) {
					return richRateTempalte;
				}
			} else {
				if (richRate > richRateTempalte.getLowest()
						&& richRate <= richRateTempalte.getHighest()) {
					return richRateTempalte;
				}
			}
		}
		return null;
	}

	public int getMineIndex() {
		return mineIndex;
	}

	public void setMineIndex(int mineIndex) {
		this.mineIndex = mineIndex;
	}

	public boolean isDouble() {
		return globaleLegionMineManager.getDoubleMineIndex() == this.mineIndex;
	}

	public boolean isRedMine() {
		return isRedMine;
	}

	public void setRedMine(boolean isRedMine) {
		this.isRedMine = isRedMine;
	}

	public int getMineType() {
		return mineType;
	}

	public void setMineType(int mineType) {
		this.mineType = mineType;
	}

	public int getSurroundNum() {
		return 0;
	}

	public String getSurroundIndexes() {
		return surroundIndexes;
	}

	public void setSurroundIndexes(String surroundIndexes) {
		this.surroundIndexes = surroundIndexes;
	}

	public String getCanMoveOrBattleIndexes() {
		return canMoveOrBattleIndexes;
	}

	public void setCanMoveOrBattleIndexes(String canMoveOrBattleIndexes) {
		this.canMoveOrBattleIndexes = canMoveOrBattleIndexes;
	}

}
