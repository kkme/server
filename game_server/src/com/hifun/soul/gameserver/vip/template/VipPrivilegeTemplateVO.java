package com.hifun.soul.gameserver.vip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * VIP特权模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class VipPrivilegeTemplateVO extends TemplateObject {

	/** VIP等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** vip等级特权描述 */
	@ExcelCellBinding(offset = 2)
	protected String desc;

	/** 达到VIP等级需要的充值数量 */
	@ExcelCellBinding(offset = 3)
	protected int rechargeNum;

	/** 购买问答祈福最大次数 */
	@ExcelCellBinding(offset = 4)
	protected int maxBuyAnswerBlessTimes;

	/** 购买体力值最大次数 */
	@ExcelCellBinding(offset = 5)
	protected int maxBuyEnergyTimes;

	/** 竞技场购买次数上限 */
	@ExcelCellBinding(offset = 6)
	protected int maxArenaBuyTimes;

	/** 刷新精英副本最大次数 */
	@ExcelCellBinding(offset = 7)
	protected int maxRefreshEliteStageTimes;

	/** 购买矿坑开采权最大次数 */
	@ExcelCellBinding(offset = 8)
	protected int maxBuyMineFieldNum;

	/** 花费魔晶收集魔法石最大次数 */
	@ExcelCellBinding(offset = 9)
	protected int maxCrystalCollectNum;

	/** 魔晶兑换最大次数 */
	@ExcelCellBinding(offset = 10)
	protected int maxExchangeTime;

	/** 扫荡减少的cd时间(单位分钟) */
	@ExcelCellBinding(offset = 11)
	protected int autoBattleCd;

	/** 试炼刷新次数 */
	@ExcelCellBinding(offset = 12)
	protected int refineRefreshTimes;

	/** 魔晶抽奖次数 */
	@ExcelCellBinding(offset = 13)
	protected int crystalTurntableTimes;

	/** 战俘营购买抓捕次数 */
	@ExcelCellBinding(offset = 14)
	protected int maxBuyPrisonArrestTimes;

	/** 角斗场购买次数 */
	@ExcelCellBinding(offset = 15)
	protected int maxBuyAbattoirTimes;

	/** 嗜血神殿购买次数 */
	@ExcelCellBinding(offset = 16)
	protected int maxBuyBloodTempleTimes;

	/** 战神之巅购买加倍次数 */
	@ExcelCellBinding(offset = 17)
	protected int maxBuyMarsMutipleTimes;

	/** 主城税收押注必胜次数 */
	@ExcelCellBinding(offset = 18)
	protected int maxLevyCertainWinTimes;

	/** 秘药使用数量 */
	@ExcelCellBinding(offset = 19)
	protected int maxNostrumNum;

	/** 默认押运怪物品质 */
	@ExcelCellBinding(offset = 20)
	protected int defaultEscortMonsterType;

	/** 可购买打劫押运次数 */
	@ExcelCellBinding(offset = 21)
	protected int maxBuyEscortRobTimes;

	/** 可购买日常任务次数 */
	@ExcelCellBinding(offset = 22)
	protected int maxBuyDailyQuestTimes;

	/** 精灵对酒必胜次数 */
	@ExcelCellBinding(offset = 23)
	protected int maxSpritePubCertainWinTimes;

	/** 最大可累计体力手动恢复次数 */
	@ExcelCellBinding(offset = 24)
	protected int maxEnergyRecoverTotalTimes;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[VIP等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[vip等级特权描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getRechargeNum() {
		return this.rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		if (rechargeNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[达到VIP等级需要的充值数量]rechargeNum的值不得小于0");
		}
		this.rechargeNum = rechargeNum;
	}
	
	public int getMaxBuyAnswerBlessTimes() {
		return this.maxBuyAnswerBlessTimes;
	}

	public void setMaxBuyAnswerBlessTimes(int maxBuyAnswerBlessTimes) {
		if (maxBuyAnswerBlessTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[购买问答祈福最大次数]maxBuyAnswerBlessTimes的值不得小于0");
		}
		this.maxBuyAnswerBlessTimes = maxBuyAnswerBlessTimes;
	}
	
	public int getMaxBuyEnergyTimes() {
		return this.maxBuyEnergyTimes;
	}

	public void setMaxBuyEnergyTimes(int maxBuyEnergyTimes) {
		if (maxBuyEnergyTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[购买体力值最大次数]maxBuyEnergyTimes的值不得小于0");
		}
		this.maxBuyEnergyTimes = maxBuyEnergyTimes;
	}
	
	public int getMaxArenaBuyTimes() {
		return this.maxArenaBuyTimes;
	}

	public void setMaxArenaBuyTimes(int maxArenaBuyTimes) {
		if (maxArenaBuyTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[竞技场购买次数上限]maxArenaBuyTimes的值不得小于0");
		}
		this.maxArenaBuyTimes = maxArenaBuyTimes;
	}
	
	public int getMaxRefreshEliteStageTimes() {
		return this.maxRefreshEliteStageTimes;
	}

	public void setMaxRefreshEliteStageTimes(int maxRefreshEliteStageTimes) {
		if (maxRefreshEliteStageTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[刷新精英副本最大次数]maxRefreshEliteStageTimes的值不得小于0");
		}
		this.maxRefreshEliteStageTimes = maxRefreshEliteStageTimes;
	}
	
	public int getMaxBuyMineFieldNum() {
		return this.maxBuyMineFieldNum;
	}

	public void setMaxBuyMineFieldNum(int maxBuyMineFieldNum) {
		if (maxBuyMineFieldNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[购买矿坑开采权最大次数]maxBuyMineFieldNum的值不得小于0");
		}
		this.maxBuyMineFieldNum = maxBuyMineFieldNum;
	}
	
	public int getMaxCrystalCollectNum() {
		return this.maxCrystalCollectNum;
	}

	public void setMaxCrystalCollectNum(int maxCrystalCollectNum) {
		if (maxCrystalCollectNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[花费魔晶收集魔法石最大次数]maxCrystalCollectNum的值不得小于0");
		}
		this.maxCrystalCollectNum = maxCrystalCollectNum;
	}
	
	public int getMaxExchangeTime() {
		return this.maxExchangeTime;
	}

	public void setMaxExchangeTime(int maxExchangeTime) {
		if (maxExchangeTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[魔晶兑换最大次数]maxExchangeTime的值不得小于0");
		}
		this.maxExchangeTime = maxExchangeTime;
	}
	
	public int getAutoBattleCd() {
		return this.autoBattleCd;
	}

	public void setAutoBattleCd(int autoBattleCd) {
		if (autoBattleCd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[扫荡减少的cd时间(单位分钟)]autoBattleCd的值不得小于0");
		}
		this.autoBattleCd = autoBattleCd;
	}
	
	public int getRefineRefreshTimes() {
		return this.refineRefreshTimes;
	}

	public void setRefineRefreshTimes(int refineRefreshTimes) {
		if (refineRefreshTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[试炼刷新次数]refineRefreshTimes的值不得小于0");
		}
		this.refineRefreshTimes = refineRefreshTimes;
	}
	
	public int getCrystalTurntableTimes() {
		return this.crystalTurntableTimes;
	}

	public void setCrystalTurntableTimes(int crystalTurntableTimes) {
		if (crystalTurntableTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[魔晶抽奖次数]crystalTurntableTimes的值不得小于0");
		}
		this.crystalTurntableTimes = crystalTurntableTimes;
	}
	
	public int getMaxBuyPrisonArrestTimes() {
		return this.maxBuyPrisonArrestTimes;
	}

	public void setMaxBuyPrisonArrestTimes(int maxBuyPrisonArrestTimes) {
		if (maxBuyPrisonArrestTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[战俘营购买抓捕次数]maxBuyPrisonArrestTimes的值不得小于0");
		}
		this.maxBuyPrisonArrestTimes = maxBuyPrisonArrestTimes;
	}
	
	public int getMaxBuyAbattoirTimes() {
		return this.maxBuyAbattoirTimes;
	}

	public void setMaxBuyAbattoirTimes(int maxBuyAbattoirTimes) {
		if (maxBuyAbattoirTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[角斗场购买次数]maxBuyAbattoirTimes的值不得小于0");
		}
		this.maxBuyAbattoirTimes = maxBuyAbattoirTimes;
	}
	
	public int getMaxBuyBloodTempleTimes() {
		return this.maxBuyBloodTempleTimes;
	}

	public void setMaxBuyBloodTempleTimes(int maxBuyBloodTempleTimes) {
		if (maxBuyBloodTempleTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[嗜血神殿购买次数]maxBuyBloodTempleTimes的值不得小于0");
		}
		this.maxBuyBloodTempleTimes = maxBuyBloodTempleTimes;
	}
	
	public int getMaxBuyMarsMutipleTimes() {
		return this.maxBuyMarsMutipleTimes;
	}

	public void setMaxBuyMarsMutipleTimes(int maxBuyMarsMutipleTimes) {
		if (maxBuyMarsMutipleTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[战神之巅购买加倍次数]maxBuyMarsMutipleTimes的值不得小于0");
		}
		this.maxBuyMarsMutipleTimes = maxBuyMarsMutipleTimes;
	}
	
	public int getMaxLevyCertainWinTimes() {
		return this.maxLevyCertainWinTimes;
	}

	public void setMaxLevyCertainWinTimes(int maxLevyCertainWinTimes) {
		if (maxLevyCertainWinTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[主城税收押注必胜次数]maxLevyCertainWinTimes的值不得小于0");
		}
		this.maxLevyCertainWinTimes = maxLevyCertainWinTimes;
	}
	
	public int getMaxNostrumNum() {
		return this.maxNostrumNum;
	}

	public void setMaxNostrumNum(int maxNostrumNum) {
		if (maxNostrumNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[秘药使用数量]maxNostrumNum的值不得小于0");
		}
		this.maxNostrumNum = maxNostrumNum;
	}
	
	public int getDefaultEscortMonsterType() {
		return this.defaultEscortMonsterType;
	}

	public void setDefaultEscortMonsterType(int defaultEscortMonsterType) {
		if (defaultEscortMonsterType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[默认押运怪物品质]defaultEscortMonsterType的值不得小于0");
		}
		this.defaultEscortMonsterType = defaultEscortMonsterType;
	}
	
	public int getMaxBuyEscortRobTimes() {
		return this.maxBuyEscortRobTimes;
	}

	public void setMaxBuyEscortRobTimes(int maxBuyEscortRobTimes) {
		if (maxBuyEscortRobTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[可购买打劫押运次数]maxBuyEscortRobTimes的值不得小于0");
		}
		this.maxBuyEscortRobTimes = maxBuyEscortRobTimes;
	}
	
	public int getMaxBuyDailyQuestTimes() {
		return this.maxBuyDailyQuestTimes;
	}

	public void setMaxBuyDailyQuestTimes(int maxBuyDailyQuestTimes) {
		if (maxBuyDailyQuestTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[可购买日常任务次数]maxBuyDailyQuestTimes的值不得小于0");
		}
		this.maxBuyDailyQuestTimes = maxBuyDailyQuestTimes;
	}
	
	public int getMaxSpritePubCertainWinTimes() {
		return this.maxSpritePubCertainWinTimes;
	}

	public void setMaxSpritePubCertainWinTimes(int maxSpritePubCertainWinTimes) {
		if (maxSpritePubCertainWinTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[精灵对酒必胜次数]maxSpritePubCertainWinTimes的值不得小于0");
		}
		this.maxSpritePubCertainWinTimes = maxSpritePubCertainWinTimes;
	}
	
	public int getMaxEnergyRecoverTotalTimes() {
		return this.maxEnergyRecoverTotalTimes;
	}

	public void setMaxEnergyRecoverTotalTimes(int maxEnergyRecoverTotalTimes) {
		if (maxEnergyRecoverTotalTimes == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[最大可累计体力手动恢复次数]maxEnergyRecoverTotalTimes不可以为0");
		}
		this.maxEnergyRecoverTotalTimes = maxEnergyRecoverTotalTimes;
	}
	

	@Override
	public String toString() {
		return "VipPrivilegeTemplateVO[level=" + level + ",desc=" + desc + ",rechargeNum=" + rechargeNum + ",maxBuyAnswerBlessTimes=" + maxBuyAnswerBlessTimes + ",maxBuyEnergyTimes=" + maxBuyEnergyTimes + ",maxArenaBuyTimes=" + maxArenaBuyTimes + ",maxRefreshEliteStageTimes=" + maxRefreshEliteStageTimes + ",maxBuyMineFieldNum=" + maxBuyMineFieldNum + ",maxCrystalCollectNum=" + maxCrystalCollectNum + ",maxExchangeTime=" + maxExchangeTime + ",autoBattleCd=" + autoBattleCd + ",refineRefreshTimes=" + refineRefreshTimes + ",crystalTurntableTimes=" + crystalTurntableTimes + ",maxBuyPrisonArrestTimes=" + maxBuyPrisonArrestTimes + ",maxBuyAbattoirTimes=" + maxBuyAbattoirTimes + ",maxBuyBloodTempleTimes=" + maxBuyBloodTempleTimes + ",maxBuyMarsMutipleTimes=" + maxBuyMarsMutipleTimes + ",maxLevyCertainWinTimes=" + maxLevyCertainWinTimes + ",maxNostrumNum=" + maxNostrumNum + ",defaultEscortMonsterType=" + defaultEscortMonsterType + ",maxBuyEscortRobTimes=" + maxBuyEscortRobTimes + ",maxBuyDailyQuestTimes=" + maxBuyDailyQuestTimes + ",maxSpritePubCertainWinTimes=" + maxSpritePubCertainWinTimes + ",maxEnergyRecoverTotalTimes=" + maxEnergyRecoverTotalTimes + ",]";

	}
}