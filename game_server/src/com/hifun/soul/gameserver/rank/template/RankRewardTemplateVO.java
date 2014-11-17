package com.hifun.soul.gameserver.rank.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 排行榜奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RankRewardTemplateVO extends TemplateObject {

	/** 排行榜ID */
	@ExcelCellBinding(offset = 1)
	protected int rankId;

	/** 排名起始 */
	@ExcelCellBinding(offset = 2)
	protected int rankingPositionBegin;

	/** 排名结束 */
	@ExcelCellBinding(offset = 3)
	protected int rankingPositionEnd;

	/** 奖品 */
	@ExcelCellBinding(offset = 4)
	protected int giftId;


	public int getRankId() {
		return this.rankId;
	}

	public void setRankId(int rankId) {
		if (rankId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[排行榜ID]rankId不可以为0");
		}
		this.rankId = rankId;
	}
	
	public int getRankingPositionBegin() {
		return this.rankingPositionBegin;
	}

	public void setRankingPositionBegin(int rankingPositionBegin) {
		if (rankingPositionBegin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[排名起始]rankingPositionBegin不可以为0");
		}
		this.rankingPositionBegin = rankingPositionBegin;
	}
	
	public int getRankingPositionEnd() {
		return this.rankingPositionEnd;
	}

	public void setRankingPositionEnd(int rankingPositionEnd) {
		if (rankingPositionEnd == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[排名结束]rankingPositionEnd不可以为0");
		}
		this.rankingPositionEnd = rankingPositionEnd;
	}
	
	public int getGiftId() {
		return this.giftId;
	}

	public void setGiftId(int giftId) {
		if (giftId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[奖品]giftId的值不得小于0");
		}
		this.giftId = giftId;
	}
	

	@Override
	public String toString() {
		return "RankRewardTemplateVO[rankId=" + rankId + ",rankingPositionBegin=" + rankingPositionBegin + ",rankingPositionEnd=" + rankingPositionEnd + ",giftId=" + giftId + ",]";

	}
}