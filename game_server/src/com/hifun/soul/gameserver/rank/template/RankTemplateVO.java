package com.hifun.soul.gameserver.rank.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 排行榜模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RankTemplateVO extends TemplateObject {

	/** 排行榜id */
	@ExcelCellBinding(offset = 1)
	protected int rankId;

	/** 排行榜名称 */
	@ExcelCellBinding(offset = 2)
	protected String rankName;

	/** 排行榜描述 */
	@ExcelCellBinding(offset = 3)
	protected String rankDesc;

	/** 是否有奖励 */
	@ExcelCellBinding(offset = 4)
	protected boolean hasReward;

	/** 奖品领取间隔 */
	@ExcelCellBinding(offset = 5)
	protected int days;


	public int getRankId() {
		return this.rankId;
	}

	public void setRankId(int rankId) {
		if (rankId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[排行榜id]rankId不可以为0");
		}
		this.rankId = rankId;
	}
	
	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		if (StringUtils.isEmpty(rankName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[排行榜名称]rankName不可以为空");
		}
		this.rankName = rankName;
	}
	
	public String getRankDesc() {
		return this.rankDesc;
	}

	public void setRankDesc(String rankDesc) {
		if (StringUtils.isEmpty(rankDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[排行榜描述]rankDesc不可以为空");
		}
		this.rankDesc = rankDesc;
	}
	
	public boolean isHasReward() {
		return this.hasReward;
	}

	public void setHasReward(boolean hasReward) {
		this.hasReward = hasReward;
	}
	
	public int getDays() {
		return this.days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	

	@Override
	public String toString() {
		return "RankTemplateVO[rankId=" + rankId + ",rankName=" + rankName + ",rankDesc=" + rankDesc + ",hasReward=" + hasReward + ",days=" + days + ",]";

	}
}