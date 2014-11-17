package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 关卡模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageTemplateVO extends TemplateObject {

	/**  地图id */
	@ExcelCellBinding(offset = 1)
	protected int mapId;

	/**  据点id(据点是一个大的建筑,关卡结合) */
	@ExcelCellBinding(offset = 2)
	protected int strongholdId;

	/**  后置关卡id */
	@ExcelCellBinding(offset = 3)
	protected int nextStageId;

	/**  客户端关心, 关卡在地图的x坐标 */
	@ExcelCellBinding(offset = 4)
	protected int x;

	/**  客户端关心, 关卡在地图的y坐标 */
	@ExcelCellBinding(offset = 5)
	protected int y;

	/**  多语言关卡的名字id */
	@ExcelCellBinding(offset = 6)
	protected int stageNameLangId;

	/**  关卡名称 */
	@ExcelCellBinding(offset = 7)
	protected String stageName;

	/**  多语言关卡的描述id */
	@ExcelCellBinding(offset = 8)
	protected int stageDescLangId;

	/**  关卡描述 */
	@ExcelCellBinding(offset = 9)
	protected String stageDesc;

	/**  进入关卡的最小等级 */
	@ExcelCellBinding(offset = 10)
	protected int minLevel;

	/**  怪物ID */
	@ExcelCellBinding(offset = 11)
	protected int monsterId;

	/**  奖励经验值 */
	@ExcelCellBinding(offset = 12)
	protected int rewardExperience;

	/**  奖励货币类型 */
	@ExcelCellBinding(offset = 13)
	protected short rewardCurrencyType;

	/**  奖励货币数量 */
	@ExcelCellBinding(offset = 14)
	protected int rewardCurrencyNum;

	/**  奖励物品 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.turntable.template.ItemRateData.class, collectionNumber = "15,16")
	protected List<com.hifun.soul.gameserver.turntable.template.ItemRateData> rewardItems;

	/** 加星条件1(剩余血量下限) */
	@ExcelCellBinding(offset = 17)
	protected int addStarOne;

	/** 加星条件2(剩余血量下限) */
	@ExcelCellBinding(offset = 18)
	protected int addStarTwo;

	/** 加星条件3(剩余血量下限) */
	@ExcelCellBinding(offset = 19)
	protected int addStarThree;

	/** 加星条件4(剩余血量下限) */
	@ExcelCellBinding(offset = 20)
	protected int addStarFour;


	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		if (mapId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 地图id]mapId不可以为0");
		}
		if (mapId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 地图id]mapId的值不得小于1");
		}
		this.mapId = mapId;
	}
	
	public int getStrongholdId() {
		return this.strongholdId;
	}

	public void setStrongholdId(int strongholdId) {
		if (strongholdId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 据点id(据点是一个大的建筑,关卡结合)]strongholdId不可以为0");
		}
		if (strongholdId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 据点id(据点是一个大的建筑,关卡结合)]strongholdId的值不得小于1");
		}
		this.strongholdId = strongholdId;
	}
	
	public int getNextStageId() {
		return this.nextStageId;
	}

	public void setNextStageId(int nextStageId) {
		if (nextStageId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 后置关卡id]nextStageId的值不得小于0");
		}
		this.nextStageId = nextStageId;
	}
	
	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		if (x < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 客户端关心, 关卡在地图的x坐标]x的值不得小于0");
		}
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		if (y < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 客户端关心, 关卡在地图的y坐标]y的值不得小于0");
		}
		this.y = y;
	}
	
	public int getStageNameLangId() {
		return this.stageNameLangId;
	}

	public void setStageNameLangId(int stageNameLangId) {
		if (stageNameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 多语言关卡的名字id]stageNameLangId的值不得小于0");
		}
		this.stageNameLangId = stageNameLangId;
	}
	
	public String getStageName() {
		return this.stageName;
	}

	public void setStageName(String stageName) {
		if (StringUtils.isEmpty(stageName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 关卡名称]stageName不可以为空");
		}
		this.stageName = stageName;
	}
	
	public int getStageDescLangId() {
		return this.stageDescLangId;
	}

	public void setStageDescLangId(int stageDescLangId) {
		if (stageDescLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 多语言关卡的描述id]stageDescLangId的值不得小于0");
		}
		this.stageDescLangId = stageDescLangId;
	}
	
	public String getStageDesc() {
		return this.stageDesc;
	}

	public void setStageDesc(String stageDesc) {
		if (StringUtils.isEmpty(stageDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 关卡描述]stageDesc不可以为空");
		}
		this.stageDesc = stageDesc;
	}
	
	public int getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(int minLevel) {
		if (minLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 进入关卡的最小等级]minLevel的值不得小于0");
		}
		this.minLevel = minLevel;
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		if (monsterId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 怪物ID]monsterId的值不得小于0");
		}
		this.monsterId = monsterId;
	}
	
	public int getRewardExperience() {
		return this.rewardExperience;
	}

	public void setRewardExperience(int rewardExperience) {
		if (rewardExperience < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 奖励经验值]rewardExperience的值不得小于0");
		}
		this.rewardExperience = rewardExperience;
	}
	
	public short getRewardCurrencyType() {
		return this.rewardCurrencyType;
	}

	public void setRewardCurrencyType(short rewardCurrencyType) {
		if (rewardCurrencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 奖励货币类型]rewardCurrencyType不可以为0");
		}
		if (rewardCurrencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 奖励货币类型]rewardCurrencyType的值不得小于1");
		}
		this.rewardCurrencyType = rewardCurrencyType;
	}
	
	public int getRewardCurrencyNum() {
		return this.rewardCurrencyNum;
	}

	public void setRewardCurrencyNum(int rewardCurrencyNum) {
		if (rewardCurrencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 奖励货币数量]rewardCurrencyNum不可以为0");
		}
		if (rewardCurrencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 奖励货币数量]rewardCurrencyNum的值不得小于1");
		}
		this.rewardCurrencyNum = rewardCurrencyNum;
	}
	
	public List<com.hifun.soul.gameserver.turntable.template.ItemRateData> getRewardItems() {
		return this.rewardItems;
	}

	public void setRewardItems(List<com.hifun.soul.gameserver.turntable.template.ItemRateData> rewardItems) {
		this.rewardItems = rewardItems;
	}
	
	public int getAddStarOne() {
		return this.addStarOne;
	}

	public void setAddStarOne(int addStarOne) {
		if (addStarOne == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[加星条件1(剩余血量下限)]addStarOne不可以为0");
		}
		this.addStarOne = addStarOne;
	}
	
	public int getAddStarTwo() {
		return this.addStarTwo;
	}

	public void setAddStarTwo(int addStarTwo) {
		if (addStarTwo == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[加星条件2(剩余血量下限)]addStarTwo不可以为0");
		}
		this.addStarTwo = addStarTwo;
	}
	
	public int getAddStarThree() {
		return this.addStarThree;
	}

	public void setAddStarThree(int addStarThree) {
		if (addStarThree == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[加星条件3(剩余血量下限)]addStarThree不可以为0");
		}
		this.addStarThree = addStarThree;
	}
	
	public int getAddStarFour() {
		return this.addStarFour;
	}

	public void setAddStarFour(int addStarFour) {
		if (addStarFour == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[加星条件4(剩余血量下限)]addStarFour不可以为0");
		}
		this.addStarFour = addStarFour;
	}
	

	@Override
	public String toString() {
		return "StageTemplateVO[mapId=" + mapId + ",strongholdId=" + strongholdId + ",nextStageId=" + nextStageId + ",x=" + x + ",y=" + y + ",stageNameLangId=" + stageNameLangId + ",stageName=" + stageName + ",stageDescLangId=" + stageDescLangId + ",stageDesc=" + stageDesc + ",minLevel=" + minLevel + ",monsterId=" + monsterId + ",rewardExperience=" + rewardExperience + ",rewardCurrencyType=" + rewardCurrencyType + ",rewardCurrencyNum=" + rewardCurrencyNum + ",rewardItems=" + rewardItems + ",addStarOne=" + addStarOne + ",addStarTwo=" + addStarTwo + ",addStarThree=" + addStarThree + ",addStarFour=" + addStarFour + ",]";

	}
}